package com.ecommerce.model;

/**
 * Modelo para a entidade Produto
 * Representa os dados de um produto do sistema
 */
public class Produto {
    
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private int quantidadeEstoque;
    private int armazemId;
    private String nomeArmazem;
    
    // Construtores
    public Produto() {}
    
    public Produto(String nome, String descricao, double preco, int quantidadeEstoque, int armazemId) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.armazemId = armazemId;
    }
    
    // Getters e Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
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
    
    public double getPreco() {
        return preco;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    
    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
    
    public int getArmazemId() {
        return armazemId;
    }
    
    public void setArmazemId(int armazemId) {
        this.armazemId = armazemId;
    }
    
    public String getNomeArmazem() {
        return nomeArmazem;
    }
    
    public void setNomeArmazem(String nomeArmazem) {
        this.nomeArmazem = nomeArmazem;
    }
    
    public String getPrecoFormatado() {
        return String.format("R$ %.2f", preco);
    }
    
    public String getStatusEstoque() {
        if (quantidadeEstoque == 0) {
            return "ESGOTADO";
        } else if (quantidadeEstoque < 10) {
            return "BAIXO ESTOQUE";
        } else if (quantidadeEstoque < 25) {
            return "ESTOQUE MÉDIO";
        } else {
            return "ESTOQUE ALTO";
        }
    }
    
    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", quantidadeEstoque=" + quantidadeEstoque +
                ", armazemId=" + armazemId +
                '}';
    }
}
