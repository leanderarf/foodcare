package br.com.food_care.entity;

public record Endereco(String rua,
                       Integer numero,
                       String bairro,
                       String cidade,
                       String estado) {
}
