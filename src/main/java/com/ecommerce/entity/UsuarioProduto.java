package com.ecommerce.entity;

import java.math.BigDecimal;

/**
 * Entidade para representar usuário com análise de produtos gerenciados
 */
public class UsuarioProduto {
    
    private Integer id;
    private String nomeCompleto;
    private String email;
    private String faixaEtaria;
    private Integer idade;
    private Integer produtosGerenciados;
    private BigDecimal valorTotalProdutos;
    
    // Construtores
    public UsuarioProduto() {}
    
    public UsuarioProduto(Integer id, String nomeCompleto, String email, String faixaEtaria, 
                          Integer idade, Integer produtosGerenciados, BigDecimal valorTotalProdutos) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.faixaEtaria = faixaEtaria;
        this.idade = idade;
        this.produtosGerenciados = produtosGerenciados;
        this.valorTotalProdutos = valorTotalProdutos;
    }
    
    // Getters e Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNomeCompleto() {
        return nomeCompleto;
    }
    
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFaixaEtaria() {
        return faixaEtaria;
    }
    
    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }
    
    public Integer getIdade() {
        return idade;
    }
    
    public void setIdade(Integer idade) {
        this.idade = idade;
    }
    
    public Integer getProdutosGerenciados() {
        return produtosGerenciados;
    }
    
    public void setProdutosGerenciados(Integer produtosGerenciados) {
        this.produtosGerenciados = produtosGerenciados;
    }
    
    public BigDecimal getValorTotalProdutos() {
        return valorTotalProdutos;
    }
    
    public void setValorTotalProdutos(BigDecimal valorTotalProdutos) {
        this.valorTotalProdutos = valorTotalProdutos;
    }
    
    @Override
    public String toString() {
        return "UsuarioProduto{" +
                "id=" + id +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", email='" + email + '\'' +
                ", faixaEtaria='" + faixaEtaria + '\'' +
                ", idade=" + idade +
                ", produtosGerenciados=" + produtosGerenciados +
                ", valorTotalProdutos=" + valorTotalProdutos +
                '}';
    }
}
