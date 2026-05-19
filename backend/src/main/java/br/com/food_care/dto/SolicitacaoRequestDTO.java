package br.com.food_care.dto;

import br.com.food_care.entity.Alimento;
import br.com.food_care.entity.StatusSolicitacao;
import br.com.food_care.entity.Usuario;

public record SolicitacaoRequestDTO (Long id,
                                     Usuario receptor,
                                     Usuario doador,
                                     Alimento alimento,
                                     String descricao,
                                     Integer quantidade,
                                     String status) {

    public StatusSolicitacao statusSolicitacaoFormatado() {
        try {
            return StatusSolicitacao.valueOf(this.status.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Status da Solicitação inválido. " +
                    "Use 'PENDENTE', 'APROVADA', 'RECUSADA', 'CANCELADA' ou 'CONCLUIDA'");
        }
    }
}
