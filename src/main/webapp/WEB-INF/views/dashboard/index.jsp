<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - E-commerce</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f5f5;
            color: #333;
        }
        
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 1rem 2rem;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .header h1 {
            font-size: 2rem;
            margin-bottom: 0.5rem;
        }
        
        .nav {
            background-color: #2c3e50;
            padding: 1rem 2rem;
        }
        
        .nav a {
            color: white;
            text-decoration: none;
            margin-right: 2rem;
            padding: 0.5rem 1rem;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        
        .nav a:hover {
            background-color: #34495e;
        }
        
        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 2rem;
        }
        
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }
        
        .stat-card {
            background: white;
            padding: 1.5rem;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        
        .stat-card h3 {
            color: #667eea;
            font-size: 2rem;
            margin-bottom: 0.5rem;
        }
        
        .stat-card p {
            color: #666;
            font-size: 1.1rem;
        }
        
        .content-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 2rem;
        }
        
        .card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        
        .card-header {
            background: #667eea;
            color: white;
            padding: 1rem;
            font-size: 1.2rem;
            font-weight: bold;
        }
        
        .card-body {
            padding: 1rem;
        }
        
        .list-item {
            padding: 0.75rem;
            border-bottom: 1px solid #eee;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .list-item:last-child {
            border-bottom: none;
        }
        
        .btn {
            background: #667eea;
            color: white;
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            display: inline-block;
            transition: background-color 0.3s;
        }
        
        .btn:hover {
            background: #5a6fd8;
        }
        
        .btn-danger {
            background: #e74c3c;
        }
        
        .btn-danger:hover {
            background: #c0392b;
        }
        
        .alert {
            padding: 1rem;
            margin: 1rem 0;
            border-radius: 5px;
        }
        
        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        @media (max-width: 768px) {
            .content-grid {
                grid-template-columns: 1fr;
            }
            
            .nav {
                text-align: center;
            }
            
            .nav a {
                display: inline-block;
                margin: 0.25rem;
            }
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>🏪 Dashboard E-commerce</h1>
        <p>Painel de controle do sistema</p>
    </div>
    
    <div class="nav">
        <a href="dashboard">📊 Dashboard</a>
        <a href="usuario">👥 Usuários</a>
        <a href="produto">📦 Produtos</a>
        <a href="usuario?action=form">➕ Novo Usuário</a>
        <a href="produto?action=form">➕ Novo Produto</a>
    </div>
    
    <div class="container">
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                ${error}
            </div>
        </c:if>
        
        <!-- Estatísticas -->
        <div class="stats-grid">
            <div class="stat-card">
                <h3>${stats.totalUsuarios}</h3>
                <p>Total de Usuários</p>
            </div>
            <div class="stat-card">
                <h3>${stats.totalProdutos}</h3>
                <p>Total de Produtos</p>
            </div>
            <div class="stat-card">
                <h3>${stats.totalPedidos}</h3>
                <p>Total de Pedidos</p>
            </div>
            <div class="stat-card">
                <h3><fmt:formatNumber value="${stats.valorTotalEstoque}" type="currency" currencyCode="BRL"/></h3>
                <p>Valor em Estoque</p>
            </div>
        </div>
        
        <!-- Conteúdo Principal -->
        <div class="content-grid">
            <!-- Produtos com Estoque Baixo -->
            <div class="card">
                <div class="card-header">
                    ⚠️ Produtos com Estoque Baixo (${stats.produtosEstoqueBaixo})
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty produtosEstoqueBaixo}">
                            <c:forEach var="produto" items="${produtosEstoqueBaixo}" varStatus="status">
                                <c:if test="${status.index < 5}">
                                    <div class="list-item">
                                        <div>
                                            <strong>${produto.nome}</strong><br>
                                            <small>Estoque: ${produto.quantidadeEstoque} | ${produto.precoFormatado}</small>
                                        </div>
                                        <a href="produto?action=edit&id=${produto.id}" class="btn">Editar</a>
                                    </div>
                                </c:if>
                            </c:forEach>
                            <div style="text-align: center; margin-top: 1rem;">
                                <a href="produto?action=estoque-baixo" class="btn">Ver Todos</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p style="text-align: center; color: #666; padding: 2rem;">
                                ✅ Todos os produtos estão com estoque adequado!
                            </p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            
            <!-- Usuários Recentes -->
            <div class="card">
                <div class="card-header">
                    👥 Usuários Recentes
                </div>
                <div class="card-body">
                    <c:forEach var="usuario" items="${usuariosRecentes}">
                        <div class="list-item">
                            <div>
                                <strong>${usuario.nome}</strong><br>
                                <small>${usuario.email}</small>
                            </div>
                            <a href="usuario?action=edit&id=${usuario.id}" class="btn">Editar</a>
                        </div>
                    </c:forEach>
                    <div style="text-align: center; margin-top: 1rem;">
                        <a href="usuario" class="btn">Ver Todos</a>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Produtos Mais Caros -->
        <div class="card" style="margin-top: 2rem;">
            <div class="card-header">
                💰 Produtos Mais Caros
            </div>
            <div class="card-body">
                <c:forEach var="produto" items="${produtosCaros}">
                    <div class="list-item">
                        <div>
                            <strong>${produto.nome}</strong><br>
                            <small>Armazém: ${produto.armazem} | Estoque: ${produto.quantidadeEstoque}</small>
                        </div>
                        <div>
                            <strong><fmt:formatNumber value="${produto.preco}" type="currency" currencyCode="BRL"/></strong>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</body>
</html>
