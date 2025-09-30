package com.ecommerce.entity;

import java.math.BigDecimal;

/**
 * Entidade para representar o resultado da análise de produtos por categoria
 */
public class AnaliseCategoria {
    
    private String categoria;
    private Integer totalProdutos;
    private Integer totalEstoque;
    private BigDecimal precoMedio;
    private BigDecimal valorTotalEstoque;
    
    // Construtores
    public AnaliseCategoria() {}
    
    public AnaliseCategoria(String categoria, Integer totalProdutos, Integer totalEstoque, 
                           BigDecimal precoMedio, BigDecimal valorTotalEstoque) {
        this.categoria = categoria;
        this.totalProdutos = totalProdutos;
        this.totalEstoque = totalEstoque;
        this.precoMedio = precoMedio;
        this.valorTotalEstoque = valorTotalEstoque;
    }
    
    // Getters e Setters
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public Integer getTotalProdutos() {
        return totalProdutos;
    }
    
    public void setTotalProdutos(Integer totalProdutos) {
        this.totalProdutos = totalProdutos;
    }
    
    public Integer getTotalEstoque() {
        return totalEstoque;
    }
    
    public void setTotalEstoque(Integer totalEstoque) {
        this.totalEstoque = totalEstoque;
    }
    
    public BigDecimal getPrecoMedio() {
        return precoMedio;
    }
    
    public void setPrecoMedio(BigDecimal precoMedio) {
        this.precoMedio = precoMedio;
    }
    
    public BigDecimal getValorTotalEstoque() {
        return valorTotalEstoque;
    }
    
    public void setValorTotalEstoque(BigDecimal valorTotalEstoque) {
        this.valorTotalEstoque = valorTotalEstoque;
    }
    
    @Override
    public String toString() {
        return "AnaliseCategoria{" +
                "categoria='" + categoria + '\'' +
                ", totalProdutos=" + totalProdutos +
                ", totalEstoque=" + totalEstoque +
                ", precoMedio=" + precoMedio +
                ", valorTotalEstoque=" + valorTotalEstoque +
                '}';
    }
}
