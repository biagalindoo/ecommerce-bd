package com.ecommerce.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade Pedido para JPA
 */
@Entity
@Table(name = "Pedido")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Data do pedido é obrigatória")
    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;
    
    @Column(name = "status_pedido", length = 20)
    private String statusPedido;
    
    @NotNull(message = "Valor total é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Valor total deve ser maior que zero")
    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    // Construtores
    public Pedido() {
        this.dataPedido = LocalDateTime.now();
    }
    
    public Pedido(Usuario usuario, BigDecimal valorTotal, String statusPedido) {
        this();
        this.usuario = usuario;
        this.valorTotal = valorTotal;
        this.statusPedido = statusPedido;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDataPedido() {
        return dataPedido;
    }
    
    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }
    
    public String getStatusPedido() {
        return statusPedido;
    }
    
    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", dataPedido=" + dataPedido +
                ", statusPedido='" + statusPedido + '\'' +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
