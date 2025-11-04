package com.ecommerce.dao;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.entity.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PedidoDAO {
    
    @Autowired
    private DatabaseConnection databaseConnection;
    
    public List<Pedido> listarTodos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT id, data_pedido, hora_pedido, status_pedido, observacao, valor_total, usuario_id FROM Pedido ORDER BY id ASC";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pedidos: " + e.getMessage());
        }
        return lista;
    }
    
    public Optional<Pedido> buscarPorId(Integer id) {
        String sql = "SELECT id, data_pedido, hora_pedido, status_pedido, observacao, valor_total, usuario_id FROM Pedido WHERE id = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedido: " + e.getMessage());
        }
        return Optional.empty();
    }
    
    public List<Pedido> buscarPorStatus(String status) {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT id, data_pedido, hora_pedido, status_pedido, observacao, valor_total, usuario_id FROM Pedido WHERE status_pedido = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedidos por status: " + e.getMessage());
        }
        return lista;
    }
    
    public Pedido salvar(Pedido p) {
        String sql = "INSERT INTO Pedido (data_pedido, hora_pedido, status_pedido, observacao, valor_total, usuario_id) VALUES (NOW(), CURTIME(), ?, ?, ?, ?)";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, p.getStatusPedido());
            stmt.setString(2, p.getObservacao());
            stmt.setBigDecimal(3, p.getValorTotal() != null ? p.getValorTotal() : BigDecimal.ZERO);
            stmt.setInt(4, p.getUsuarioId());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar pedido: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar pedido", e);
        }
        return p;
    }
    
    public Pedido atualizar(Pedido p) {
        String sql = "UPDATE Pedido SET status_pedido = ?, observacao = ?, valor_total = ?, usuario_id = ? WHERE id = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getStatusPedido());
            stmt.setString(2, p.getObservacao());
            stmt.setBigDecimal(3, p.getValorTotal() != null ? p.getValorTotal() : BigDecimal.ZERO);
            stmt.setInt(4, p.getUsuarioId());
            stmt.setInt(5, p.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pedido: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar pedido", e);
        }
        return p;
    }
    
    public void excluir(Integer id) {
        String sql = "DELETE FROM Pedido WHERE id = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir pedido: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir pedido", e);
        }
    }
    
    private Pedido mapear(ResultSet rs) throws SQLException {
        Pedido p = new Pedido();
        p.setId(rs.getInt("id"));
        Timestamp dt = rs.getTimestamp("data_pedido");
        if (dt != null) { p.setDataPedido(dt.toLocalDateTime()); }
        Time ht = rs.getTime("hora_pedido");
        if (ht != null) { p.setHoraPedido(ht.toLocalTime()); }
        p.setStatusPedido(rs.getString("status_pedido"));
        p.setObservacao(rs.getString("observacao"));
        p.setValorTotal(rs.getBigDecimal("valor_total"));
        p.setUsuarioId(rs.getInt("usuario_id"));
        return p;
    }
}


