package com.ecommerce.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Entidade FornecedorProduto para JPA
 */
@Entity
@Table(name = "FornecedorProduto")
public class FornecedorProduto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Quantidade fornecida é obrigatória")
    @Min(value = 1, message = "Quantidade fornecida deve ser pelo menos 1")
    @Column(name = "quantidade_fornecida", nullable = false)
    private Integer quantidadeFornecida;
    
    @NotNull(message = "Custo unitário é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Custo unitário deve ser maior que zero")
    @Column(name = "custo_unitario_compra", nullable = false, precision = 10, scale = 2)
    private BigDecimal custoUnitarioCompra;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
    
    // Construtores
    public FornecedorProduto() {}
    
    public FornecedorProduto(Integer quantidadeFornecida, BigDecimal custoUnitarioCompra, Fornecedor fornecedor, Produto produto) {
        this.quantidadeFornecida = quantidadeFornecida;
        this.custoUnitarioCompra = custoUnitarioCompra;
        this.fornecedor = fornecedor;
        this.produto = produto;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getQuantidadeFornecida() {
        return quantidadeFornecida;
    }
    
    public void setQuantidadeFornecida(Integer quantidadeFornecida) {
        this.quantidadeFornecida = quantidadeFornecida;
    }
    
    public BigDecimal getCustoUnitarioCompra() {
        return custoUnitarioCompra;
    }
    
    public void setCustoUnitarioCompra(BigDecimal custoUnitarioCompra) {
        this.custoUnitarioCompra = custoUnitarioCompra;
    }
    
    public Fornecedor getFornecedor() {
        return fornecedor;
    }
    
    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
    
    public Produto getProduto() {
        return produto;
    }
    
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    @Override
    public String toString() {
        return "FornecedorProduto{" +
                "id=" + id +
                ", quantidadeFornecida=" + quantidadeFornecida +
                ", custoUnitarioCompra=" + custoUnitarioCompra +
                '}';
    }
}
