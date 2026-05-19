package br.com.food_care.dto;


import br.com.food_care.entity.StatusAlimento;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public record AlimentoRequestDTO (Long id,
                                  String nome,
                                  String descricao,
                                  Integer quantidadeTotal,
                                  Integer quantidadeReservada,
                                  String dataValidade,
                                  String status,
                                  String prioridade,
                                  Long idUsuario) {

    public LocalDate dataValidadeFormatada() {
        try {
            LocalDate dataFormatada = LocalDate.parse(this.dataValidade);

            if (dataFormatada.isBefore(LocalDate.now())) {
                throw new RuntimeException("Não é permitido cadastrar alimentos vencidos.");
            } else {
                return dataFormatada;
            }
        } catch (DateTimeParseException | NullPointerException e) {
            throw new IllegalArgumentException("Data inválida. Use o formato yyyy-MM-dd");
        }
    }

    public StatusAlimento statusAlimentoFormatado() {
        try {
            return StatusAlimento.valueOf(this.status.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Status de Alimento inválido. " +
                    "Use 'DISPONIVEL','RESERVADO', 'ESGOTADO' ou 'FINALIZADO'");
        }
    }

}
