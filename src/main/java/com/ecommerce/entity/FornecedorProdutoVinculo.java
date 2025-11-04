package com.ecommerce.entity;

import java.math.BigDecimal;

/**
 * DTO simples para exibir e manipular vínculo Fornecedor-Produto
 */
public class FornecedorProdutoVinculo {
    private Integer fornecedorId;
    private Integer produtoId;
    private Integer quantidadeFornecida;
    private BigDecimal custoUnitarioCompra;
    private String razaoSocial;
    private String nomeFantasia;

    public Integer getFornecedorId() { return fornecedorId; }
    public void setFornecedorId(Integer fornecedorId) { this.fornecedorId = fornecedorId; }
    public Integer getProdutoId() { return produtoId; }
    public void setProdutoId(Integer produtoId) { this.produtoId = produtoId; }
    public Integer getQuantidadeFornecida() { return quantidadeFornecida; }
    public void setQuantidadeFornecida(Integer quantidadeFornecida) { this.quantidadeFornecida = quantidadeFornecida; }
    public BigDecimal getCustoUnitarioCompra() { return custoUnitarioCompra; }
    public void setCustoUnitarioCompra(BigDecimal custoUnitarioCompra) { this.custoUnitarioCompra = custoUnitarioCompra; }
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
}


