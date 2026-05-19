package br.com.food_care.dto;

public record UsuarioResponseDTO (Long id,
                                  String nome,
                                  String email,
                                  String tipo,
                                  boolean ativo){
}
