package com.ecommerce.dto;

/**
 * Distribuição de estoque por faixas.
 */
public class StockDistributionDTO {

    private long esgotados;
    private long baixo;
    private long adequado;
    private long alto;

    public long getEsgotados() {
        return esgotados;
    }

    public void setEsgotados(long esgotados) {
        this.esgotados = esgotados;
    }

    public long getBaixo() {
        return baixo;
    }

    public void setBaixo(long baixo) {
        this.baixo = baixo;
    }

    public long getAdequado() {
        return adequado;
    }

    public void setAdequado(long adequado) {
        this.adequado = adequado;
    }

    public long getAlto() {
        return alto;
    }

    public void setAlto(long alto) {
        this.alto = alto;
    }
}


