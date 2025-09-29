package com.ecommerce.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Entidade ItemCarrinho para JPA
 */
@Entity
@Table(name = "ItemCarrinho")
public class ItemCarrinho {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrinho_id", nullable = false)
    private Carrinho carrinho;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
    
    // Construtores
    public ItemCarrinho() {}
    
    public ItemCarrinho(Integer quantidade, Carrinho carrinho, Produto produto) {
        this.quantidade = quantidade;
        this.carrinho = carrinho;
        this.produto = produto;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    
    public Carrinho getCarrinho() {
        return carrinho;
    }
    
    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }
    
    public Produto getProduto() {
        return produto;
    }
    
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    @Override
    public String toString() {
        return "ItemCarrinho{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                '}';
    }
}
