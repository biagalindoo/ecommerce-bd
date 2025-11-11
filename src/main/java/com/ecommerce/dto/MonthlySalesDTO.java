package com.ecommerce.dto;

import java.math.BigDecimal;

/**
 * Dados de vendas mensais.
 */
public class MonthlySalesDTO {

    private String periodo;
    private BigDecimal receita;
    private long pedidos;

    public MonthlySalesDTO() {
    }

    public MonthlySalesDTO(String periodo, BigDecimal receita, long pedidos) {
        this.periodo = periodo;
        this.receita = receita;
        this.pedidos = pedidos;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public BigDecimal getReceita() {
        return receita;
    }

    public void setReceita(BigDecimal receita) {
        this.receita = receita;
    }

    public long getPedidos() {
        return pedidos;
    }

    public void setPedidos(long pedidos) {
        this.pedidos = pedidos;
    }
}


