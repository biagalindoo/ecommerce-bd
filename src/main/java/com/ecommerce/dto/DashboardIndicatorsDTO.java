package com.ecommerce.dto;

import java.math.BigDecimal;

/**
 * Indicadores resumidos exibidos no dashboard.
 */
public class DashboardIndicatorsDTO {

    private long totalUsuarios;
    private long totalProdutos;
    private long totalPedidos;
    private long pedidosPeriodo;
    private long totalFornecedores;
    private long produtosEstoqueBaixo;
    private BigDecimal receitaTotal;
    private BigDecimal ticketMedio;

    public long getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(long totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }

    public long getTotalProdutos() {
        return totalProdutos;
    }

    public void setTotalProdutos(long totalProdutos) {
        this.totalProdutos = totalProdutos;
    }

    public long getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(long totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public long getPedidosPeriodo() {
        return pedidosPeriodo;
    }

    public void setPedidosPeriodo(long pedidosPeriodo) {
        this.pedidosPeriodo = pedidosPeriodo;
    }

    public long getTotalFornecedores() {
        return totalFornecedores;
    }

    public void setTotalFornecedores(long totalFornecedores) {
        this.totalFornecedores = totalFornecedores;
    }

    public long getProdutosEstoqueBaixo() {
        return produtosEstoqueBaixo;
    }

    public void setProdutosEstoqueBaixo(long produtosEstoqueBaixo) {
        this.produtosEstoqueBaixo = produtosEstoqueBaixo;
    }

    public BigDecimal getReceitaTotal() {
        return receitaTotal;
    }

    public void setReceitaTotal(BigDecimal receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    public BigDecimal getTicketMedio() {
        return ticketMedio;
    }

    public void setTicketMedio(BigDecimal ticketMedio) {
        this.ticketMedio = ticketMedio;
    }
}


