<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Novo Produto - E-commerce</title>
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
            max-width: 800px;
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
            padding: 2rem;
        }
        
        .form-group {
            margin-bottom: 1.5rem;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: bold;
            color: #333;
        }
        
        .form-group input,
        .form-group select,
        .form-group textarea {
            width: 100%;
            padding: 0.75rem;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 1rem;
            transition: border-color 0.3s;
        }
        
        .form-group input:focus,
        .form-group select:focus,
        .form-group textarea:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.2);
        }
        
        .form-group textarea {
            resize: vertical;
            min-height: 80px;
        }
        
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
        }
        
        .btn {
            background: #667eea;
            color: white;
            padding: 0.75rem 1.5rem;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            display: inline-block;
            transition: background-color 0.3s;
            cursor: pointer;
            font-size: 1rem;
        }
        
        .btn:hover {
            background: #5a6fd8;
        }
        
        .btn-secondary {
            background: #6c757d;
        }
        
        .btn-secondary:hover {
            background: #5a6268;
        }
        
        .btn-success {
            background: #27ae60;
        }
        
        .btn-success:hover {
            background: #229954;
        }
        
        .form-actions {
            display: flex;
            gap: 1rem;
            justify-content: flex-end;
            margin-top: 2rem;
        }
        
        .alert {
            padding: 1rem;
            margin: 1rem 0;
            border-radius: 5px;
        }
        
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        .required {
            color: #e74c3c;
        }
        
        .help-text {
            color: #666;
            font-size: 0.875rem;
            margin-top: 0.25rem;
        }
        
        @media (max-width: 768px) {
            .form-row {
                grid-template-columns: 1fr;
            }
            
            .form-actions {
                flex-direction: column;
            }
            
            .btn {
                width: 100%;
                text-align: center;
            }
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>➕ Novo Produto</h1>
        <p>Cadastrar novo produto no sistema</p>
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
                <h2>Dados do Produto</h2>
                <a href="produto" class="btn btn-secondary">← Voltar</a>
            </div>
            
            <div class="card-body">
                <c:if test="${param.error == 'erro_criar_produto'}">
                    <div class="alert alert-error">
                        ❌ Erro ao criar produto. Verifique os dados e tente novamente.
                    </div>
                </c:if>
                
                <form method="post" action="produto">
                    <input type="hidden" name="action" value="create">
                    
                    <div class="form-group">
                        <label for="nome">Nome do Produto <span class="required">*</span></label>
                        <input type="text" id="nome" name="nome" required>
                        <div class="help-text">Nome descritivo do produto</div>
                    </div>
                    
                    <div class="form-group">
                        <label for="descricao">Descrição</label>
                        <textarea id="descricao" name="descricao" placeholder="Descrição detalhada do produto..."></textarea>
                        <div class="help-text">Descrição opcional do produto</div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label for="preco">Preço (R$) <span class="required">*</span></label>
                            <input type="number" id="preco" name="preco" step="0.01" min="0" required>
                            <div class="help-text">Preço de venda do produto</div>
                        </div>
                        
                        <div class="form-group">
                            <label for="quantidadeEstoque">Quantidade em Estoque <span class="required">*</span></label>
                            <input type="number" id="quantidadeEstoque" name="quantidadeEstoque" min="0" required>
                            <div class="help-text">Quantidade disponível em estoque</div>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="armazemId">Armazém <span class="required">*</span></label>
                        <select id="armazemId" name="armazemId" required>
                            <option value="">Selecione um armazém...</option>
                            <c:forEach var="armazem" items="${armazens}">
                                <option value="${armazem.split(' - ')[0]}">${armazem}</option>
                            </c:forEach>
                        </select>
                        <div class="help-text">Armazém onde o produto será armazenado</div>
                    </div>
                    
                    <div class="form-actions">
                        <a href="produto" class="btn btn-secondary">Cancelar</a>
                        <button type="submit" class="btn btn-success">✅ Criar Produto</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <script>
        // Validação básica do formulário
        document.querySelector('form').addEventListener('submit', function(e) {
            const preco = parseFloat(document.getElementById('preco').value);
            const quantidade = parseInt(document.getElementById('quantidadeEstoque').value);
            const nome = document.getElementById('nome').value.trim();
            
            if (nome.length < 3) {
                alert('Nome do produto deve ter pelo menos 3 caracteres');
                e.preventDefault();
                return;
            }
            
            if (preco <= 0) {
                alert('Preço deve ser maior que zero');
                e.preventDefault();
                return;
            }
            
            if (quantidade < 0) {
                alert('Quantidade em estoque não pode ser negativa');
                e.preventDefault();
                return;
            }
        });
        
        // Formatação do preço
        document.getElementById('preco').addEventListener('input', function(e) {
            let value = e.target.value;
            if (value && !isNaN(value)) {
                e.target.value = parseFloat(value).toFixed(2);
            }
        });
    </script>
</body>
</html>
