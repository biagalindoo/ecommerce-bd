package com.ecommerce.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Entidade Fornecedor para JPA
 */
@Entity
@Table(name = "Fornecedor")
public class Fornecedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome fantasia é obrigatório")
    @Size(max = 100, message = "Nome fantasia deve ter no máximo 100 caracteres")
    @Column(name = "nome_fantasia", nullable = false, length = 100)
    private String nomeFantasia;
    
    @NotBlank(message = "Razão social é obrigatória")
    @Size(max = 200, message = "Razão social deve ter no máximo 200 caracteres")
    @Column(name = "razao_social", nullable = false, length = 200)
    private String razaoSocial;
    
    @Size(max = 20, message = "CNPJ deve ter no máximo 20 caracteres")
    @Column(name = "cnpj", length = 20)
    private String cnpj;
    
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    @Column(name = "email", length = 100)
    private String email;
    
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    @Column(name = "telefone", length = 20)
    private String telefone;
    
    // Construtores
    public Fornecedor() {}
    
    public Fornecedor(String nomeFantasia, String razaoSocial, String cnpj, String email, String telefone) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNomeFantasia() {
        return nomeFantasia;
    }
    
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
    
    public String getRazaoSocial() {
        return razaoSocial;
    }
    
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
