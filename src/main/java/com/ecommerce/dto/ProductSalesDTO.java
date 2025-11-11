package com.ecommerce.dto;

import java.math.BigDecimal;

/**
 * Representa vendas agregadas por produto.
 */
public class ProductSalesDTO {

    private Integer produtoId;
    private String nome;
    private long quantidadeVendida;
    private BigDecimal receitaGerada;

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(long quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    public BigDecimal getReceitaGerada() {
        return receitaGerada;
    }

    public void setReceitaGerada(BigDecimal receitaGerada) {
        this.receitaGerada = receitaGerada;
    }
}


