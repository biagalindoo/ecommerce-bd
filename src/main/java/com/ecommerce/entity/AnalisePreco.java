package com.ecommerce.entity;

import java.math.BigDecimal;

/**
 * Entidade para representar análise de produtos por faixa de preço
 */
public class AnalisePreco {
    
    private String faixaPreco;
    private Integer totalProdutos;
    private BigDecimal precoMedio;
    private Integer totalEstoque;
    
    // Construtores
    public AnalisePreco() {}
    
    public AnalisePreco(String faixaPreco, Integer totalProdutos, BigDecimal precoMedio, Integer totalEstoque) {
        this.faixaPreco = faixaPreco;
        this.totalProdutos = totalProdutos;
        this.precoMedio = precoMedio;
        this.totalEstoque = totalEstoque;
    }
    
    // Getters e Setters
    public String getFaixaPreco() {
        return faixaPreco;
    }
    
    public void setFaixaPreco(String faixaPreco) {
        this.faixaPreco = faixaPreco;
    }
    
    public Integer getTotalProdutos() {
        return totalProdutos;
    }
    
    public void setTotalProdutos(Integer totalProdutos) {
        this.totalProdutos = totalProdutos;
    }
    
    public BigDecimal getPrecoMedio() {
        return precoMedio;
    }
    
    public void setPrecoMedio(BigDecimal precoMedio) {
        this.precoMedio = precoMedio;
    }
    
    public Integer getTotalEstoque() {
        return totalEstoque;
    }
    
    public void setTotalEstoque(Integer totalEstoque) {
        this.totalEstoque = totalEstoque;
    }
    
    @Override
    public String toString() {
        return "AnalisePreco{" +
                "faixaPreco='" + faixaPreco + '\'' +
                ", totalProdutos=" + totalProdutos +
                ", precoMedio=" + precoMedio +
                ", totalEstoque=" + totalEstoque +
                '}';
    }
}
