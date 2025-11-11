package com.ecommerce.dao;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DashboardDAO {

    @Autowired
    private DatabaseConnection databaseConnection;

    public DashboardIndicatorsDTO buscarIndicadores(LocalDate dataInicio, LocalDate dataFim) {
        DashboardIndicatorsDTO dto = new DashboardIndicatorsDTO();

        dto.setTotalUsuarios(contarRegistros("Usuario"));
        dto.setTotalProdutos(contarRegistros("Produto"));
        dto.setTotalFornecedores(contarRegistros("Fornecedor"));
        dto.setTotalPedidos(contarRegistros("Pedido"));
        dto.setProdutosEstoqueBaixo(contarProdutosEstoqueBaixo());

        BigDecimal receitaPeriodo = BigDecimal.ZERO;
        long pedidosPeriodo = 0L;

        String sql = "SELECT COALESCE(SUM(valor_total),0) AS receita, COUNT(*) AS pedidos " +
                "FROM Pedido WHERE 1=1";
        List<Object> params = new ArrayList<>();
        if (dataInicio != null) {
            sql += " AND data_pedido >= ?";
            params.add(Timestamp.valueOf(LocalDateTime.of(dataInicio, LocalTime.MIN)));
        }
        if (dataFim != null) {
            sql += " AND data_pedido <= ?";
            params.add(Timestamp.valueOf(LocalDateTime.of(dataFim, LocalTime.MAX)));
        }

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = prepareWithParams(conn, sql, params);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                receitaPeriodo = rs.getBigDecimal("receita");
                pedidosPeriodo = rs.getLong("pedidos");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar indicadores: " + e.getMessage());
        }

        dto.setReceitaTotal(receitaPeriodo != null ? receitaPeriodo : BigDecimal.ZERO);
        dto.setPedidosPeriodo(pedidosPeriodo);
        if (pedidosPeriodo > 0) {
            dto.setTicketMedio(dto.getReceitaTotal()
                    .divide(BigDecimal.valueOf(pedidosPeriodo), 2, RoundingMode.HALF_UP));
        } else {
            dto.setTicketMedio(BigDecimal.ZERO);
        }
        return dto;
    }

    public List<StatusCountDTO> pedidosPorStatus(LocalDate dataInicio, LocalDate dataFim) {
        List<StatusCountDTO> lista = new ArrayList<>();
        String sql = "SELECT status_pedido, COUNT(*) AS total FROM Pedido WHERE 1=1";
        List<Object> params = new ArrayList<>();
        if (dataInicio != null) {
            sql += " AND data_pedido >= ?";
            params.add(Timestamp.valueOf(LocalDateTime.of(dataInicio, LocalTime.MIN)));
        }
        if (dataFim != null) {
            sql += " AND data_pedido <= ?";
            params.add(Timestamp.valueOf(LocalDateTime.of(dataFim, LocalTime.MAX)));
        }
        sql += " GROUP BY status_pedido";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = prepareWithParams(conn, sql, params);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new StatusCountDTO(
                        rs.getString("status_pedido"),
                        rs.getLong("total")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedidos por status: " + e.getMessage());
        }
        return lista;
    }

    public List<MonthlySalesDTO> vendasMensais(LocalDate dataInicio, LocalDate dataFim) {
        Map<String, MonthlySalesDTO> dados = new LinkedHashMap<>();

        String sql = "SELECT DATE_FORMAT(data_pedido, '%Y-%m') AS periodo, " +
                "COALESCE(SUM(valor_total),0) AS receita, COUNT(*) AS pedidos " +
                "FROM Pedido WHERE 1=1";
        List<Object> params = new ArrayList<>();
        if (dataInicio != null) {
            sql += " AND data_pedido >= ?";
            params.add(Timestamp.valueOf(LocalDateTime.of(dataInicio, LocalTime.MIN)));
        }
        if (dataFim != null) {
            sql += " AND data_pedido <= ?";
            params.add(Timestamp.valueOf(LocalDateTime.of(dataFim, LocalTime.MAX)));
        }
        sql += " GROUP BY DATE_FORMAT(data_pedido, '%Y-%m') ORDER BY periodo";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = prepareWithParams(conn, sql, params);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String periodo = rs.getString("periodo");
                dados.put(periodo, new MonthlySalesDTO(
                        periodo,
                        rs.getBigDecimal("receita"),
                        rs.getLong("pedidos")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar vendas mensais: " + e.getMessage());
        }

        return new ArrayList<>(dados.values());
    }

    public List<ProductSalesDTO> produtosMaisVendidos(LocalDate dataInicio, LocalDate dataFim, int limite) {
        List<ProductSalesDTO> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, COALESCE(SUM(ip.quantidade),0) AS quantidade, " +
                "COALESCE(SUM(ip.subtotal),0) AS receita " +
                "FROM Produto p " +
                "JOIN ItemPedido ip ON ip.produto_id = p.id " +
                "JOIN Pedido ped ON ped.id = ip.pedido_id " +
                "WHERE 1=1";

        List<Object> params = new ArrayList<>();
        if (dataInicio != null) {
            sql += " AND ped.data_pedido >= ?";
            params.add(Timestamp.valueOf(LocalDateTime.of(dataInicio, LocalTime.MIN)));
        }
        if (dataFim != null) {
            sql += " AND ped.data_pedido <= ?";
            params.add(Timestamp.valueOf(LocalDateTime.of(dataFim, LocalTime.MAX)));
        }
        sql += " GROUP BY p.id, p.nome ORDER BY quantidade DESC LIMIT ?";
        params.add(limite);

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = prepareWithParams(conn, sql, params);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ProductSalesDTO dto = new ProductSalesDTO();
                dto.setProdutoId(rs.getInt("id"));
                dto.setNome(rs.getString("nome"));
                dto.setQuantidadeVendida(rs.getLong("quantidade"));
                dto.setReceitaGerada(rs.getBigDecimal("receita"));
                lista.add(dto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos mais vendidos: " + e.getMessage());
        }
        return lista;
    }

    public StockDistributionDTO distribuicaoEstoque() {
        StockDistributionDTO dto = new StockDistributionDTO();
        String sql = "SELECT " +
                "SUM(CASE WHEN quantidade_estoque = 0 THEN 1 ELSE 0 END) AS esgotados, " +
                "SUM(CASE WHEN quantidade_estoque BETWEEN 1 AND 9 THEN 1 ELSE 0 END) AS baixo, " +
                "SUM(CASE WHEN quantidade_estoque BETWEEN 10 AND 50 THEN 1 ELSE 0 END) AS adequado, " +
                "SUM(CASE WHEN quantidade_estoque > 50 THEN 1 ELSE 0 END) AS alto " +
                "FROM Produto";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                dto.setEsgotados(rs.getLong("esgotados"));
                dto.setBaixo(rs.getLong("baixo"));
                dto.setAdequado(rs.getLong("adequado"));
                dto.setAlto(rs.getLong("alto"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar distribuição de estoque: " + e.getMessage());
        }
        return dto;
    }

    public List<SupplierContributionDTO> fornecedoresContribuicao(int limite) {
        List<SupplierContributionDTO> lista = new ArrayList<>();
        String sql = "SELECT f.id, f.nome_fantasia, " +
                "COALESCE(SUM(fp.quantidade_fornecida),0) AS quantidade, " +
                "COALESCE(SUM(fp.quantidade_fornecida * fp.custo_unitario_compra),0) AS valor " +
                "FROM Fornecedor f " +
                "LEFT JOIN FornecedorProduto fp ON fp.fornecedor_id = f.id " +
                "GROUP BY f.id, f.nome_fantasia " +
                "ORDER BY quantidade DESC " +
                "LIMIT ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limite);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SupplierContributionDTO dto = new SupplierContributionDTO();
                    dto.setFornecedorId(rs.getInt("id"));
                    dto.setFornecedorNome(rs.getString("nome_fantasia"));
                    dto.setQuantidadeTotal(rs.getLong("quantidade"));
                    dto.setValorTotal(rs.getBigDecimal("valor"));
                    lista.add(dto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar contribuição de fornecedores: " + e.getMessage());
        }
        return lista;
    }

    public PriceStatisticsDTO estatisticasPrecos() {
        PriceStatisticsDTO dto = new PriceStatisticsDTO();
        List<BigDecimal> precos = new ArrayList<>();
        String sql = "SELECT preco FROM Produto WHERE preco IS NOT NULL";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                precos.add(rs.getBigDecimal("preco"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar preços: " + e.getMessage());
        }

        if (precos.isEmpty()) {
            dto.setMedia(BigDecimal.ZERO);
            dto.setMediana(BigDecimal.ZERO);
            dto.setModa(BigDecimal.ZERO);
            dto.setVariancia(BigDecimal.ZERO);
            dto.setDesvioPadrao(BigDecimal.ZERO);
            return dto;
        }

        precos.sort(BigDecimal::compareTo);
        BigDecimal soma = BigDecimal.ZERO;
        Map<BigDecimal, Integer> frequencias = new HashMap<>();
        for (BigDecimal preco : precos) {
            soma = soma.add(preco);
            frequencias.merge(preco, 1, Integer::sum);
        }

        BigDecimal media = soma.divide(BigDecimal.valueOf(precos.size()), 4, RoundingMode.HALF_UP);
        dto.setMedia(media);

        // Mediana
        int tamanho = precos.size();
        if (tamanho % 2 == 0) {
            BigDecimal a = precos.get(tamanho / 2 - 1);
            BigDecimal b = precos.get(tamanho / 2);
            dto.setMediana(a.add(b).divide(BigDecimal.valueOf(2), 4, RoundingMode.HALF_UP));
        } else {
            dto.setMediana(precos.get(tamanho / 2));
        }

        // Moda
        BigDecimal moda = precos.get(0);
        int maiorFreq = 0;
        for (Map.Entry<BigDecimal, Integer> entry : frequencias.entrySet()) {
            if (entry.getValue() > maiorFreq) {
                maiorFreq = entry.getValue();
                moda = entry.getKey();
            }
        }
        dto.setModa(moda);

        // Variância e desvio padrão
        BigDecimal somaQuadrados = BigDecimal.ZERO;
        for (BigDecimal preco : precos) {
            BigDecimal diferenca = preco.subtract(media);
            somaQuadrados = somaQuadrados.add(diferenca.pow(2));
        }
        BigDecimal variancia = somaQuadrados.divide(BigDecimal.valueOf(precos.size()), 4, RoundingMode.HALF_UP);
        dto.setVariancia(variancia);
        dto.setDesvioPadrao(BigDecimal.valueOf(Math.sqrt(variancia.doubleValue())));

        // Ajusta casas decimais finais
        dto.setMedia(dto.getMedia().setScale(2, RoundingMode.HALF_UP));
        dto.setMediana(dto.getMediana().setScale(2, RoundingMode.HALF_UP));
        dto.setModa(dto.getModa().setScale(2, RoundingMode.HALF_UP));
        dto.setVariancia(dto.getVariancia().setScale(2, RoundingMode.HALF_UP));
        dto.setDesvioPadrao(dto.getDesvioPadrao().setScale(2, RoundingMode.HALF_UP));

        return dto;
    }

    private long contarRegistros(String tabela) {
        String sql = "SELECT COUNT(*) AS total FROM " + tabela;
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("total");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar registros de " + tabela + ": " + e.getMessage());
        }
        return 0;
    }

    private long contarProdutosEstoqueBaixo() {
        String sql = "SELECT COUNT(*) AS total FROM Produto WHERE quantidade_estoque BETWEEN 0 AND 9";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("total");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar produtos com estoque baixo: " + e.getMessage());
        }
        return 0;
    }

    private PreparedStatement prepareWithParams(Connection conn, String sql, List<Object> params) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int index = 0; index < params.size(); index++) {
            Object value = params.get(index);
            int paramIndex = index + 1;
            if (value instanceof Timestamp) {
                stmt.setTimestamp(paramIndex, (Timestamp) value);
            } else if (value instanceof Integer) {
                stmt.setInt(paramIndex, (Integer) value);
            } else if (value instanceof Long) {
                stmt.setLong(paramIndex, (Long) value);
            } else if (value instanceof BigDecimal) {
                stmt.setBigDecimal(paramIndex, (BigDecimal) value);
            } else if (value instanceof String) {
                stmt.setString(paramIndex, (String) value);
            } else {
                stmt.setObject(paramIndex, value);
            }
        }
        return stmt;
    }
}


