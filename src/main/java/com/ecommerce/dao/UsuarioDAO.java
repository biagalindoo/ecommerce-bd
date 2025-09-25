package com.ecommerce.dao;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operações CRUD na tabela Usuario
 * Usa SQL puro sem ORMs ou frameworks de abstração
 */
public class UsuarioDAO {
    
    private Connection connection;
    
    public UsuarioDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Inserir novo usuário
     */
    public boolean inserir(Usuario usuario) {
        String sql = "INSERT INTO Usuario (email, senha_hash, cpf, primeiro_nome, sobrenome, data_nascimento) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenhaHash());
            stmt.setString(3, usuario.getCpf());
            stmt.setString(4, usuario.getPrimeiroNome());
            stmt.setString(5, usuario.getSobrenome());
            stmt.setString(6, usuario.getDataNascimento());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Buscar usuário por ID
     */
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM Usuario WHERE id = ?";
        Usuario usuario = null;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenhaHash(rs.getString("senha_hash"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setPrimeiroNome(rs.getString("primeiro_nome"));
                usuario.setSobrenome(rs.getString("sobrenome"));
                usuario.setDataNascimento(rs.getString("data_nascimento"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
        }
        return usuario;
    }
    
    /**
     * Listar todos os usuários
     */
    public List<Usuario> listarTodos() {
        String sql = "SELECT * FROM Usuario ORDER BY primeiro_nome, sobrenome";
        List<Usuario> usuarios = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenhaHash(rs.getString("senha_hash"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setPrimeiroNome(rs.getString("primeiro_nome"));
                usuario.setSobrenome(rs.getString("sobrenome"));
                usuario.setDataNascimento(rs.getString("data_nascimento"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
        return usuarios;
    }
    
    /**
     * Atualizar usuário
     */
    public boolean atualizar(Usuario usuario) {
        String sql = "UPDATE Usuario SET email = ?, senha_hash = ?, cpf = ?, primeiro_nome = ?, sobrenome = ?, data_nascimento = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenhaHash());
            stmt.setString(3, usuario.getCpf());
            stmt.setString(4, usuario.getPrimeiroNome());
            stmt.setString(5, usuario.getSobrenome());
            stmt.setString(6, usuario.getDataNascimento());
            stmt.setInt(7, usuario.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Deletar usuário
     */
    public boolean deletar(int id) {
        String sql = "DELETE FROM Usuario WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Buscar usuários por nome
     */
    public List<Usuario> buscarPorNome(String nome) {
        String sql = "SELECT * FROM Usuario WHERE primeiro_nome LIKE ? OR sobrenome LIKE ? ORDER BY primeiro_nome, sobrenome";
        List<Usuario> usuarios = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchTerm = "%" + nome + "%";
            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenhaHash(rs.getString("senha_hash"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setPrimeiroNome(rs.getString("primeiro_nome"));
                usuario.setSobrenome(rs.getString("sobrenome"));
                usuario.setDataNascimento(rs.getString("data_nascimento"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários por nome: " + e.getMessage());
        }
        return usuarios;
    }
}
