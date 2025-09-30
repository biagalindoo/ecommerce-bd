package com.ecommerce.entity;

import java.math.BigDecimal;

/**
 * Entidade para representar produto com responsável (JOIN)
 */
public class ProdutoResponsavel {
    
    private String produto;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    private Integer armazemId;
    private String responsavelArmazem;
    private String emailResponsavel;
    
    // Construtores
    public ProdutoResponsavel() {}
    
    public ProdutoResponsavel(String produto, BigDecimal preco, Integer quantidadeEstoque, 
                             Integer armazemId, String responsavelArmazem, String emailResponsavel) {
        this.produto = produto;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.armazemId = armazemId;
        this.responsavelArmazem = responsavelArmazem;
        this.emailResponsavel = emailResponsavel;
    }
    
    // Getters e Setters
    public String getProduto() {
        return produto;
    }
    
    public void setProduto(String produto) {
        this.produto = produto;
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
    
    public String getResponsavelArmazem() {
        return responsavelArmazem;
    }
    
    public void setResponsavelArmazem(String responsavelArmazem) {
        this.responsavelArmazem = responsavelArmazem;
    }
    
    public String getEmailResponsavel() {
        return emailResponsavel;
    }
    
    public void setEmailResponsavel(String emailResponsavel) {
        this.emailResponsavel = emailResponsavel;
    }
    
    @Override
    public String toString() {
        return "ProdutoResponsavel{" +
                "produto='" + produto + '\'' +
                ", preco=" + preco +
                ", quantidadeEstoque=" + quantidadeEstoque +
                ", armazemId=" + armazemId +
                ", responsavelArmazem='" + responsavelArmazem + '\'' +
                ", emailResponsavel='" + emailResponsavel + '\'' +
                '}';
    }
}
