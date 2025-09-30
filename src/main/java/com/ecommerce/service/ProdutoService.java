package com.ecommerce.service;

import com.ecommerce.dao.ProdutoDAO;
import com.ecommerce.entity.Produto;
import com.ecommerce.entity.AnalisePreco;
import com.ecommerce.entity.ProdutoResponsavel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service para operações de Produto usando SQL puro
 */
@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoDAO produtoDAO;
    
    /**
     * Busca produto por ID
     */
    public Optional<Produto> buscarPorId(Integer id) {
        return produtoDAO.buscarPorId(id);
    }
    
    /**
     * Lista todos os produtos
     */
    public List<Produto> listarTodos() {
        return produtoDAO.listarTodos();
    }
    
    /**
     * Salva um produto
     */
    public Produto salvar(Produto produto) {
        return produtoDAO.salvar(produto);
    }
    
    /**
     * Atualiza um produto
     */
    public Produto atualizar(Produto produto) {
        return produtoDAO.atualizar(produto);
    }
    
    /**
     * Exclui um produto
     */
    public void excluir(Integer id) {
        produtoDAO.excluir(id);
    }
    
    /**
     * Busca produtos por nome
     */
    public List<Produto> buscarPorNome(String nome) {
        return produtoDAO.buscarPorNome(nome);
    }
    
    /**
     * Busca produtos com estoque baixo
     */
    public List<Produto> buscarProdutosComEstoqueBaixo(int limite) {
        return produtoDAO.buscarProdutosComEstoqueBaixo(limite);
    }
    
    /**
     * Busca produtos esgotados
     */
    public List<Produto> buscarProdutosEsgotados() {
        return produtoDAO.buscarProdutosEsgotados();
    }
    
    /**
     * Calcula valor total do estoque
     */
    public BigDecimal calcularValorTotalEstoque() {
        return produtoDAO.calcularValorTotalEstoque();
    }
    
    /**
     * Conta total de produtos
     */
    public long contarTotal() {
        return produtoDAO.contarTotal();
    }
    
    
    /**
     * Análise de produtos por faixa de preço
     */
    public List<AnalisePreco> analisarPorPreco() {
        return produtoDAO.analisarPorPreco();
    }
    
    /**
     * Lista produtos com responsáveis (JOIN)
     */
    public List<ProdutoResponsavel> listarProdutosComResponsaveis() {
        return produtoDAO.listarProdutosComResponsaveis();
    }
}