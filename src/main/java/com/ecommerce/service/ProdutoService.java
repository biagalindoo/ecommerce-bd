package com.ecommerce.service;

import com.ecommerce.entity.Produto;
import com.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service para lógica de negócio de Produto
 */
@Service
@Transactional
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    /**
     * Salva um produto
     */
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
    
    /**
     * Busca produto por ID
     */
    @Transactional(readOnly = true)
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }
    
    /**
     * Lista todos os produtos
     */
    @Transactional(readOnly = true)
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
    
    /**
     * Busca produtos por nome
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContaining(nome);
    }
    
    /**
     * Busca produtos por categoria
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarPorCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria);
    }
    
    /**
     * Busca produtos com estoque baixo
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarProdutosComEstoqueBaixo(Integer limite) {
        return produtoRepository.findProdutosComEstoqueBaixo(limite);
    }
    
    /**
     * Busca produtos esgotados
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarProdutosEsgotados() {
        return produtoRepository.findProdutosEsgotados();
    }
    
    /**
     * Busca produtos por faixa de preço
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarPorFaixaPreco(BigDecimal precoMin, BigDecimal precoMax) {
        return produtoRepository.findByPrecoBetween(precoMin, precoMax);
    }
    
    /**
     * Busca produtos por armazém
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarPorArmazem(Long armazemId) {
        return produtoRepository.findByArmazemId(armazemId);
    }
    
    /**
     * Busca produtos mais vendidos
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarProdutosMaisVendidos() {
        return produtoRepository.findProdutosMaisVendidos();
    }
    
    /**
     * Exclui um produto
     */
    public void excluir(Long id) {
        produtoRepository.deleteById(id);
    }
    
    /**
     * Atualiza um produto
     */
    public Produto atualizar(Produto produto) {
        return produtoRepository.save(produto);
    }
    
    /**
     * Calcula valor total do estoque
     */
    @Transactional(readOnly = true)
    public Optional<BigDecimal> calcularValorTotalEstoque() {
        return produtoRepository.calcularValorTotalEstoque();
    }
    
    /**
     * Busca produtos com maior margem de lucro
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarProdutosComMaiorMargem() {
        return produtoRepository.findProdutosComMaiorMargem();
    }
    
    /**
     * Conta produtos por categoria
     */
    @Transactional(readOnly = true)
    public List<Object[]> contarPorCategoria() {
        return produtoRepository.countByCategoria();
    }
    
    /**
     * Atualiza estoque de um produto
     */
    public void atualizarEstoque(Long produtoId, Integer quantidade) {
        Optional<Produto> produtoOpt = produtoRepository.findById(produtoId);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidade);
            produtoRepository.save(produto);
        }
    }
}
