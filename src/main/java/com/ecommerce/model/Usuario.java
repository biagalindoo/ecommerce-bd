package com.ecommerce.model;

/**
 * Modelo para a entidade Usuario
 * Representa os dados de um usuário do sistema
 */
public class Usuario {
    
    private int id;
    private String email;
    private String senhaHash;
    private String cpf;
    private String primeiroNome;
    private String sobrenome;
    private String dataNascimento;
    
    // Construtores
    public Usuario() {}
    
    public Usuario(String email, String senhaHash, String cpf, String primeiroNome, 
                   String sobrenome, String dataNascimento) {
        this.email = email;
        this.senhaHash = senhaHash;
        this.cpf = cpf;
        this.primeiroNome = primeiroNome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
    }
    
    // Getters e Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
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
    
    public String getDataNascimento() {
        return dataNascimento;
    }
    
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
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
                ", dataNascimento='" + dataNascimento + '\'' +
                '}';
    }
}
