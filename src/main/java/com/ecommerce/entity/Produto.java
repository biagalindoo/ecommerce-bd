package com.ecommerce.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Entidade Produto para JPA
 */
@Entity
@Table(name = "Produto")
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    
    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    @Column(name = "descricao", length = 500)
    private String descricao;
    
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;
    
    @NotNull(message = "Quantidade em estoque é obrigatória")
    @Min(value = 0, message = "Quantidade em estoque não pode ser negativa")
    @Column(name = "quantidade_estoque", nullable = false)
    private Integer quantidadeEstoque;
    
    @Size(max = 50, message = "Categoria deve ter no máximo 50 caracteres")
    @Column(name = "categoria", length = 50)
    private String categoria;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "armazem_id")
    private Armazem armazem;
    
    // Relacionamentos
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<ItemPedido> itensPedido;
    
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<ItemCarrinho> itensCarrinho;
    
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<FornecedorProduto> fornecedores;
    
    // Construtores
    public Produto() {}
    
    public Produto(String nome, String descricao, BigDecimal preco, Integer quantidadeEstoque, String categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public BigDecimal getPreco() {
        return preco;
    }
    
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
    
    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    
    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public Armazem getArmazem() {
        return armazem;
    }
    
    public void setArmazem(Armazem armazem) {
        this.armazem = armazem;
    }
    
    public java.util.List<ItemPedido> getItensPedido() {
        return itensPedido;
    }
    
    public void setItensPedido(java.util.List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }
    
    public java.util.List<ItemCarrinho> getItensCarrinho() {
        return itensCarrinho;
    }
    
    public void setItensCarrinho(java.util.List<ItemCarrinho> itensCarrinho) {
        this.itensCarrinho = itensCarrinho;
    }
    
    public java.util.List<FornecedorProduto> getFornecedores() {
        return fornecedores;
    }
    
    public void setFornecedores(java.util.List<FornecedorProduto> fornecedores) {
        this.fornecedores = fornecedores;
    }
    
    // Métodos auxiliares
    public boolean isEstoqueBaixo() {
        return quantidadeEstoque != null && quantidadeEstoque < 10;
    }
    
    public boolean isEsgotado() {
        return quantidadeEstoque != null && quantidadeEstoque == 0;
    }
    
    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidadeEstoque=" + quantidadeEstoque +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
