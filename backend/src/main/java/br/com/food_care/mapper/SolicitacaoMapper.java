package br.com.food_care.mapper;

import br.com.food_care.dto.SolicitacaoRequestDTO;
import br.com.food_care.dto.SolicitacaoResponseDTO;
import br.com.food_care.entity.Alimento;
import br.com.food_care.entity.Solicitacao;
import br.com.food_care.entity.StatusSolicitacao;
import br.com.food_care.entity.Usuario;

import java.time.LocalDate;

public class SolicitacaoMapper {
    public static Solicitacao criar(Long idUsuario, Usuario receptor, Alimento alimento, SolicitacaoRequestDTO dto) {
        Solicitacao novaSolicitacao = new Solicitacao();

        novaSolicitacao.setQuantidade(dto.quantidade());
        novaSolicitacao.setStatus(StatusSolicitacao.PENDENTE);
        novaSolicitacao.setDataCriacao(LocalDate.now());
        novaSolicitacao.setDescricao(dto.descricao());
        novaSolicitacao.setReceptor(receptor);
        novaSolicitacao.setAlimento(alimento);

        return novaSolicitacao;

    }

    public static Solicitacao alterar(Usuario receptor, SolicitacaoRequestDTO dto, Solicitacao solicitacao) {
        if (dto.descricao() != null) solicitacao.setDescricao(dto.descricao());
        if (dto.quantidade() != null) solicitacao.setQuantidade(dto.quantidade());

        return solicitacao;
    }

    public static SolicitacaoResponseDTO paraResponse(Solicitacao solicitacao) {
        return new SolicitacaoResponseDTO(solicitacao.getId(),
                solicitacao.getReceptor().getId(),
                solicitacao.getReceptor().getNome(),
                solicitacao.getAlimento().getId(),
                solicitacao.getAlimento().getNome(),
                solicitacao.getQuantidade(),
                solicitacao.getDescricao(),
                solicitacao.getStatus().name(),
                solicitacao.getDataCriacao() != null ? solicitacao.getDataCriacao().toString() : null,
                solicitacao.getDataAprovacao() != null ? solicitacao.getDataAprovacao().toString() : null,
                solicitacao.getDataConclusao() != null ? solicitacao.getDataConclusao().toString() : null,
                solicitacao.getDoador() != null ? solicitacao.getDoador().getNome() : "Aguardando Doador"
        );
    }
}
