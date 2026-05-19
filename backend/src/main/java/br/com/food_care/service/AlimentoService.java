package br.com.food_care.service;

import br.com.food_care.dto.AlimentoRequestDTO;
import br.com.food_care.dto.AlimentoResponseDTO;
import br.com.food_care.entity.Alimento;
import br.com.food_care.entity.StatusAlimento;
import br.com.food_care.entity.TipoUsuario;
import br.com.food_care.entity.Usuario;
import br.com.food_care.mapper.AlimentoMapper;
import br.com.food_care.repository.AlimentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public Alimento salvarAlimento(Long idUsuario, AlimentoRequestDTO dto) {
        Usuario doador = buscaPorDoadorPorId(idUsuario);
        Alimento alimento = AlimentoMapper.criar(dto, doador);
        return repository.save(alimento);
    }

    @Transactional
    public Alimento atualizarAlimento(Long idUsuario, String nomeAlimento, AlimentoRequestDTO alimentoDto) {
        Usuario doador = buscaPorDoadorPorId(idUsuario);
        Alimento alimento = repository.findByNomeIgnoreCaseAndDoadorId(nomeAlimento, doador.getId())
                .orElseThrow(() -> new RuntimeException("Alimento '" + nomeAlimento + "' não encontrado para este doador"));

        AlimentoMapper.atualizar(alimentoDto, alimento);
        return repository.save(alimento);
    }

    @Transactional
    public void excluirAlimento(Long idUsuario, String senhaConfirmacao, String nomeAlimento) {
        Usuario doador = buscaPorDoadorPorId(idUsuario);
        Alimento alimento = repository.findByNomeIgnoreCaseAndDoadorId(nomeAlimento, doador.getId())
                .orElseThrow(() -> new RuntimeException("Alimento '" + nomeAlimento + "' não encontrado para este doador"));

        if (!doador.getSenha().equals(senhaConfirmacao))
            throw new RuntimeException("Senha de confirmação incorreta. Operação cancelada.");

        if (!alimento.getSolicitacoes().isEmpty()) {
            alimento.setStatus(StatusAlimento.FINALIZADO);
            repository.save(alimento);
        } else {
            repository.delete(alimento);
        }
    }

    @Transactional
    public void salvarInstancia(Alimento alimento) { repository.save(alimento); }

    public Alimento validarAlimentoExistente(Long id) {
        return repository.findByIdWithLock(id).
                orElseThrow(() -> new RuntimeException("Alimento não encontrado!"));
    }

    private Usuario buscaPorDoadorPorId(Long id) {
        Usuario usuarioExistente = usuarioService.verificarSeUsuarioExiste(id);

        //Usuario usuarioExistente = usuarioRepository.findById(id)
         //       .orElseThrow(() -> new RuntimeException("Usuário não cadatsrado."));

        if (!usuarioExistente.getTipo().equals(TipoUsuario.DOADOR))
            throw new RuntimeException("Apenas usuários do tipo DOADOR podem cadastrar alimentos.");

        return usuarioExistente;
    }

    public List<AlimentoResponseDTO> listarDisponiveisPorValidade() {
        List<Alimento> alimentos = repository.findAllByStatusOrderByDataValidadeAsc(StatusAlimento.DISPONIVEL);

        return alimentos.stream()
                .map(AlimentoMapper::paraResponse)
                .toList();
    }
}
