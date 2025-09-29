package com.ecommerce.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidade Usuario para JPA
 */
@Entity
@Table(name = "Usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Primeiro nome é obrigatório")
    @Size(max = 50, message = "Primeiro nome deve ter no máximo 50 caracteres")
    @Column(name = "primeiro_nome", nullable = false, length = 50)
    private String primeiroNome;
    
    @NotBlank(message = "Sobrenome é obrigatório")
    @Size(max = 50, message = "Sobrenome deve ter no máximo 50 caracteres")
    @Column(name = "sobrenome", nullable = false, length = 50)
    private String sobrenome;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    // Relacionamentos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Telefone> telefones;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Endereco> enderecos;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pedido> pedidos;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Carrinho> carrinhos;
    
    // Construtores
    public Usuario() {
        this.dataCadastro = LocalDateTime.now();
    }
    
    public Usuario(String primeiroNome, String sobrenome, String email) {
        this();
        this.primeiroNome = primeiroNome;
        this.sobrenome = sobrenome;
        this.email = email;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
    
    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
    public List<Telefone> getTelefones() {
        return telefones;
    }
    
    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }
    
    public List<Endereco> getEnderecos() {
        return enderecos;
    }
    
    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
    
    public List<Pedido> getPedidos() {
        return pedidos;
    }
    
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    
    public List<Carrinho> getCarrinhos() {
        return carrinhos;
    }
    
    public void setCarrinhos(List<Carrinho> carrinhos) {
        this.carrinhos = carrinhos;
    }
    
    // Métodos auxiliares
    public String getNomeCompleto() {
        return primeiroNome + " " + sobrenome;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", primeiroNome='" + primeiroNome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}
