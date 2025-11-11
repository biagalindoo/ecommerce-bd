package com.ecommerce.dto;

import java.math.BigDecimal;

/**
 * Quantidade fornecida por fornecedor.
 */
public class SupplierContributionDTO {

    private Integer fornecedorId;
    private String fornecedorNome;
    private long quantidadeTotal;
    private BigDecimal valorTotal;

    public Integer getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Integer fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public String getFornecedorNome() {
        return fornecedorNome;
    }

    public void setFornecedorNome(String fornecedorNome) {
        this.fornecedorNome = fornecedorNome;
    }

    public long getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(long quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}


