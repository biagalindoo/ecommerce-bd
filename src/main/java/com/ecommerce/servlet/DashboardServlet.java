package com.ecommerce.servlet;

import com.ecommerce.dao.ProdutoDAO;
import com.ecommerce.dao.UsuarioDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecommerce.database.DatabaseConnection;

/**
 * Servlet para o dashboard principal
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Estatísticas gerais
            Map<String, Object> stats = obterEstatisticas();
            request.setAttribute("stats", stats);
            
            // Produtos com estoque baixo
            ProdutoDAO produtoDAO = new ProdutoDAO();
            request.setAttribute("produtosEstoqueBaixo", produtoDAO.buscarEstoqueBaixo());
            
            // Usuários recentes
            List<Map<String, Object>> usuariosRecentes = obterUsuariosRecentes();
            request.setAttribute("usuariosRecentes", usuariosRecentes);
            
            // Produtos mais caros
            List<Map<String, Object>> produtosCaros = obterProdutosMaisCaros();
            request.setAttribute("produtosCaros", produtosCaros);
            
            request.getRequestDispatcher("/WEB-INF/views/dashboard/index.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Erro ao carregar dashboard: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/dashboard/index.jsp").forward(request, response);
        }
    }
    
    private Map<String, Object> obterEstatisticas() throws SQLException {
        Map<String, Object> stats = new HashMap<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        
        // Total de usuários
        String sqlUsuarios = "SELECT COUNT(*) as total FROM Usuario";
        try (PreparedStatement stmt = connection.prepareStatement(sqlUsuarios)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stats.put("totalUsuarios", rs.getInt("total"));
            }
        }
        
        // Total de produtos
        String sqlProdutos = "SELECT COUNT(*) as total FROM Produto";
        try (PreparedStatement stmt = connection.prepareStatement(sqlProdutos)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stats.put("totalProdutos", rs.getInt("total"));
            }
        }
        
        // Total de pedidos
        String sqlPedidos = "SELECT COUNT(*) as total FROM Pedido";
        try (PreparedStatement stmt = connection.prepareStatement(sqlPedidos)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stats.put("totalPedidos", rs.getInt("total"));
            }
        }
        
        // Valor total em estoque
        String sqlValorEstoque = "SELECT SUM(preco * quantidade_estoque) as valor_total FROM Produto";
        try (PreparedStatement stmt = connection.prepareStatement(sqlValorEstoque)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stats.put("valorTotalEstoque", rs.getDouble("valor_total"));
            }
        }
        
        // Produtos com estoque baixo
        String sqlEstoqueBaixo = "SELECT COUNT(*) as total FROM Produto WHERE quantidade_estoque < 10";
        try (PreparedStatement stmt = connection.prepareStatement(sqlEstoqueBaixo)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stats.put("produtosEstoqueBaixo", rs.getInt("total"));
            }
        }
        
        return stats;
    }
    
    private List<Map<String, Object>> obterUsuariosRecentes() throws SQLException {
        List<Map<String, Object>> usuarios = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        
        String sql = "SELECT id, primeiro_nome, sobrenome, email, data_nascimento FROM Usuario ORDER BY id DESC LIMIT 5";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> usuario = new HashMap<>();
                usuario.put("id", rs.getInt("id"));
                usuario.put("nome", rs.getString("primeiro_nome") + " " + rs.getString("sobrenome"));
                usuario.put("email", rs.getString("email"));
                usuario.put("dataNascimento", rs.getString("data_nascimento"));
                usuarios.add(usuario);
            }
        }
        
        return usuarios;
    }
    
    private List<Map<String, Object>> obterProdutosMaisCaros() throws SQLException {
        List<Map<String, Object>> produtos = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        
        String sql = "SELECT p.nome, p.preco, p.quantidade_estoque, a.nome as armazem FROM Produto p LEFT JOIN Armazem a ON p.armazem_id = a.id ORDER BY p.preco DESC LIMIT 5";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> produto = new HashMap<>();
                produto.put("nome", rs.getString("nome"));
                produto.put("preco", rs.getDouble("preco"));
                produto.put("quantidadeEstoque", rs.getInt("quantidade_estoque"));
                produto.put("armazem", rs.getString("armazem"));
                produtos.add(produto);
            }
        }
        
        return produtos;
    }
}
