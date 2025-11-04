package com.ecommerce.dao;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.entity.FornecedorProdutoVinculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FornecedorProdutoDAO {
    
    @Autowired
    private DatabaseConnection databaseConnection;
    
    public List<FornecedorProdutoVinculo> listarPorProduto(Integer produtoId) {
        String sql = "SELECT fp.fornecedor_id, fp.produto_id, fp.quantidade_fornecida, fp.custo_unitario_compra, " +
                "f.razao_social, f.nome_fantasia " +
                "FROM FornecedorProduto fp JOIN Fornecedor f ON f.id = fp.fornecedor_id " +
                "WHERE fp.produto_id = ? ORDER BY f.razao_social";
        List<FornecedorProdutoVinculo> lista = new ArrayList<>();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, produtoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public void vincular(Integer fornecedorId, Integer produtoId, Integer quantidade, BigDecimal custo) {
        String sql = "INSERT INTO FornecedorProduto (fornecedor_id, produto_id, quantidade_fornecida, custo_unitario_compra) VALUES (?, ?, ?, ?)";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, fornecedorId);
            stmt.setInt(2, produtoId);
            stmt.setInt(3, quantidade != null ? quantidade : 1);
            stmt.setBigDecimal(4, custo != null ? custo : BigDecimal.ZERO);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao vincular fornecedor ao produto", e);
        }
    }
    
    public void desvincular(Integer fornecedorId, Integer produtoId) {
        String sql = "DELETE FROM FornecedorProduto WHERE fornecedor_id = ? AND produto_id = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, fornecedorId);
            stmt.setInt(2, produtoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao desvincular fornecedor do produto", e);
        }
    }
    
    private FornecedorProdutoVinculo mapear(ResultSet rs) throws SQLException {
        FornecedorProdutoVinculo v = new FornecedorProdutoVinculo();
        v.setFornecedorId(rs.getInt("fornecedor_id"));
        v.setProdutoId(rs.getInt("produto_id"));
        v.setQuantidadeFornecida(rs.getInt("quantidade_fornecida"));
        v.setCustoUnitarioCompra(rs.getBigDecimal("custo_unitario_compra"));
        v.setRazaoSocial(rs.getString("razao_social"));
        v.setNomeFantasia(rs.getString("nome_fantasia"));
        return v;
    }
}


