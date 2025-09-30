package com.ecommerce.dao;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.entity.Produto;
import com.ecommerce.entity.AnalisePreco;
import com.ecommerce.entity.ProdutoResponsavel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para operações de Produto usando SQL puro
 */
@Repository
public class ProdutoDAO {
    
    @Autowired
    private DatabaseConnection databaseConnection;
    
    /**
     * Lista todos os produtos
     */
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, preco, quantidade_estoque, armazem_id FROM Produto";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                produtos.add(mapearResultado(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
        
        return produtos;
    }
    
    /**
     * Busca produto por ID
     */
    public Optional<Produto> buscarPorId(Integer id) {
        String sql = "SELECT id, nome, descricao, preco, quantidade_estoque, armazem_id FROM Produto WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearResultado(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto por ID: " + e.getMessage());
        }
        
        return Optional.empty();
    }
    
    /**
     * Busca produtos por nome
     */
    public List<Produto> buscarPorNome(String nome) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, preco, quantidade_estoque, armazem_id FROM Produto WHERE nome LIKE ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nome + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    produtos.add(mapearResultado(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos por nome: " + e.getMessage());
        }
        
        return produtos;
    }
    
    /**
     * Salva um novo produto
     */
    public Produto salvar(Produto produto) {
        String sql = "INSERT INTO Produto (nome, descricao, preco, quantidade_estoque, armazem_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setBigDecimal(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            if (produto.getArmazemId() != null) {
                stmt.setInt(5, produto.getArmazemId());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        produto.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar produto: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar produto", e);
        }
        
        return produto;
    }
    
    /**
     * Atualiza um produto
     */
    public Produto atualizar(Produto produto) {
        String sql = "UPDATE Produto SET nome = ?, descricao = ?, preco = ?, quantidade_estoque = ?, armazem_id = ? WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setBigDecimal(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            if (produto.getArmazemId() != null) {
                stmt.setInt(5, produto.getArmazemId());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            stmt.setInt(6, produto.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar produto", e);
        }
        
        return produto;
    }
    
    /**
     * Exclui um produto
     */
    public void excluir(Integer id) {
        String sql = "DELETE FROM Produto WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir produto", e);
        }
    }
    
    /**
     * Busca produtos com estoque baixo
     */
    public List<Produto> buscarProdutosComEstoqueBaixo(int limite) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, preco, quantidade_estoque, armazem_id FROM Produto WHERE quantidade_estoque < ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, limite);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    produtos.add(mapearResultado(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos com estoque baixo: " + e.getMessage());
        }
        
        return produtos;
    }
    
    /**
     * Busca produtos esgotados
     */
    public List<Produto> buscarProdutosEsgotados() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, preco, quantidade_estoque, armazem_id FROM Produto WHERE quantidade_estoque = 0";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                produtos.add(mapearResultado(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos esgotados: " + e.getMessage());
        }
        
        return produtos;
    }
    
    /**
     * Calcula valor total do estoque
     */
    public BigDecimal calcularValorTotalEstoque() {
        String sql = "SELECT SUM(preco * quantidade_estoque) FROM Produto";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                BigDecimal valor = rs.getBigDecimal(1);
                return valor != null ? valor : BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao calcular valor total do estoque: " + e.getMessage());
        }
        
        return BigDecimal.ZERO;
    }
    
    /**
     * Conta total de produtos
     */
    public long contarTotal() {
        String sql = "SELECT COUNT(*) FROM Produto";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar produtos: " + e.getMessage());
        }
        
        return 0;
    }
    
    
    /**
     * Análise de produtos por faixa de preço
     */
    public List<AnalisePreco> analisarPorPreco() {
        String sql = "SELECT " +
                    "CASE " +
                    "WHEN preco < 50 THEN 'Até R$ 50' " +
                    "WHEN preco BETWEEN 50 AND 100 THEN 'R$ 50 - R$ 100' " +
                    "WHEN preco BETWEEN 100 AND 200 THEN 'R$ 100 - R$ 200' " +
                    "WHEN preco BETWEEN 200 AND 500 THEN 'R$ 200 - R$ 500' " +
                    "ELSE 'Acima de R$ 500' " +
                    "END AS faixa_preco, " +
                    "COUNT(*) AS total_produtos, " +
                    "ROUND(AVG(preco), 2) AS preco_medio, " +
                    "SUM(quantidade_estoque) AS total_estoque " +
                    "FROM Produto " +
                    "GROUP BY faixa_preco " +
                    "ORDER BY MIN(preco)";
        
        List<AnalisePreco> analises = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                AnalisePreco analise = new AnalisePreco();
                analise.setFaixaPreco(rs.getString("faixa_preco"));
                analise.setTotalProdutos(rs.getInt("total_produtos"));
                analise.setPrecoMedio(rs.getBigDecimal("preco_medio"));
                analise.setTotalEstoque(rs.getInt("total_estoque"));
                
                analises.add(analise);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return analises;
    }
    
    /**
     * Lista produtos com responsáveis (JOIN com Usuario)
     */
    public List<ProdutoResponsavel> listarProdutosComResponsaveis() {
        String sql = "SELECT p.nome AS produto, p.preco, p.quantidade_estoque, p.armazem_id, " +
                    "CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS responsavel_armazem, " +
                    "u.email AS email_responsavel " +
                    "FROM Produto p " +
                    "LEFT JOIN Usuario u ON p.armazem_id = u.id " +
                    "WHERE p.quantidade_estoque > 0 " +
                    "ORDER BY p.armazem_id, p.nome";
        
        List<ProdutoResponsavel> produtos = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                ProdutoResponsavel produto = new ProdutoResponsavel();
                produto.setProduto(rs.getString("produto"));
                produto.setPreco(rs.getBigDecimal("preco"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produto.setArmazemId(rs.getInt("armazem_id"));
                produto.setResponsavelArmazem(rs.getString("responsavel_armazem"));
                produto.setEmailResponsavel(rs.getString("email_responsavel"));
                
                produtos.add(produto);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return produtos;
    }
    
    /**
     * Mapeia resultado do ResultSet para objeto Produto
     */
    private Produto mapearResultado(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setId(rs.getInt("id"));
        produto.setNome(rs.getString("nome"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setPreco(rs.getBigDecimal("preco"));
        produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
        produto.setArmazemId(rs.getInt("armazem_id"));
        
        return produto;
    }
}
