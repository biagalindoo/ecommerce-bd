package com.ecommerce.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade Carrinho para JPA
 */
@Entity
@Table(name = "Carrinho")
public class Carrinho {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    // Construtores
    public Carrinho() {
        this.dataCriacao = LocalDateTime.now();
    }
    
    public Carrinho(Usuario usuario) {
        this();
        this.usuario = usuario;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public String toString() {
        return "Carrinho{" +
                "id=" + id +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
