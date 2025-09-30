package com.ecommerce.entity;

/**
 * Entidade para representar análise de usuários por faixa etária
 */
public class AnaliseIdade {
    
    private String faixaEtaria;
    private Integer totalUsuarios;
    private Double idadeMedia;
    private Integer idadeMinima;
    private Integer idadeMaxima;
    
    // Construtores
    public AnaliseIdade() {}
    
    public AnaliseIdade(String faixaEtaria, Integer totalUsuarios, Double idadeMedia, 
                        Integer idadeMinima, Integer idadeMaxima) {
        this.faixaEtaria = faixaEtaria;
        this.totalUsuarios = totalUsuarios;
        this.idadeMedia = idadeMedia;
        this.idadeMinima = idadeMinima;
        this.idadeMaxima = idadeMaxima;
    }
    
    // Getters e Setters
    public String getFaixaEtaria() {
        return faixaEtaria;
    }
    
    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }
    
    public Integer getTotalUsuarios() {
        return totalUsuarios;
    }
    
    public void setTotalUsuarios(Integer totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }
    
    public Double getIdadeMedia() {
        return idadeMedia;
    }
    
    public void setIdadeMedia(Double idadeMedia) {
        this.idadeMedia = idadeMedia;
    }
    
    public Integer getIdadeMinima() {
        return idadeMinima;
    }
    
    public void setIdadeMinima(Integer idadeMinima) {
        this.idadeMinima = idadeMinima;
    }
    
    public Integer getIdadeMaxima() {
        return idadeMaxima;
    }
    
    public void setIdadeMaxima(Integer idadeMaxima) {
        this.idadeMaxima = idadeMaxima;
    }
    
    @Override
    public String toString() {
        return "AnaliseIdade{" +
                "faixaEtaria='" + faixaEtaria + '\'' +
                ", totalUsuarios=" + totalUsuarios +
                ", idadeMedia=" + idadeMedia +
                ", idadeMinima=" + idadeMinima +
                ", idadeMaxima=" + idadeMaxima +
                '}';
    }
}
