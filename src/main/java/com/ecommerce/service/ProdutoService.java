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

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoDAO produtoDAO;
    
    public Optional<Produto> buscarPorId(Integer id) {
        return produtoDAO.buscarPorId(id);
    }
    
    public List<Produto> listarTodos() {
        return produtoDAO.listarTodos();
    }
    
    public Produto salvar(Produto produto) {
        return produtoDAO.salvar(produto);
    }
    
    public Produto atualizar(Produto produto) {
        return produtoDAO.atualizar(produto);
    }
   
    public void excluir(Integer id) {
        produtoDAO.excluir(id);
    }
    
    public List<Produto> buscarPorNome(String nome) {
        return produtoDAO.buscarPorNome(nome);
    }
    
    public List<Produto> buscarProdutosComEstoqueBaixo(int limite) {
        return produtoDAO.buscarProdutosComEstoqueBaixo(limite);
    }
    
    public List<Produto> buscarProdutosEsgotados() {
        return produtoDAO.buscarProdutosEsgotados();
    }
    
    public BigDecimal calcularValorTotalEstoque() {
        return produtoDAO.calcularValorTotalEstoque();
    }
    
    public long contarTotal() {
        return produtoDAO.contarTotal();
    }
    
    
    public List<AnalisePreco> analisarPorPreco() {
        return produtoDAO.analisarPorPreco();
    }
    
    public List<ProdutoResponsavel> listarProdutosComResponsaveis() {
        return produtoDAO.listarProdutosComResponsaveis();
    }
}