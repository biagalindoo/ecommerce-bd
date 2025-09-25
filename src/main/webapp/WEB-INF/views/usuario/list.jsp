<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Usuários - E-commerce</title>
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
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .card-body {
            padding: 1rem;
        }
        
        .search-form {
            display: flex;
            gap: 1rem;
            margin-bottom: 1rem;
            align-items: center;
        }
        
        .search-form input {
            flex: 1;
            padding: 0.5rem;
            border: 1px solid #ddd;
            border-radius: 5px;
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
            cursor: pointer;
        }
        
        .btn:hover {
            background: #5a6fd8;
        }
        
        .btn-success {
            background: #27ae60;
        }
        
        .btn-success:hover {
            background: #229954;
        }
        
        .btn-danger {
            background: #e74c3c;
        }
        
        .btn-danger:hover {
            background: #c0392b;
        }
        
        .btn-warning {
            background: #f39c12;
        }
        
        .btn-warning:hover {
            background: #e67e22;
        }
        
        .table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
        }
        
        .table th,
        .table td {
            padding: 0.75rem;
            text-align: left;
            border-bottom: 1px solid #eee;
        }
        
        .table th {
            background-color: #f8f9fa;
            font-weight: bold;
            color: #333;
        }
        
        .table tr:hover {
            background-color: #f8f9fa;
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
        
        .actions {
            display: flex;
            gap: 0.5rem;
        }
        
        .actions .btn {
            padding: 0.25rem 0.5rem;
            font-size: 0.875rem;
        }
        
        @media (max-width: 768px) {
            .table {
                font-size: 0.875rem;
            }
            
            .table th,
            .table td {
                padding: 0.5rem;
            }
            
            .actions {
                flex-direction: column;
            }
            
            .search-form {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>👥 Gerenciar Usuários</h1>
        <p>Lista de usuários cadastrados no sistema</p>
    </div>
    
    <div class="nav">
        <a href="dashboard">📊 Dashboard</a>
        <a href="usuario">👥 Usuários</a>
        <a href="produto">📦 Produtos</a>
        <a href="usuario?action=form">➕ Novo Usuário</a>
        <a href="produto?action=form">➕ Novo Produto</a>
    </div>
    
    <div class="container">
        <div class="card">
            <div class="card-header">
                <h2>Lista de Usuários</h2>
                <a href="usuario?action=form" class="btn btn-success">➕ Novo Usuário</a>
            </div>
            
            <div class="card-body">
                <!-- Mensagens -->
                <c:if test="${param.success == 'usuario_criado'}">
                    <div class="alert alert-success">
                        ✅ Usuário criado com sucesso!
                    </div>
                </c:if>
                <c:if test="${param.success == 'usuario_atualizado'}">
                    <div class="alert alert-success">
                        ✅ Usuário atualizado com sucesso!
                    </div>
                </c:if>
                <c:if test="${param.success == 'usuario_deletado'}">
                    <div class="alert alert-success">
                        ✅ Usuário deletado com sucesso!
                    </div>
                </c:if>
                <c:if test="${param.error == 'erro_criar_usuario'}">
                    <div class="alert alert-error">
                        ❌ Erro ao criar usuário!
                    </div>
                </c:if>
                <c:if test="${param.error == 'erro_atualizar_usuario'}">
                    <div class="alert alert-error">
                        ❌ Erro ao atualizar usuário!
                    </div>
                </c:if>
                <c:if test="${param.error == 'erro_deletar_usuario'}">
                    <div class="alert alert-error">
                        ❌ Erro ao deletar usuário!
                    </div>
                </c:if>
                <c:if test="${param.error == 'usuario_nao_encontrado'}">
                    <div class="alert alert-error">
                        ❌ Usuário não encontrado!
                    </div>
                </c:if>
                
                <!-- Formulário de Busca -->
                <form class="search-form" method="get" action="usuario">
                    <input type="hidden" name="action" value="search">
                    <input type="text" name="nome" placeholder="Buscar por nome..." 
                           value="${searchTerm}" class="form-control">
                    <button type="submit" class="btn">🔍 Buscar</button>
                    <a href="usuario" class="btn btn-warning">🔄 Limpar</a>
                </form>
                
                <!-- Tabela de Usuários -->
                <c:choose>
                    <c:when test="${not empty usuarios}">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nome</th>
                                    <th>Email</th>
                                    <th>CPF</th>
                                    <th>Data Nascimento</th>
                                    <th>Ações</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="usuario" items="${usuarios}">
                                    <tr>
                                        <td>${usuario.id}</td>
                                        <td>${usuario.nomeCompleto}</td>
                                        <td>${usuario.email}</td>
                                        <td>${usuario.cpf}</td>
                                        <td>${usuario.dataNascimento}</td>
                                        <td>
                                            <div class="actions">
                                                <a href="usuario?action=edit&id=${usuario.id}" 
                                                   class="btn btn-warning">✏️ Editar</a>
                                                <a href="usuario?action=delete&id=${usuario.id}" 
                                                   class="btn btn-danger"
                                                   onclick="return confirm('Tem certeza que deseja deletar este usuário?')">🗑️ Deletar</a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        
                        <div style="margin-top: 1rem; text-align: center; color: #666;">
                            Total de usuários: ${usuarios.size()}
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div style="text-align: center; padding: 2rem; color: #666;">
                            <c:choose>
                                <c:when test="${not empty searchTerm}">
                                    <p>🔍 Nenhum usuário encontrado para "${searchTerm}"</p>
                                    <a href="usuario" class="btn">Ver Todos os Usuários</a>
                                </c:when>
                                <c:otherwise>
                                    <p>📝 Nenhum usuário cadastrado ainda</p>
                                    <a href="usuario?action=form" class="btn btn-success">Criar Primeiro Usuário</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</body>
</html>
