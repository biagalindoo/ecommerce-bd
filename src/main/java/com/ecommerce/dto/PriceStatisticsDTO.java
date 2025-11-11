package com.ecommerce.dto;

import java.math.BigDecimal;

/**
 * Métricas estatísticas para preços de produtos.
 */
public class PriceStatisticsDTO {

    private BigDecimal media;
    private BigDecimal mediana;
    private BigDecimal moda;
    private BigDecimal variancia;
    private BigDecimal desvioPadrao;

    public BigDecimal getMedia() {
        return media;
    }

    public void setMedia(BigDecimal media) {
        this.media = media;
    }

    public BigDecimal getMediana() {
        return mediana;
    }

    public void setMediana(BigDecimal mediana) {
        this.mediana = mediana;
    }

    public BigDecimal getModa() {
        return moda;
    }

    public void setModa(BigDecimal moda) {
        this.moda = moda;
    }

    public BigDecimal getVariancia() {
        return variancia;
    }

    public void setVariancia(BigDecimal variancia) {
        this.variancia = variancia;
    }

    public BigDecimal getDesvioPadrao() {
        return desvioPadrao;
    }

    public void setDesvioPadrao(BigDecimal desvioPadrao) {
        this.desvioPadrao = desvioPadrao;
    }
}


