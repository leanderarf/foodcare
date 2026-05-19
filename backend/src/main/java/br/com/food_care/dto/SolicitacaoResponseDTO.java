package br.com.food_care.dto;

public record SolicitacaoResponseDTO (Long id,
                                      Long idReceptor,
                                      String nomeReceptor,
                                      Long idAlimento,
                                      String nomeAlimento,
                                      Integer quantidade,
                                      String descricao,
                                      String status,
                                      String dataCriacao,
                                      String dataAprovacao,
                                      String dataConclusao,
                                      String nomeDoador){
}
