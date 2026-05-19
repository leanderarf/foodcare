package br.com.food_care.dto;

public record AlimentoResponseDTO (Long id,
                                   String nome,
                                   Integer quantidadeTotal,
                                   Integer quantidadeReservada,
                                   String dataValidade,
                                   String status){
}
