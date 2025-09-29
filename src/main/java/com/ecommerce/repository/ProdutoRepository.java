package com.ecommerce.repository;

import com.ecommerce.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository para entidade Produto
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    /**
     * Busca produtos por nome
     */
    @Query("SELECT p FROM Produto p WHERE " +
           "LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Produto> findByNomeContaining(@Param("nome") String nome);
    
    /**
     * Busca produtos por categoria
     */
    List<Produto> findByCategoria(String categoria);
    
    /**
     * Busca produtos com estoque baixo
     */
    @Query("SELECT p FROM Produto p WHERE p.quantidadeEstoque < :limite")
    List<Produto> findProdutosComEstoqueBaixo(@Param("limite") Integer limite);
    
    /**
     * Busca produtos esgotados
     */
    @Query("SELECT p FROM Produto p WHERE p.quantidadeEstoque = 0")
    List<Produto> findProdutosEsgotados();
    
    /**
     * Busca produtos por faixa de preço
     */
    @Query("SELECT p FROM Produto p WHERE p.preco BETWEEN :precoMin AND :precoMax")
    List<Produto> findByPrecoBetween(@Param("precoMin") BigDecimal precoMin, 
                                    @Param("precoMax") BigDecimal precoMax);
    
    /**
     * Busca produtos por armazém
     */
    @Query("SELECT p FROM Produto p WHERE p.armazem.id = :armazemId")
    List<Produto> findByArmazemId(@Param("armazemId") Long armazemId);
    
    /**
     * Busca produtos mais vendidos
     */
    @Query("SELECT p FROM Produto p " +
           "LEFT JOIN p.itensPedido ip " +
           "LEFT JOIN ip.pedido ped " +
           "WHERE ped.statusPedido IN ('pago', 'enviado') " +
           "GROUP BY p.id " +
           "ORDER BY SUM(ip.quantidade) DESC")
    List<Produto> findProdutosMaisVendidos();
    
    /**
     * Conta produtos por categoria
     */
    @Query("SELECT p.categoria, COUNT(p) FROM Produto p GROUP BY p.categoria")
    List<Object[]> countByCategoria();
    
    /**
     * Calcula valor total do estoque
     */
    @Query("SELECT SUM(p.preco * p.quantidadeEstoque) FROM Produto p")
    Optional<BigDecimal> calcularValorTotalEstoque();
    
    /**
     * Busca produtos com maior margem de lucro
     */
    @Query("SELECT p FROM Produto p " +
           "LEFT JOIN p.fornecedores fp " +
           "WHERE fp.custoUnitarioCompra IS NOT NULL " +
           "ORDER BY (p.preco - fp.custoUnitarioCompra) DESC")
    List<Produto> findProdutosComMaiorMargem();
}
