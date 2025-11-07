package com.ecommerce.dao;

import com.ecommerce.database.DatabaseConnection;
import com.ecommerce.entity.LogAuditoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LogDAO {

    @Autowired
    private DatabaseConnection databaseConnection;

    public List<LogAuditoria> listarRecentes(int limite) {
        
        List<LogAuditoria> logs = new ArrayList<>();
        String sql = "SELECT id, tabela, operacao, registro_id, data_evento, detalhes " +
                "FROM Log_Auditoria ORDER BY id DESC LIMIT ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limite);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapear(rs));
                }
            }
            System.out.println("LogDAO: Encontrados " + logs.size() + " logs");
        } catch (SQLException e) {
            System.err.println("ERRO ao listar logs: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }
        return logs;
    }

    private LogAuditoria mapear(ResultSet rs) throws SQLException {
        LogAuditoria log = new LogAuditoria();
        log.setId(rs.getInt("id"));
        log.setTabela(rs.getString("tabela"));
        log.setOperacao(rs.getString("operacao"));
        int regId = rs.getInt("registro_id");
        log.setRegistroId(rs.wasNull() ? null : regId);
        Timestamp ts = rs.getTimestamp("data_evento");
        if (ts != null) { log.setDataEvento(ts.toLocalDateTime()); }
        log.setDetalhes(rs.getString("detalhes"));
        return log;
    }
}


