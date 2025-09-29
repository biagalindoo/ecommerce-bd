package com.ecommerce.dao;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para operações de Usuario usando SQL puro
 */
@Repository
public class UsuarioDAO {
    
    @Autowired
    private DatabaseConnection databaseConnection;
    
    /**
     * Lista todos os usuários
     */
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, email, senha_hash, cpf, primeiro_nome, sobrenome, data_nascimento FROM Usuario";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                usuarios.add(mapearResultado(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    /**
     * Busca usuário por ID
     */
    public Optional<Usuario> buscarPorId(Integer id) {
        String sql = "SELECT id, email, senha_hash, cpf, primeiro_nome, sobrenome, data_nascimento FROM Usuario WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearResultado(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
        }
        
        return Optional.empty();
    }
    
    /**
     * Busca usuários por nome
     */
    public List<Usuario> buscarPorNome(String nome) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, email, senha_hash, cpf, primeiro_nome, sobrenome, data_nascimento FROM Usuario WHERE primeiro_nome LIKE ? OR sobrenome LIKE ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String nomeParam = "%" + nome + "%";
            stmt.setString(1, nomeParam);
            stmt.setString(2, nomeParam);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapearResultado(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários por nome: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    /**
     * Salva um novo usuário
     */
    public Usuario salvar(Usuario usuario) {
        String sql = "INSERT INTO Usuario (email, senha_hash, cpf, primeiro_nome, sobrenome, data_nascimento) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenhaHash());
            stmt.setString(3, usuario.getCpf());
            stmt.setString(4, usuario.getPrimeiroNome());
            stmt.setString(5, usuario.getSobrenome());
            stmt.setDate(6, Date.valueOf(usuario.getDataNascimento()));
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
        
        return usuario;
    }
    
    /**
     * Atualiza um usuário
     */
    public Usuario atualizar(Usuario usuario) {
        String sql = "UPDATE Usuario SET email = ?, senha_hash = ?, cpf = ?, primeiro_nome = ?, sobrenome = ?, data_nascimento = ? WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenhaHash());
            stmt.setString(3, usuario.getCpf());
            stmt.setString(4, usuario.getPrimeiroNome());
            stmt.setString(5, usuario.getSobrenome());
            stmt.setDate(6, Date.valueOf(usuario.getDataNascimento()));
            stmt.setInt(7, usuario.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
        
        return usuario;
    }
    
    /**
     * Exclui um usuário
     */
    public void excluir(Integer id) {
        String sql = "DELETE FROM Usuario WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir usuário", e);
        }
    }
    
    /**
     * Conta total de usuários
     */
    public long contarTotal() {
        String sql = "SELECT COUNT(*) FROM Usuario";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar usuários: " + e.getMessage());
        }
        
        return 0;
    }
    
    /**
     * Verifica se email já existe
     */
    public boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM Usuario WHERE email = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar email: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Mapeia resultado do ResultSet para objeto Usuario
     */
    private Usuario mapearResultado(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenhaHash(rs.getString("senha_hash"));
        usuario.setCpf(rs.getString("cpf"));
        usuario.setPrimeiroNome(rs.getString("primeiro_nome"));
        usuario.setSobrenome(rs.getString("sobrenome"));
        
        Date dataNascimento = rs.getDate("data_nascimento");
        if (dataNascimento != null) {
            usuario.setDataNascimento(dataNascimento.toLocalDate());
        }
        
        return usuario;
    }
}
