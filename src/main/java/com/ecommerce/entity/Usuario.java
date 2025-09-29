package com.ecommerce.entity;

import java.time.LocalDate;

/**
 * Entidade Usuario para representar dados do banco
 */
public class Usuario {
    
    private Integer id;
    private String email;
    private String senhaHash;
    private String cpf;
    private String primeiroNome;
    private String sobrenome;
    private LocalDate dataNascimento;
    
    // Construtores
    public Usuario() {}
    
    public Usuario(Integer id, String email, String senhaHash, String cpf, 
                   String primeiroNome, String sobrenome, LocalDate dataNascimento) {
        this.id = id;
        this.email = email;
        this.senhaHash = senhaHash;
        this.cpf = cpf;
        this.primeiroNome = primeiroNome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
    }
    
    // Getters e Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSenhaHash() {
        return senhaHash;
    }
    
    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getPrimeiroNome() {
        return primeiroNome;
    }
    
    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }
    
    public String getSobrenome() {
        return sobrenome;
    }
    
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    // Métodos auxiliares
    public String getNomeCompleto() {
        return primeiroNome + " " + sobrenome;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", primeiroNome='" + primeiroNome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", dataNascimento=" + dataNascimento +
                '}';
    }
}