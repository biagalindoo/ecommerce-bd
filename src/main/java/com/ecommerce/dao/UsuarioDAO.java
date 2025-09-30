package com.ecommerce.dao;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.entity.Usuario;
import com.ecommerce.entity.UsuarioCompleto;
import com.ecommerce.entity.UsuarioProduto;
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
        String sql = "SELECT id, email, senha_hash, cpf, primeiro_nome, sobrenome, data_nascimento FROM Usuario ORDER BY id ASC";
        
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
     * Lista todos os usuários com informações completas
     */
    public List<UsuarioCompleto> listarUsuariosCompletos() {
        String sql = "SELECT id, CONCAT(primeiro_nome, ' ', sobrenome) AS nome_completo, " +
                    "email, cpf, data_nascimento, " +
                    "TIMESTAMPDIFF(YEAR, data_nascimento, CURDATE()) AS idade " +
                    "FROM Usuario " +
                    "ORDER BY id ASC";
        
        List<UsuarioCompleto> usuarios = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                UsuarioCompleto usuario = new UsuarioCompleto();
                usuario.setId(rs.getInt("id"));
                usuario.setNomeCompleto(rs.getString("nome_completo"));
                usuario.setEmail(rs.getString("email"));
                usuario.setCpf(rs.getString("cpf"));
                
                Date dataNascimento = rs.getDate("data_nascimento");
                if (dataNascimento != null) {
                    usuario.setDataNascimento(dataNascimento.toLocalDate());
                }
                
                usuario.setIdade(rs.getInt("idade"));
                
                usuarios.add(usuario);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuarios;
    }
    
    
    /**
     * Lista usuários com análise de produtos gerenciados (JOIN)
     */
    public List<UsuarioProduto> listarUsuariosComProdutos() {
        String sql = "SELECT u.id, CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS nome_completo, " +
                    "u.email, " +
                    "CASE " +
                    "WHEN TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) < 18 THEN 'Menores de 18' " +
                    "WHEN TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) BETWEEN 18 AND 25 THEN '18-25 anos' " +
                    "WHEN TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) BETWEEN 26 AND 35 THEN '26-35 anos' " +
                    "WHEN TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) BETWEEN 36 AND 50 THEN '36-50 anos' " +
                    "ELSE 'Acima de 50 anos' " +
                    "END AS faixa_etaria, " +
                    "TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) AS idade, " +
                    "COUNT(p.id) AS produtos_gerenciados, " +
                    "COALESCE(SUM(p.preco * p.quantidade_estoque), 0) AS valor_total_produtos " +
                    "FROM Usuario u " +
                    "LEFT JOIN Produto p ON u.id = p.armazem_id " +
                    "WHERE u.data_nascimento IS NOT NULL " +
                    "GROUP BY u.id, u.primeiro_nome, u.sobrenome, u.email, u.data_nascimento " +
                    "ORDER BY idade, nome_completo";
        
        List<UsuarioProduto> usuarios = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                UsuarioProduto usuario = new UsuarioProduto();
                usuario.setId(rs.getInt("id"));
                usuario.setNomeCompleto(rs.getString("nome_completo"));
                usuario.setEmail(rs.getString("email"));
                usuario.setFaixaEtaria(rs.getString("faixa_etaria"));
                usuario.setIdade(rs.getInt("idade"));
                usuario.setProdutosGerenciados(rs.getInt("produtos_gerenciados"));
                usuario.setValorTotalProdutos(rs.getBigDecimal("valor_total_produtos"));
                
                usuarios.add(usuario);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuarios;
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
