package br.com.food_care.entity;

public enum Sexo {
    MASCULINO ('M'),
    FEMININO ('F');

    private final char sigla;

    Sexo (char sigla) {
        this.sigla = sigla;
    }

    public char getSigla() { return sigla; }
}
