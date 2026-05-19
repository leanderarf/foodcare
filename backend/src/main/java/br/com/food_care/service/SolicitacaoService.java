package br.com.food_care.service;

import br.com.food_care.dto.SolicitacaoRequestDTO;
import br.com.food_care.dto.SolicitacaoResponseDTO;
import br.com.food_care.entity.*;
import br.com.food_care.mapper.SolicitacaoMapper;
import br.com.food_care.repository.SolicitacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AlimentoService alimentoService;

    @Transactional
    public Solicitacao criarSolicitacao(SolicitacaoRequestDTO solicitacaoDto) {
        Long idUsuario = solicitacaoDto.id();
        Usuario receptor = buscarReceptorPorId(idUsuario);
        Alimento alimento = alimentoService.validarAlimentoExistente(solicitacaoDto.alimento().getId());

        int estoqueDisponivel = alimento.getQuantidadeTotal() - alimento.getQuantidadeReservada();

        if (estoqueDisponivel < solicitacaoDto.quantidade())
            throw  new RuntimeException("Não é possível solicitar este alimento. Quantidade disponível insuficiente.");

        alimento.setQuantidadeReservada(alimento.getQuantidadeReservada() + solicitacaoDto.quantidade());
        alimentoService.salvarInstancia(alimento);

        Solicitacao solicitacao = SolicitacaoMapper.criar(idUsuario, receptor, alimento, solicitacaoDto);
        solicitacao.setReceptor(receptor);
        receptor.getSolicitacoesFeitas().add(solicitacao);

        return repository.save(solicitacao);
    }

    @Transactional
    public Solicitacao alterarSolicitacao(Long idUsuario, SolicitacaoRequestDTO solicitacaoDto) {
        Usuario receptor = buscarReceptorPorId(idUsuario);
        Solicitacao solicitacao = verificarSeSolicitacaoExiste(solicitacaoDto.id());
        Alimento alimento = solicitacao.getAlimento();

        if (!solicitacao.getReceptor().getId().equals(receptor.getId()))
            throw new RuntimeException("Apenas o Receptor dono da solicitação pode editá-la.");

        if (solicitacao.getStatus() != StatusSolicitacao.PENDENTE)
            throw new RuntimeException("A solicitação não pode mais ser alterada pois não está mais pendente.");

        if (solicitacaoDto.quantidade() != null) {
            int diferenca = solicitacaoDto.quantidade() - solicitacao.getQuantidade();
            int estoqueDisponivel = alimento.getQuantidadeTotal() - alimento.getQuantidadeReservada();

            if (diferenca > estoqueDisponivel)
                throw new RuntimeException("Quantidade indisponível no estoque. Disponível: " + estoqueDisponivel);

            alimento.setQuantidadeReservada(alimento.getQuantidadeReservada() + diferenca);
            alimentoService.salvarInstancia(alimento);
        }

        SolicitacaoMapper.alterar(receptor, solicitacaoDto, solicitacao);

        return repository.save(solicitacao);
    }

    @Transactional
    public void aprovarSolicitacao(Long idSolicitacao, Long idUsuario) {
        Usuario doador = buscarDoadorPorId(idUsuario);
        Solicitacao solicitacao = verificarSeSolicitacaoExiste(idSolicitacao);
        Alimento alimento = solicitacao.getAlimento();

        if (!alimento.getDoador().getId().equals(doador.getId()))
            throw new RuntimeException("Operação negada: Você só pode aprovar solicitações dos seus próprios alimentos.");

        if (solicitacao.getStatus() != StatusSolicitacao.PENDENTE)
            throw new RuntimeException("Esta solicitação já foi processada (Status: " + solicitacao.getStatus() + ")");

        if (solicitacao.getQuantidade() > alimento.getQuantidadeTotal())
            throw new RuntimeException("Não é possível aprovar: quantidade solicitada é maior que o estoque atual disponível.");

        solicitacao.setDoador(doador);
        doador.getSolicitacoesGerenciadas().add(solicitacao);

        solicitacao.setStatus(StatusSolicitacao.APROVADA);
        solicitacao.setDataAprovacao(LocalDate.now());

        if (alimento.getQuantidadeReservada().equals(alimento.getQuantidadeTotal())) {
            alimento.setStatus(StatusAlimento.FINALIZADO);
        }

        alimentoService.salvarInstancia(alimento);
        repository.save(solicitacao);
    }

    @Transactional
    public void recusarSolicitacao(Long idSolicitacao, Long idUsuario) {
        Usuario doador = buscarDoadorPorId(idUsuario);
        Solicitacao solicitacao = verificarSeSolicitacaoExiste(idSolicitacao);
        Alimento alimento = solicitacao.getAlimento();

        if (!alimento.getDoador().getId().equals(doador.getId()))
            throw new RuntimeException("Operação negada: Você só pode recusar solicitações dos seus próprios alimentos.");

        if (solicitacao.getStatus() != StatusSolicitacao.PENDENTE)
            throw new RuntimeException("Status inválido para recusa.");

        alimento.setQuantidadeReservada(alimento.getQuantidadeReservada() - solicitacao.getQuantidade());

        if (alimento.getStatus() == StatusAlimento.FINALIZADO && alimento.getQuantidadeTotal() > 0)
            alimento.setStatus(StatusAlimento.DISPONIVEL);

        doador.getSolicitacoesGerenciadas().add(solicitacao);

        alimentoService.salvarInstancia(alimento);
        solicitacao.setStatus(StatusSolicitacao.RECUSADA);
        repository.save(solicitacao);
    }

    @Transactional
    public void cancelarSolicitacao(Long idUsuario, Long idSolicitacao) {
        Usuario receptor = buscarReceptorPorId(idUsuario);
        Solicitacao solicitacao = verificarSeSolicitacaoExiste(idSolicitacao);

        if (!solicitacao.getReceptor().getId().equals(receptor.getId()))
            throw new RuntimeException("Apenas o criador da solicitação pode cancelá-la.");

        if (solicitacao.getStatus() != StatusSolicitacao.PENDENTE)
            throw new RuntimeException("Solicitações aprovadas ou concluídas não podem ser canceladas.");

        Alimento alimento = solicitacao.getAlimento();
        alimento.setQuantidadeReservada(alimento.getQuantidadeReservada() - solicitacao.getQuantidade());

        if (alimento.getStatus() == StatusAlimento.FINALIZADO && alimento.getQuantidadeTotal() > 0)
            alimento.setStatus(StatusAlimento.DISPONIVEL);

        receptor.getSolicitacoesFeitas().add(solicitacao);

        alimentoService.salvarInstancia(alimento);
        solicitacao.setStatus(StatusSolicitacao.CANCELADA);
        repository.save(solicitacao);
    }

    @Transactional
    public void concluirSolicitacao(Long idUsuario, Long idSolicitacao) {
        Usuario receptor = buscarReceptorPorId(idUsuario);

        Solicitacao solicitacao = verificarSeSolicitacaoExiste(idSolicitacao);
        Alimento alimento = solicitacao.getAlimento();

        if (!solicitacao.getReceptor().getId().equals(receptor.getId()))
            throw new RuntimeException("Apenas o criador da solicitação pode concluiá-la.");

        if (solicitacao.getStatus() != StatusSolicitacao.APROVADA)
            throw new RuntimeException("A doação precisa estar aprovada para ser concluída.");

        alimento.setQuantidadeTotal(alimento.getQuantidadeTotal() - solicitacao.getQuantidade());
        alimento.setQuantidadeReservada(alimento.getQuantidadeReservada() - solicitacao.getQuantidade());

        solicitacao.setStatus(StatusSolicitacao.CONCLUIDA);
        solicitacao.setDataConclusao(LocalDate.now());

        if (alimento.getQuantidadeTotal() == 0)
            alimento.setStatus(StatusAlimento.FINALIZADO);

        receptor.getSolicitacoesFeitas().add(solicitacao);

        alimentoService.salvarInstancia(alimento);
        repository.save(solicitacao);
    }

    private Usuario buscarReceptorPorId(Long id) {
        Usuario usuario = usuarioService.verificarSeUsuarioExiste(id);

        if (!usuario.getTipo().equals(TipoUsuario.RECEPTOR))
            throw new RuntimeException("Apenas usuários do tipo RECEPTOR podem realizar essa ação.");

        return usuario;
    }

    private Usuario buscarDoadorPorId(Long id) {
        Usuario usuario = usuarioService.verificarSeUsuarioExiste(id);

        if (!usuario.getTipo().equals(TipoUsuario.DOADOR))
            throw new RuntimeException("Apenas usuários do tipo DOADOR  podem realizar essa ação.");

        return usuario;
    }

    private Solicitacao verificarSeSolicitacaoExiste(Long id) {
        return repository.findById(id).
                orElseThrow(() -> new RuntimeException("Solicitação não encontrada!"));
    }

    public List<SolicitacaoResponseDTO> listarPendentesPorDoador(Long doadorId) {
        return repository.findByAlimentoDoadorIdAndStatus(doadorId, StatusSolicitacao.PENDENTE)
                .stream()
                .map(SolicitacaoMapper::paraResponse)
                .toList();
    }

    public List<SolicitacaoResponseDTO> listarPorReceptor(Long receptorId) {
        return repository.findByReceptorId(receptorId)
                .stream()
                .map(SolicitacaoMapper::paraResponse)
                .toList();
    }
}
