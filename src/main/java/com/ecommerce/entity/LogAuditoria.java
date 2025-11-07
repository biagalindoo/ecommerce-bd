package com.ecommerce.entity;

import java.time.LocalDateTime;

public class LogAuditoria {
    private Integer id;
    private String tabela;
    private String operacao;
    private Integer registroId;
    private LocalDateTime dataEvento;
    private String detalhes;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTabela() { return tabela; }
    public void setTabela(String tabela) { this.tabela = tabela; }
    public String getOperacao() { return operacao; }
    public void setOperacao(String operacao) { this.operacao = operacao; }
    public Integer getRegistroId() { return registroId; }
    public void setRegistroId(Integer registroId) { this.registroId = registroId; }
    public LocalDateTime getDataEvento() { return dataEvento; }
    public void setDataEvento(LocalDateTime dataEvento) { this.dataEvento = dataEvento; }
    public String getDetalhes() { return detalhes; }
    public void setDetalhes(String detalhes) { this.detalhes = detalhes; }
}


