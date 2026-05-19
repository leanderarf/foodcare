package br.com.food_care.dto;

import br.com.food_care.entity.TipoUsuario;

public record LoginResponseDTO (Long id,
                               String nome,
                               String email,
                               String senha,
                               String tipoUsuario,
                               boolean logado){

    public TipoUsuario tipoUsuarioFormatado() {
        try {
            return TipoUsuario.valueOf(this.tipoUsuario.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Usuário inválido. Use 'DOADOR' ou 'RECEPTOR'");
        }
    }
}
