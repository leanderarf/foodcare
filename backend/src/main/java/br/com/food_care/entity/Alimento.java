package br.com.food_care.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alimentos")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 250)
    private String descricao;

    @Column(name = "quantidade_total", nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer quantidadeTotal;

    @Column(name = "quantidade_reservada", columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Integer quantidadeReservada = 0;

    @Column(name = "data_validade", nullable = false)
    private LocalDate dataValidade;

    @Enumerated(EnumType.STRING)
    private StatusAlimento status;

    @Enumerated(EnumType.STRING)
    private PrioridadeAlimento prioridade;

    @ManyToOne
    private Usuario doador;

    @OneToMany(mappedBy = "alimento", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<Solicitacao> solicitacoes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(Integer quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public Integer getQuantidadeReservada() {
        return quantidadeReservada;
    }

    public void setQuantidadeReservada(Integer quantidadeReservada) {
        this.quantidadeReservada = quantidadeReservada;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public StatusAlimento getStatus() {
        return status;
    }

    public void setStatus(StatusAlimento status) {
        this.status = status;
    }

    public PrioridadeAlimento getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(PrioridadeAlimento prioridade) {
        this.prioridade = prioridade;
    }

    public Usuario getDoador() {
        return doador;
    }

    public void setDoador(Usuario doador) {
        this.doador = doador;
    }

    public List<Solicitacao> getSolicitacoes() {
        return solicitacoes;
    }

    public void setSolicitacoes(List<Solicitacao> solicitacoes) {
        this.solicitacoes = solicitacoes;
    }
}
