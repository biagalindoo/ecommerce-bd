package com.ecommerce.entity;

import java.math.BigDecimal;

/**
 * Entidade Produto para representar dados do banco
 */
public class Produto {
    
    private Integer id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    private Integer armazemId;
    private String categoria;
    
    // Construtores
    public Produto() {}
    
    public Produto(Integer id, String nome, String descricao, BigDecimal preco, 
                   Integer quantidadeEstoque, Integer armazemId, String categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.armazemId = armazemId;
        this.categoria = categoria;
    }
    
    // Getters e Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
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
    
    public BigDecimal getPreco() {
        return preco;
    }
    
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
    
    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    
    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
    
    public Integer getArmazemId() {
        return armazemId;
    }
    
    public void setArmazemId(Integer armazemId) {
        this.armazemId = armazemId;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", quantidadeEstoque=" + quantidadeEstoque +
                ", armazemId=" + armazemId +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}