package br.com.food_care.dto;

import br.com.food_care.entity.Sexo;
import br.com.food_care.entity.TipoUsuario;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public record UsuarioRequestDTO(Long id,
                                String cpf,
                                String cnpj,
                                String nome,
                                String dataNascimento,
                                String email,
                                String senha,
                                String telefone,
                                String rua, Integer numero, String bairro, String cidade, String estado,
                                String sexo,
                                String tipoUsuario) {
    public LocalDate dataNascimentoFormatada() {
        try {
            return LocalDate.parse(this.dataNascimento);
        } catch (DateTimeParseException | NullPointerException e) {
            throw new IllegalArgumentException("Data inválida. Use o formato yyyy-MM-dd");
        }
    }

    public Sexo sexoFormatado() {
        try {
            return Sexo.valueOf(this.sexo.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Sexo inválido. Use 'MASCULINO' ou 'FEMININO'");
        }
    }

    public TipoUsuario tipoUsuarioFormatado() {
        try {
            return TipoUsuario.valueOf(this.tipoUsuario.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Usuário inválido. Use 'DOADOR' ou 'RECEPTOR'");
        }
    }
}
