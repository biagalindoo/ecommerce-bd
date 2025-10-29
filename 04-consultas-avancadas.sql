--Índice 1: Para otimizar consultas por data de nascimento (usado em análises de idade)
CREATE INDEX idx_usuario_data_nascimento ON Usuario(data_nascimento);

--Índice 2: Para otimizar consultas por preço de produto (usado em análises de preço)
CREATE INDEX idx_produto_preco ON Produto(preco);

-- Índice 3: Para otimizar consultas por status de pedido (usado em análises de vendas)
CREATE INDEX idx_pedido_status ON Pedido(status_pedido);

-- Índice 4: Para otimizar consultas por data de pedido (usado em análises temporais)
CREATE INDEX idx_pedido_data ON Pedido(data_pedido);

SELECT 
    u.id,
    CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS nome_completo,
    u.email,
    u.data_nascimento,
    TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) AS idade,
    'Nunca fez pedidos' AS status_compra
FROM Usuario u
LEFT JOIN Pedido p ON u.id = p.usuario_id
WHERE p.id IS NULL
ORDER BY u.data_nascimento DESC;


SELECT 
    COALESCE(p.id, fp.produto_id) AS produto_id,
    COALESCE(p.nome, 'Produto não encontrado') AS nome_produto,
    COALESCE(p.preco, 0) AS preco,
    COALESCE(f.id, fp.fornecedor_id) AS fornecedor_id,
    COALESCE(f.nome_fantasia, 'Fornecedor não encontrado') AS nome_fornecedor,
    COALESCE(fp.quantidade_fornecida, 0) AS quantidade_fornecida,
    COALESCE(fp.custo_unitario_compra, 0) AS custo_compra
FROM Produto p
FULL OUTER JOIN FornecedorProduto fp ON p.id = fp.produto_id
FULL OUTER JOIN Fornecedor f ON fp.fornecedor_id = f.id
ORDER BY produto_id, fornecedor_id;


SELECT 
    p.id,
    p.nome,
    p.preco,
    p.armazem_id,
    a.nome AS nome_armazem,
    (SELECT ROUND(AVG(p2.preco), 2) 
     FROM Produto p2 
     WHERE p2.armazem_id = p.armazem_id) AS preco_medio_armazem,
    ROUND(p.preco - (SELECT AVG(p3.preco) 
                     FROM Produto p3 
                     WHERE p3.armazem_id = p.armazem_id), 2) AS diferenca_media
FROM Produto p
LEFT JOIN Armazem a ON p.armazem_id = a.id
WHERE p.preco > (SELECT AVG(p4.preco) 
                 FROM Produto p4 
                 WHERE p4.armazem_id = p.armazem_id)
ORDER BY diferenca_media DESC;


SELECT 
    u.id,
    CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS nome_completo,
    u.email,
    COUNT(p.id) AS total_pedidos,
    SUM(p.valor_total) AS valor_total_gasto,
    ROUND(AVG(p.valor_total), 2) AS valor_medio_por_pedido,
    (SELECT ROUND(AVG(p2.valor_total), 2) 
     FROM Pedido p2 
     WHERE p2.status_pedido != 'cancelado') AS media_geral_pedidos
FROM Usuario u
INNER JOIN Pedido p ON u.id = p.usuario_id
WHERE p.status_pedido != 'cancelado'
GROUP BY u.id, u.primeiro_nome, u.sobrenome, u.email
HAVING SUM(p.valor_total) > (SELECT AVG(p3.valor_total) 
                             FROM Pedido p3 
                             WHERE p3.status_pedido != 'cancelado')
ORDER BY valor_total_gasto DESC;


-- VISÃO 1: Dashboard de Vendas por Usuário
-- Descrição: Visão consolidada com informações completas de vendas por usuário
-- Justificativa: Permite análise rápida do desempenho de vendas por usuário,
-- incluindo dados pessoais, endereço, telefone e estatísticas de pedidos
CREATE VIEW vw_dashboard_vendas_usuario AS
SELECT 
    u.id AS usuario_id,
    CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS nome_completo,
    u.email,
    u.cpf,
    TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) AS idade,
    CONCAT(e.rua, ', ', e.numero, ' - ', e.bairro, ', ', e.cidade, '/', e.estado) AS endereco_completo,
    t.numero AS telefone,
    COUNT(DISTINCT p.id) AS total_pedidos,
    COUNT(DISTINCT CASE WHEN p.status_pedido = 'pago' THEN p.id END) AS pedidos_pagos,
    COUNT(DISTINCT CASE WHEN p.status_pedido = 'enviado' THEN p.id END) AS pedidos_enviados,
    COUNT(DISTINCT CASE WHEN p.status_pedido = 'cancelado' THEN p.id END) AS pedidos_cancelados,
    COALESCE(SUM(CASE WHEN p.status_pedido != 'cancelado' THEN p.valor_total ELSE 0 END), 0) AS valor_total_gasto,
    ROUND(COALESCE(AVG(CASE WHEN p.status_pedido != 'cancelado' THEN p.valor_total END), 0), 2) AS valor_medio_pedido,
    MAX(p.data_pedido) AS ultimo_pedido
FROM Usuario u
LEFT JOIN Pedido p ON u.id = p.usuario_id
LEFT JOIN Endereco e ON u.id = e.usuario_id
LEFT JOIN Telefone t ON u.id = t.usuario_id
GROUP BY u.id, u.primeiro_nome, u.sobrenome, u.email, u.cpf, u.data_nascimento, 
         e.rua, e.numero, e.bairro, e.cidade, e.estado, t.numero;

-- VISÃO 2: Análise Completa de Produtos e Fornecedores
-- Descrição: Visão detalhada com informações de produtos, fornecedores e performance de vendas
-- Justificativa: Permite análise completa do catálogo de produtos, incluindo
-- informações de fornecedores, estoque, vendas e margem de lucro
CREATE VIEW vw_analise_produtos_fornecedores AS
SELECT 
    p.id AS produto_id,
    p.nome AS nome_produto,
    p.descricao,
    p.preco AS preco_venda,
    p.quantidade_estoque,
    a.nome AS nome_armazem,
    f.nome_fantasia AS fornecedor_principal,
    f.razao_social AS razao_social_fornecedor,
    fp.custo_unitario_compra,
    ROUND(p.preco - COALESCE(fp.custo_unitario_compra, 0), 2) AS margem_lucro,
    ROUND(((p.preco - COALESCE(fp.custo_unitario_compra, 0)) / p.preco) * 100, 2) AS percentual_margem,
    COUNT(DISTINCT ip.pedido_id) AS total_pedidos,
    COALESCE(SUM(ip.quantidade), 0) AS total_vendido,
    COALESCE(SUM(ip.subtotal), 0) AS receita_total,
    ROUND(COALESCE(AVG(ip.preco_unitario), 0), 2) AS preco_medio_vendido,
    CASE 
        WHEN p.quantidade_estoque = 0 THEN 'Sem estoque'
        WHEN p.quantidade_estoque < 10 THEN 'Estoque baixo'
        WHEN p.quantidade_estoque < 50 THEN 'Estoque médio'
        ELSE 'Estoque alto'
    END AS status_estoque
FROM Produto p
LEFT JOIN Armazem a ON p.armazem_id = a.id
LEFT JOIN FornecedorProduto fp ON p.id = fp.produto_id
LEFT JOIN Fornecedor f ON fp.fornecedor_id = f.id
LEFT JOIN ItemPedido ip ON p.id = ip.produto_id
LEFT JOIN Pedido ped ON ip.pedido_id = ped.id AND ped.status_pedido != 'cancelado'
GROUP BY p.id, p.nome, p.descricao, p.preco, p.quantidade_estoque, a.nome,
         f.nome_fantasia, f.razao_social, fp.custo_unitario_compra;


-- Teste da Visão 1: Dashboard de Vendas por Usuário
-- SELECT * FROM vw_dashboard_vendas_usuario ORDER BY valor_total_gasto DESC LIMIT 10;

-- Teste da Visão 2: Análise Completa de Produtos e Fornecedores
-- SELECT * FROM vw_analise_produtos_fornecedores WHERE status_estoque = 'Estoque baixo' ORDER BY total_vendido DESC;
