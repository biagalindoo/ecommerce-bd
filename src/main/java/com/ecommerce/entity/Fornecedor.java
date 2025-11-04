package com.ecommerce.entity;

/**
 * Entidade Fornecedor para representar dados do banco
 */
public class Fornecedor {
    
    private Integer id;
    private String email;
    private String nomeFantasia;
    private String cnpj;
    private String razaoSocial;
    
    public Fornecedor() {}
    
    public Fornecedor(Integer id, String email, String nomeFantasia, String cnpj, String razaoSocial) {
        this.id = id;
        this.email = email;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }
    
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
    
    public String getNomeFantasia() {
        return nomeFantasia;
    }
    
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getRazaoSocial() {
        return razaoSocial;
    }
    
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    
    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                '}';
    }
}


