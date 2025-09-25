package com.ecommerce.dao;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operações CRUD na tabela Produto
 * Usa SQL puro sem ORMs ou frameworks de abstração
 */
public class ProdutoDAO {
    
    private Connection connection;
    
    public ProdutoDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Inserir novo produto
     */
    public boolean inserir(Produto produto) {
        String sql = "INSERT INTO Produto (nome, descricao, preco, quantidade_estoque, armazem_id) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            stmt.setInt(5, produto.getArmazemId());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    produto.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Buscar produto por ID
     */
    public Produto buscarPorId(int id) {
        String sql = "SELECT p.*, a.nome as nome_armazem FROM Produto p LEFT JOIN Armazem a ON p.armazem_id = a.id WHERE p.id = ?";
        Produto produto = null;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produto.setArmazemId(rs.getInt("armazem_id"));
                produto.setNomeArmazem(rs.getString("nome_armazem"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto por ID: " + e.getMessage());
        }
        return produto;
    }
    
    /**
     * Listar todos os produtos
     */
    public List<Produto> listarTodos() {
        String sql = "SELECT p.*, a.nome as nome_armazem FROM Produto p LEFT JOIN Armazem a ON p.armazem_id = a.id ORDER BY p.nome";
        List<Produto> produtos = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produto.setArmazemId(rs.getInt("armazem_id"));
                produto.setNomeArmazem(rs.getString("nome_armazem"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }
    
    /**
     * Atualizar produto
     */
    public boolean atualizar(Produto produto) {
        String sql = "UPDATE Produto SET nome = ?, descricao = ?, preco = ?, quantidade_estoque = ?, armazem_id = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            stmt.setInt(5, produto.getArmazemId());
            stmt.setInt(6, produto.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Deletar produto
     */
    public boolean deletar(int id) {
        String sql = "DELETE FROM Produto WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar produto: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Buscar produtos por nome
     */
    public List<Produto> buscarPorNome(String nome) {
        String sql = "SELECT p.*, a.nome as nome_armazem FROM Produto p LEFT JOIN Armazem a ON p.armazem_id = a.id WHERE p.nome LIKE ? ORDER BY p.nome";
        List<Produto> produtos = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchTerm = "%" + nome + "%";
            stmt.setString(1, searchTerm);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produto.setArmazemId(rs.getInt("armazem_id"));
                produto.setNomeArmazem(rs.getString("nome_armazem"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos por nome: " + e.getMessage());
        }
        return produtos;
    }
    
    /**
     * Listar armazéns disponíveis
     */
    public List<String> listarArmazens() {
        String sql = "SELECT id, nome FROM Armazem ORDER BY nome";
        List<String> armazens = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                armazens.add(rs.getInt("id") + " - " + rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar armazéns: " + e.getMessage());
        }
        return armazens;
    }
    
    /**
     * Buscar produtos com estoque baixo
     */
    public List<Produto> buscarEstoqueBaixo() {
        String sql = "SELECT p.*, a.nome as nome_armazem FROM Produto p LEFT JOIN Armazem a ON p.armazem_id = a.id WHERE p.quantidade_estoque < 10 ORDER BY p.quantidade_estoque";
        List<Produto> produtos = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produto.setArmazemId(rs.getInt("armazem_id"));
                produto.setNomeArmazem(rs.getString("nome_armazem"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos com estoque baixo: " + e.getMessage());
        }
        return produtos;
    }
}
