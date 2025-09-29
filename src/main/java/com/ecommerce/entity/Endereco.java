package com.ecommerce.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Entidade Endereco para JPA
 */
@Entity
@Table(name = "Endereco")
public class Endereco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Rua é obrigatória")
    @Size(max = 100, message = "Rua deve ter no máximo 100 caracteres")
    @Column(name = "rua", nullable = false, length = 100)
    private String rua;
    
    @NotBlank(message = "Número é obrigatório")
    @Size(max = 10, message = "Número deve ter no máximo 10 caracteres")
    @Column(name = "numero", nullable = false, length = 10)
    private String numero;
    
    @Size(max = 50, message = "Bairro deve ter no máximo 50 caracteres")
    @Column(name = "bairro", length = 50)
    private String bairro;
    
    @NotBlank(message = "Cidade é obrigatória")
    @Size(max = 50, message = "Cidade deve ter no máximo 50 caracteres")
    @Column(name = "cidade", nullable = false, length = 50)
    private String cidade;
    
    @NotBlank(message = "Estado é obrigatório")
    @Size(max = 2, message = "Estado deve ter 2 caracteres")
    @Column(name = "estado", nullable = false, length = 2)
    private String estado;
    
    @Size(max = 10, message = "CEP deve ter no máximo 10 caracteres")
    @Column(name = "cep", length = 10)
    private String cep;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    // Construtores
    public Endereco() {}
    
    public Endereco(String rua, String numero, String bairro, String cidade, String estado, String cep, Usuario usuario) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.usuario = usuario;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRua() {
        return rua;
    }
    
    public void setRua(String rua) {
        this.rua = rua;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getBairro() {
        return bairro;
    }
    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getCep() {
        return cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    // Métodos auxiliares
    public String getEnderecoCompleto() {
        return rua + ", " + numero + " - " + bairro + ", " + cidade + "/" + estado;
    }
    
    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", rua='" + rua + '\'' +
                ", numero='" + numero + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", cep='" + cep + '\'' +
                '}';
    }
}
