package com.ecommerce.dao;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.entity.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FornecedorDAO {
    
    @Autowired
    private DatabaseConnection databaseConnection;
    
    public List<Fornecedor> listarTodos() {
        List<Fornecedor> lista = new ArrayList<>();
        String sql = "SELECT id, email, nome_fantasia, cnpj, razao_social FROM Fornecedor ORDER BY id ASC";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar fornecedores: " + e.getMessage());
        }
        return lista;
    }
    
    public Optional<Fornecedor> buscarPorId(Integer id) {
        String sql = "SELECT id, email, nome_fantasia, cnpj, razao_social FROM Fornecedor WHERE id = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar fornecedor: " + e.getMessage());
        }
        return Optional.empty();
    }
    
    public List<Fornecedor> buscarPorNome(String termo) {
        List<Fornecedor> lista = new ArrayList<>();
        String sql = "SELECT id, email, nome_fantasia, cnpj, razao_social FROM Fornecedor WHERE nome_fantasia LIKE ? OR razao_social LIKE ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String p = "%" + termo + "%";
            stmt.setString(1, p);
            stmt.setString(2, p);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar fornecedores: " + e.getMessage());
        }
        return lista;
    }
    
    public Fornecedor salvar(Fornecedor f) {
        String sql = "INSERT INTO Fornecedor (email, nome_fantasia, cnpj, razao_social) VALUES (?, ?, ?, ?)";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, f.getEmail());
            stmt.setString(2, f.getNomeFantasia());
            stmt.setString(3, f.getCnpj());
            stmt.setString(4, f.getRazaoSocial());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    f.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar fornecedor: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar fornecedor", e);
        }
        return f;
    }
    
    public Fornecedor atualizar(Fornecedor f) {
        String sql = "UPDATE Fornecedor SET email = ?, nome_fantasia = ?, cnpj = ?, razao_social = ? WHERE id = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, f.getEmail());
            stmt.setString(2, f.getNomeFantasia());
            stmt.setString(3, f.getCnpj());
            stmt.setString(4, f.getRazaoSocial());
            stmt.setInt(5, f.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar fornecedor: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar fornecedor", e);
        }
        return f;
    }
    
    public void excluir(Integer id) {
        String sql = "DELETE FROM Fornecedor WHERE id = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir fornecedor: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir fornecedor", e);
        }
    }
    
    private Fornecedor mapear(ResultSet rs) throws SQLException {
        Fornecedor f = new Fornecedor();
        f.setId(rs.getInt("id"));
        f.setEmail(rs.getString("email"));
        f.setNomeFantasia(rs.getString("nome_fantasia"));
        f.setCnpj(rs.getString("cnpj"));
        f.setRazaoSocial(rs.getString("razao_social"));
        return f;
    }
}


