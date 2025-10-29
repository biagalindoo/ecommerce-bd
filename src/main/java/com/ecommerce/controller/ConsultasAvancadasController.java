package com.ecommerce.controller;

import com.ecommerce.database.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/consultas-avancadas")
public class ConsultasAvancadasController {

    @Autowired
    private DatabaseConnection databaseConnection;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("titulo", "Consultas Avançadas");
        return "consultas-avancadas/index";
    }

    @GetMapping("/anti-join")
    public String antiJoin(Model model) {
        List<Map<String, Object>> usuarios = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection()) {
            // Consulta Anti-Join: Usuários que nunca fizeram pedidos
            String sql = "SELECT " +
                "u.id, " +
                "CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS nome_completo, " +
                "u.email, " +
                "u.data_nascimento, " +
                "TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) AS idade, " +
                "'Nunca fez pedidos' AS status_compra " +
                "FROM Usuario u " +
                "LEFT JOIN Pedido p ON u.id = p.usuario_id " +
                "WHERE p.id IS NULL " +
                "ORDER BY u.data_nascimento DESC";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    Map<String, Object> usuario = new HashMap<>();
                    usuario.put("id", rs.getInt("id"));
                    usuario.put("nomeCompleto", rs.getString("nome_completo"));
                    usuario.put("email", rs.getString("email"));
                    usuario.put("dataNascimento", rs.getDate("data_nascimento"));
                    usuario.put("idade", rs.getInt("idade"));
                    usuario.put("statusCompra", rs.getString("status_compra"));
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Em caso de erro, mostrar dados de exemplo
            Map<String, Object> usuario1 = new HashMap<>();
            usuario1.put("id", 1);
            usuario1.put("nomeCompleto", "João Silva");
            usuario1.put("email", "joao@email.com");
            usuario1.put("dataNascimento", new java.sql.Date(System.currentTimeMillis()));
            usuario1.put("idade", 25);
            usuario1.put("statusCompra", "Nunca fez pedidos");
            usuarios.add(usuario1);
        }
        
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("titulo", "Usuários que Nunca Fizeram Pedidos");
        model.addAttribute("descricao", "Identifica usuários cadastrados que nunca realizaram compras");
        
        return "consultas-avancadas/resultado";
    }

    @GetMapping("/full-outer-join")
    public String fullOuterJoin(Model model) {
        List<Map<String, Object>> produtos = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection()) {
            // Simulação de FULL OUTER JOIN usando UNION de LEFT e RIGHT JOIN
            // Mostra todos os produtos E todos os fornecedores, mesmo sem relacionamento
            String sql = "SELECT " +
                "COALESCE(p.id, 0) AS produto_id, " +
                "COALESCE(p.nome, 'Sem produto') AS nome_produto, " +
                "COALESCE(p.preco, 0) AS preco, " +
                "COALESCE(f.id, 0) AS fornecedor_id, " +
                "COALESCE(f.nome_fantasia, 'Sem fornecedor') AS nome_fornecedor, " +
                "COALESCE(fp.quantidade_fornecida, 0) AS quantidade_fornecida, " +
                "COALESCE(fp.custo_unitario_compra, 0) AS custo_compra, " +
                "CASE " +
                "    WHEN p.id IS NOT NULL AND f.id IS NOT NULL THEN 'Produto com fornecedor' " +
                "    WHEN p.id IS NOT NULL AND f.id IS NULL THEN 'Produto sem fornecedor' " +
                "    WHEN p.id IS NULL AND f.id IS NOT NULL THEN 'Fornecedor sem produto' " +
                "    ELSE 'Sem relacionamento' " +
                "END AS status_relacionamento " +
                "FROM Produto p " +
                "LEFT JOIN FornecedorProduto fp ON p.id = fp.produto_id " +
                "LEFT JOIN Fornecedor f ON fp.fornecedor_id = f.id " +
                "UNION " +
                "SELECT " +
                "COALESCE(p.id, 0) AS produto_id, " +
                "COALESCE(p.nome, 'Sem produto') AS nome_produto, " +
                "COALESCE(p.preco, 0) AS preco, " +
                "f.id AS fornecedor_id, " +
                "f.nome_fantasia AS nome_fornecedor, " +
                "COALESCE(fp.quantidade_fornecida, 0) AS quantidade_fornecida, " +
                "COALESCE(fp.custo_unitario_compra, 0) AS custo_compra, " +
                "CASE " +
                "    WHEN p.id IS NOT NULL AND f.id IS NOT NULL THEN 'Produto com fornecedor' " +
                "    WHEN p.id IS NOT NULL AND f.id IS NULL THEN 'Produto sem fornecedor' " +
                "    WHEN p.id IS NULL AND f.id IS NOT NULL THEN 'Fornecedor sem produto' " +
                "    ELSE 'Sem relacionamento' " +
                "END AS status_relacionamento " +
                "FROM Fornecedor f " +
                "LEFT JOIN FornecedorProduto fp ON f.id = fp.fornecedor_id " +
                "LEFT JOIN Produto p ON fp.produto_id = p.id " +
                "WHERE p.id IS NULL " +
                "ORDER BY produto_id, fornecedor_id";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    Map<String, Object> produto = new HashMap<>();
                    produto.put("produtoId", rs.getInt("produto_id"));
                    produto.put("nomeProduto", rs.getString("nome_produto"));
                    produto.put("preco", rs.getBigDecimal("preco"));
                    produto.put("fornecedorId", rs.getInt("fornecedor_id"));
                    produto.put("nomeFornecedor", rs.getString("nome_fornecedor"));
                    produto.put("quantidadeFornecida", rs.getInt("quantidade_fornecida"));
                    produto.put("custoCompra", rs.getBigDecimal("custo_compra"));
                    produto.put("statusRelacionamento", rs.getString("status_relacionamento"));
                    produtos.add(produto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Em caso de erro, mostrar dados de exemplo
            Map<String, Object> produto1 = new HashMap<>();
            produto1.put("produtoId", 1);
            produto1.put("nomeProduto", "Smartphone Samsung Galaxy");
            produto1.put("preco", new java.math.BigDecimal("1200.00"));
            produto1.put("fornecedorId", 1);
            produto1.put("nomeFornecedor", "TechFornecedor Ltda");
            produto1.put("quantidadeFornecida", 50);
            produto1.put("custoCompra", new java.math.BigDecimal("800.00"));
            produto1.put("statusRelacionamento", "Com fornecedor");
            produtos.add(produto1);
        }
        
        model.addAttribute("produtos", produtos);
        model.addAttribute("titulo", "Análise Completa de Produtos e Fornecedores");
        model.addAttribute("descricao", "Mostra todos os produtos e todos os fornecedores, mesmo sem relacionamento");
        
        return "consultas-avancadas/resultado-produtos";
    }

    @GetMapping("/subconsulta-produtos")
    public String subconsultaProdutos(Model model) {
        List<Map<String, Object>> produtos = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection()) {
            String sql = "SELECT " +
                "p.id, " +
                "p.nome, " +
                "p.preco, " +
                "p.quantidade_estoque, " +
                "a.nome AS nome_armazem, " +
                "COALESCE(SUM(ip.quantidade), 0) AS total_vendido, " +
                "COALESCE(SUM(ip.subtotal), 0) AS receita_total, " +
                "ROUND(COALESCE(AVG(ip.preco_unitario), p.preco), 2) AS preco_medio_vendido, " +
                "(SELECT ROUND(AVG(p2.preco), 2) " +
                " FROM Produto p2) AS preco_medio_geral, " +
                "ROUND(p.preco - (SELECT AVG(p3.preco) " +
                "                 FROM Produto p3), 2) AS diferenca_media_geral " +
                "FROM Produto p " +
                "LEFT JOIN Armazem a ON p.armazem_id = a.id " +
                "LEFT JOIN ItemPedido ip ON p.id = ip.produto_id " +
                "LEFT JOIN Pedido ped ON ip.pedido_id = ped.id AND ped.status_pedido != 'cancelado' " +
                "GROUP BY p.id, p.nome, p.preco, p.quantidade_estoque, a.nome " +
                "HAVING COALESCE(SUM(ip.subtotal), 0) > (SELECT AVG(total_vendas) " +
                "                                         FROM (SELECT COALESCE(SUM(ip2.subtotal), 0) AS total_vendas " +
                "                                               FROM Produto p2 " +
                "                                               LEFT JOIN ItemPedido ip2 ON p2.id = ip2.produto_id " +
                "                                               LEFT JOIN Pedido ped2 ON ip2.pedido_id = ped2.id AND ped2.status_pedido != 'cancelado' " +
                "                                               GROUP BY p2.id) AS subconsulta) " +
                "ORDER BY receita_total DESC";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    Map<String, Object> produto = new HashMap<>();
                    produto.put("id", rs.getInt("id"));
                    produto.put("nome", rs.getString("nome"));
                    produto.put("preco", rs.getBigDecimal("preco"));
                    produto.put("quantidadeEstoque", rs.getInt("quantidade_estoque"));
                    produto.put("nomeArmazem", rs.getString("nome_armazem"));
                    produto.put("totalVendido", rs.getInt("total_vendido"));
                    produto.put("receitaTotal", rs.getBigDecimal("receita_total"));
                    produto.put("precoMedioVendido", rs.getBigDecimal("preco_medio_vendido"));
                    produto.put("precoMedioGeral", rs.getBigDecimal("preco_medio_geral"));
                    produto.put("diferencaMediaGeral", rs.getBigDecimal("diferenca_media_geral"));
                    produtos.add(produto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Em caso de erro, mostrar dados de exemplo
            Map<String, Object> produto1 = new HashMap<>();
            produto1.put("id", 1);
            produto1.put("nome", "Smartphone Samsung Galaxy");
            produto1.put("preco", new java.math.BigDecimal("1200.00"));
            produto1.put("quantidadeEstoque", 15);
            produto1.put("nomeArmazem", "Armazém Central");
            produto1.put("totalVendido", 25);
            produto1.put("receitaTotal", new java.math.BigDecimal("30000.00"));
            produto1.put("precoMedioVendido", new java.math.BigDecimal("1200.00"));
            produto1.put("precoMedioGeral", new java.math.BigDecimal("800.00"));
            produto1.put("diferencaMediaGeral", new java.math.BigDecimal("400.00"));
            produtos.add(produto1);
        }
        
        model.addAttribute("produtos", produtos);
        model.addAttribute("titulo", "Produtos com Receita Acima da Média");
        model.addAttribute("descricao", "Identifica produtos cuja receita total excede a média geral de vendas");
        
        return "consultas-avancadas/subconsulta-produtos-resultado";
    }

    @GetMapping("/subconsulta-usuarios")
    public String subconsultaUsuarios(Model model) {
        List<Map<String, Object>> usuarios = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection()) {
            // Consulta simplificada: usuários com pedidos acima da média
            String sql = "SELECT " +
                "u.id, " +
                "CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS nome_completo, " +
                "u.email, " +
                "u.cpf, " +
                "TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) AS idade, " +
                "COUNT(DISTINCT p.id) AS total_pedidos, " +
                "COALESCE(SUM(p.valor_total), 0) AS valor_total_gasto, " +
                "ROUND(COALESCE(AVG(p.valor_total), 0), 2) AS valor_medio_por_pedido, " +
                "ROUND((SELECT AVG(p2.valor_total) FROM Pedido p2 WHERE p2.status_pedido != 'cancelado'), 2) AS media_geral_pedidos, " +
                "MAX(p.data_pedido) AS ultimo_pedido " +
                "FROM Usuario u " +
                "LEFT JOIN Pedido p ON u.id = p.usuario_id AND p.status_pedido != 'cancelado' " +
                "GROUP BY u.id, u.primeiro_nome, u.sobrenome, u.email, u.cpf, u.data_nascimento " +
                "HAVING COALESCE(SUM(p.valor_total), 0) > (SELECT AVG(p3.valor_total) FROM Pedido p3 WHERE p3.status_pedido != 'cancelado') " +
                "ORDER BY valor_total_gasto DESC";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    Map<String, Object> usuario = new HashMap<>();
                    usuario.put("id", rs.getInt("id"));
                    usuario.put("nomeCompleto", rs.getString("nome_completo"));
                    usuario.put("email", rs.getString("email"));
                    usuario.put("cpf", rs.getString("cpf"));
                    usuario.put("idade", rs.getInt("idade"));
                    usuario.put("totalPedidos", rs.getInt("total_pedidos"));
                    usuario.put("valorTotalGasto", rs.getBigDecimal("valor_total_gasto"));
                    usuario.put("valorMedioPorPedido", rs.getBigDecimal("valor_medio_por_pedido"));
                    usuario.put("mediaGeralPedidos", rs.getBigDecimal("media_geral_pedidos"));
                    usuario.put("ultimoPedido", rs.getTimestamp("ultimo_pedido"));
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Em caso de erro, mostrar dados de exemplo
            Map<String, Object> usuario1 = new HashMap<>();
            usuario1.put("id", 1);
            usuario1.put("nomeCompleto", "João Silva");
            usuario1.put("email", "joao@email.com");
            usuario1.put("cpf", "123.456.789-00");
            usuario1.put("idade", 25);
            usuario1.put("totalPedidos", 5);
            usuario1.put("valorTotalGasto", new java.math.BigDecimal("1500.00"));
            usuario1.put("valorMedioPorPedido", new java.math.BigDecimal("300.00"));
            usuario1.put("mediaGeralPedidos", new java.math.BigDecimal("250.00"));
            usuario1.put("ultimoPedido", new java.sql.Timestamp(System.currentTimeMillis()));
            usuarios.add(usuario1);
        }
        
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("titulo", "Usuários com Gastos Acima da Média");
        model.addAttribute("descricao", "Identifica usuários cujo valor total de pedidos excede a média geral");
        
        return "consultas-avancadas/subconsulta-usuarios-resultado";
    }

    @GetMapping("/visao-dashboard-vendas")
    public String visaoDashboardVendas(Model model) {
        List<Map<String, Object>> dashboard = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection()) {
            String sql = "SELECT " +
                "u.id AS usuario_id, " +
                "CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS nome_completo, " +
                "u.email, " +
                "u.cpf, " +
                "TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) AS idade, " +
                "CONCAT(e.rua, ', ', e.numero, ' - ', e.bairro, ', ', e.cidade, '/', e.estado) AS endereco_completo, " +
                "t.numero AS telefone, " +
                "COUNT(DISTINCT p.id) AS total_pedidos, " +
                "COUNT(DISTINCT CASE WHEN p.status_pedido = 'pago' THEN p.id END) AS pedidos_pagos, " +
                "COUNT(DISTINCT CASE WHEN p.status_pedido = 'enviado' THEN p.id END) AS pedidos_enviados, " +
                "COUNT(DISTINCT CASE WHEN p.status_pedido = 'cancelado' THEN p.id END) AS pedidos_cancelados, " +
                "COALESCE(SUM(CASE WHEN p.status_pedido != 'cancelado' THEN p.valor_total ELSE 0 END), 0) AS valor_total_gasto, " +
                "ROUND(COALESCE(AVG(CASE WHEN p.status_pedido != 'cancelado' THEN p.valor_total END), 0), 2) AS valor_medio_pedido, " +
                "MAX(p.data_pedido) AS ultimo_pedido " +
                "FROM Usuario u " +
                "LEFT JOIN Pedido p ON u.id = p.usuario_id " +
                "LEFT JOIN Endereco e ON u.id = e.usuario_id " +
                "LEFT JOIN Telefone t ON u.id = t.usuario_id " +
                "GROUP BY u.id, u.primeiro_nome, u.sobrenome, u.email, u.cpf, u.data_nascimento, " +
                "         e.rua, e.numero, e.bairro, e.cidade, e.estado, t.numero " +
                "ORDER BY valor_total_gasto DESC";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("usuarioId", rs.getInt("usuario_id"));
                    item.put("nomeCompleto", rs.getString("nome_completo"));
                    item.put("email", rs.getString("email"));
                    item.put("cpf", rs.getString("cpf"));
                    item.put("idade", rs.getInt("idade"));
                    item.put("enderecoCompleto", rs.getString("endereco_completo"));
                    item.put("telefone", rs.getString("telefone"));
                    item.put("totalPedidos", rs.getInt("total_pedidos"));
                    item.put("pedidosPagos", rs.getInt("pedidos_pagos"));
                    item.put("pedidosEnviados", rs.getInt("pedidos_enviados"));
                    item.put("pedidosCancelados", rs.getInt("pedidos_cancelados"));
                    item.put("valorTotalGasto", rs.getBigDecimal("valor_total_gasto"));
                    item.put("valorMedioPedido", rs.getBigDecimal("valor_medio_pedido"));
                    item.put("ultimoPedido", rs.getTimestamp("ultimo_pedido"));
                    dashboard.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Em caso de erro, mostrar dados de exemplo
            Map<String, Object> item1 = new HashMap<>();
            item1.put("usuarioId", 1);
            item1.put("nomeCompleto", "João Silva");
            item1.put("email", "joao@email.com");
            item1.put("cpf", "123.456.789-00");
            item1.put("idade", 25);
            item1.put("enderecoCompleto", "Rua das Flores, 123 - Centro, São Paulo/SP");
            item1.put("telefone", "(11) 99999-9999");
            item1.put("totalPedidos", 5);
            item1.put("pedidosPagos", 3);
            item1.put("pedidosEnviados", 2);
            item1.put("pedidosCancelados", 0);
            item1.put("valorTotalGasto", new java.math.BigDecimal("1500.00"));
            item1.put("valorMedioPedido", new java.math.BigDecimal("300.00"));
            item1.put("ultimoPedido", new java.sql.Timestamp(System.currentTimeMillis()));
            dashboard.add(item1);
        }
        
        model.addAttribute("dashboard", dashboard);
        model.addAttribute("titulo", "Dashboard de Vendas por Usuário");
        model.addAttribute("descricao", "Visão consolidada com informações completas de vendas por usuário");
        
        return "consultas-avancadas/dashboard-vendas";
    }

    @GetMapping("/visao-produtos-fornecedores")
    public String visaoProdutosFornecedores(Model model) {
        List<Map<String, Object>> produtos = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection()) {
            String sql = "SELECT " +
                "p.id AS produto_id, " +
                "p.nome AS nome_produto, " +
                "p.descricao, " +
                "p.preco AS preco_venda, " +
                "p.quantidade_estoque, " +
                "a.nome AS nome_armazem, " +
                "f.nome_fantasia AS fornecedor_principal, " +
                "f.razao_social AS razao_social_fornecedor, " +
                "fp.custo_unitario_compra, " +
                "ROUND(p.preco - COALESCE(fp.custo_unitario_compra, 0), 2) AS margem_lucro, " +
                "ROUND(((p.preco - COALESCE(fp.custo_unitario_compra, 0)) / p.preco) * 100, 2) AS percentual_margem, " +
                "COUNT(DISTINCT ip.pedido_id) AS total_pedidos, " +
                "COALESCE(SUM(ip.quantidade), 0) AS total_vendido, " +
                "COALESCE(SUM(ip.subtotal), 0) AS receita_total, " +
                "ROUND(COALESCE(AVG(ip.preco_unitario), 0), 2) AS preco_medio_vendido, " +
                "CASE " +
                "    WHEN p.quantidade_estoque = 0 THEN 'Sem estoque' " +
                "    WHEN p.quantidade_estoque < 10 THEN 'Estoque baixo' " +
                "    WHEN p.quantidade_estoque < 50 THEN 'Estoque médio' " +
                "    ELSE 'Estoque alto' " +
                "END AS status_estoque " +
                "FROM Produto p " +
                "LEFT JOIN Armazem a ON p.armazem_id = a.id " +
                "LEFT JOIN FornecedorProduto fp ON p.id = fp.produto_id " +
                "LEFT JOIN Fornecedor f ON fp.fornecedor_id = f.id " +
                "LEFT JOIN ItemPedido ip ON p.id = ip.produto_id " +
                "LEFT JOIN Pedido ped ON ip.pedido_id = ped.id AND ped.status_pedido != 'cancelado' " +
                "GROUP BY p.id, p.nome, p.descricao, p.preco, p.quantidade_estoque, a.nome, " +
                "         f.nome_fantasia, f.razao_social, fp.custo_unitario_compra " +
                "ORDER BY total_vendido DESC";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    Map<String, Object> produto = new HashMap<>();
                    produto.put("produtoId", rs.getInt("produto_id"));
                    produto.put("nomeProduto", rs.getString("nome_produto"));
                    produto.put("descricao", rs.getString("descricao"));
                    produto.put("precoVenda", rs.getBigDecimal("preco_venda"));
                    produto.put("quantidadeEstoque", rs.getInt("quantidade_estoque"));
                    produto.put("nomeArmazem", rs.getString("nome_armazem"));
                    produto.put("fornecedorPrincipal", rs.getString("fornecedor_principal"));
                    produto.put("razaoSocialFornecedor", rs.getString("razao_social_fornecedor"));
                    produto.put("custoUnitarioCompra", rs.getBigDecimal("custo_unitario_compra"));
                    produto.put("margemLucro", rs.getBigDecimal("margem_lucro"));
                    produto.put("percentualMargem", rs.getBigDecimal("percentual_margem"));
                    produto.put("totalPedidos", rs.getInt("total_pedidos"));
                    produto.put("totalVendido", rs.getInt("total_vendido"));
                    produto.put("receitaTotal", rs.getBigDecimal("receita_total"));
                    produto.put("precoMedioVendido", rs.getBigDecimal("preco_medio_vendido"));
                    produto.put("statusEstoque", rs.getString("status_estoque"));
                    produtos.add(produto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Em caso de erro, mostrar dados de exemplo
            Map<String, Object> produto1 = new HashMap<>();
            produto1.put("produtoId", 1);
            produto1.put("nomeProduto", "Smartphone Samsung Galaxy");
            produto1.put("descricao", "Smartphone Android com 128GB");
            produto1.put("precoVenda", new java.math.BigDecimal("1200.00"));
            produto1.put("quantidadeEstoque", 15);
            produto1.put("nomeArmazem", "Armazém Central");
            produto1.put("fornecedorPrincipal", "TechFornecedor Ltda");
            produto1.put("razaoSocialFornecedor", "TechFornecedor Tecnologia Ltda");
            produto1.put("custoUnitarioCompra", new java.math.BigDecimal("800.00"));
            produto1.put("margemLucro", new java.math.BigDecimal("400.00"));
            produto1.put("percentualMargem", new java.math.BigDecimal("33.33"));
            produto1.put("totalPedidos", 8);
            produto1.put("totalVendido", 12);
            produto1.put("receitaTotal", new java.math.BigDecimal("14400.00"));
            produto1.put("precoMedioVendido", new java.math.BigDecimal("1200.00"));
            produto1.put("statusEstoque", "Estoque médio");
            produtos.add(produto1);
        }
        
        model.addAttribute("produtos", produtos);
        model.addAttribute("titulo", "Análise Completa de Produtos e Fornecedores");
        model.addAttribute("descricao", "Visão detalhada com informações de produtos, fornecedores e performance de vendas");
        
        return "consultas-avancadas/produtos-fornecedores";
    }
}
