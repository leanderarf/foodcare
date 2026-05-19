package br.com.food_care.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 11, updatable = false, nullable = true)
    @CPF
    private String cpf;

    @Column(unique = true, length = 14, updatable = false, nullable = true)
    @CNPJ
    private String cnpj;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, name = "data_nascimento")
    @Past
    private LocalDate dataNascimento;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(length = 15)
    private String telefone;

    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    @Column(nullable = false)
    private boolean ativo;

    @Transient
    private boolean logado;

    @OneToMany(mappedBy = "receptor", cascade = CascadeType.ALL)
    private List<Solicitacao> solicitacoesFeitas = new ArrayList<>();

    @OneToMany(mappedBy = "doador", cascade = CascadeType.ALL)
    private List<Solicitacao> solicitacoesGerenciadas = new ArrayList<>();

    public Usuario(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Solicitacao> getSolicitacoesFeitas() {
        return solicitacoesFeitas;
    }

    public void setSolicitacoesFeitas(List<Solicitacao> historico) { this.solicitacoesFeitas = historico; }

    public List<Solicitacao> getSolicitacoesGerenciadas() { return solicitacoesFeitas; }

    public void setSolicitacoesGerenciadas(List<Solicitacao> historico) { this.solicitacoesFeitas = historico; }

    public void setLogado(boolean status) { this.logado = status; }

    public boolean isLogado() { return this.logado; }

    public void logout() { this.logado = false; }
}
