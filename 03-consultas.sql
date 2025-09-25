-- ===============================================
-- CONSULTAS SQL - DIFERENTES NÍVEIS DE DIFICULDADE
-- ===============================================

-- ===============================================
-- CONSULTA 1: SIMPLES - Listar usuários com seus telefones
-- ===============================================
-- Nível: Básico
-- Descrição: Lista todos os usuários com seus respectivos telefones
-- Dificuldade: Baixa

SELECT 
    u.id,
    u.primeiro_nome,
    u.sobrenome,
    u.email,
    t.numero AS telefone
FROM Usuario u
LEFT JOIN Telefone t ON u.id = t.usuario_id
ORDER BY u.primeiro_nome, u.sobrenome;

-- ===============================================
-- CONSULTA 2: COM JOIN - Relatório completo de pedidos
-- ===============================================
-- Nível: Intermediário
-- Descrição: Relatório detalhado de pedidos com informações do usuário, 
--           endereço, produtos e status de pagamento
-- Dificuldade: Média

SELECT 
    p.id AS pedido_id,
    p.data_pedido,
    p.status_pedido,
    p.valor_total,
    CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS nome_completo,
    u.email,
    CONCAT(e.rua, ', ', e.numero, ' - ', e.bairro, ', ', e.cidade, '/', e.estado) AS endereco_completo,
    pr.nome AS produto,
    ip.quantidade,
    ip.preco_unitario,
    ip.subtotal,
    pg.forma_pagamento,
    pg.status_pagamento
FROM Pedido p
INNER JOIN Usuario u ON p.usuario_id = u.id
INNER JOIN Endereco e ON u.id = e.usuario_id
INNER JOIN ItemPedido ip ON p.id = ip.pedido_id
INNER JOIN Produto pr ON ip.produto_id = pr.id
LEFT JOIN Pagamento pg ON p.id = pg.pedido_id
ORDER BY p.data_pedido DESC, p.id;

-- ===============================================
-- CONSULTA 3: COMPLEXA - Análise de vendas por fornecedor
-- ===============================================
-- Nível: Avançado
-- Descrição: Análise completa de vendas por fornecedor, incluindo
--           quantidade vendida, receita total e margem de lucro
-- Dificuldade: Alta

SELECT 
    f.nome_fantasia AS fornecedor,
    f.razao_social,
    COUNT(DISTINCT ip.pedido_id) AS total_pedidos,
    SUM(ip.quantidade) AS total_produtos_vendidos,
    SUM(ip.subtotal) AS receita_total,
    SUM(fp.quantidade_fornecida * fp.custo_unitario_compra) AS custo_total_fornecido,
    ROUND(
        (SUM(ip.subtotal) - SUM(fp.quantidade_fornecida * fp.custo_unitario_compra)) / 
        SUM(fp.quantidade_fornecida * fp.custo_unitario_compra) * 100, 2
    ) AS margem_lucro_percentual,
    ROUND(SUM(ip.subtotal) - SUM(fp.quantidade_fornecida * fp.custo_unitario_compra), 2) AS lucro_total
FROM Fornecedor f
INNER JOIN FornecedorProduto fp ON f.id = fp.fornecedor_id
INNER JOIN Produto p ON fp.produto_id = p.id
INNER JOIN ItemPedido ip ON p.id = ip.produto_id
INNER JOIN Pedido ped ON ip.pedido_id = ped.id
WHERE ped.status_pedido IN ('pago', 'enviado')
GROUP BY f.id, f.nome_fantasia, f.razao_social
HAVING total_pedidos > 0
ORDER BY receita_total DESC;

-- ===============================================
-- CONSULTA 4: AVANÇADA - Ranking de produtos mais vendidos com análise de margem
-- ===============================================
-- Nível: Expert
-- Descrição: Ranking completo dos produtos mais vendidos com análise
--           de margem de lucro, estoque e performance por armazém
-- Dificuldade: Muito Alta

WITH vendas_produto AS (
    SELECT 
        p.id AS produto_id,
        p.nome,
        p.preco,
        p.quantidade_estoque,
        a.nome AS armazem,
        COUNT(ip.id) AS total_vendas,
        SUM(ip.quantidade) AS quantidade_total_vendida,
        SUM(ip.subtotal) AS receita_total,
        AVG(ip.preco_unitario) AS preco_medio_venda
    FROM Produto p
    INNER JOIN Armazem a ON p.armazem_id = a.id
    LEFT JOIN ItemPedido ip ON p.id = ip.produto_id
    LEFT JOIN Pedido ped ON ip.pedido_id = ped.id
    WHERE ped.status_pedido IN ('pago', 'enviado') OR ped.status_pedido IS NULL
    GROUP BY p.id, p.nome, p.preco, p.quantidade_estoque, a.nome
),
fornecedor_custo AS (
    SELECT 
        fp.produto_id,
        AVG(fp.custo_unitario_compra) AS custo_medio,
        MIN(fp.custo_unitario_compra) AS custo_minimo,
        MAX(fp.custo_unitario_compra) AS custo_maximo
    FROM FornecedorProduto fp
    GROUP BY fp.produto_id
)
SELECT 
    vp.produto_id,
    vp.nome AS produto,
    vp.armazem,
    vp.preco AS preco_atual,
    vp.quantidade_estoque,
    vp.total_vendas,
    vp.quantidade_total_vendida,
    vp.receita_total,
    vp.preco_medio_venda,
    fc.custo_medio,
    fc.custo_minimo,
    fc.custo_maximo,
    ROUND(vp.preco - fc.custo_medio, 2) AS margem_absoluta,
    ROUND((vp.preco - fc.custo_medio) / fc.custo_medio * 100, 2) AS margem_percentual,
    CASE 
        WHEN vp.quantidade_estoque = 0 THEN 'ESGOTADO'
        WHEN vp.quantidade_estoque < 10 THEN 'BAIXO ESTOQUE'
        WHEN vp.quantidade_estoque < 25 THEN 'ESTOQUE MÉDIO'
        ELSE 'ESTOQUE ALTO'
    END AS status_estoque,
    ROUND(vp.receita_total / NULLIF(vp.total_vendas, 0), 2) AS ticket_medio
FROM vendas_produto vp
LEFT JOIN fornecedor_custo fc ON vp.produto_id = fc.produto_id
WHERE vp.total_vendas > 0 OR vp.quantidade_estoque > 0
ORDER BY 
    vp.receita_total DESC,
    vp.quantidade_total_vendida DESC,
    margem_percentual DESC;

-- ===============================================
-- CONSULTA BÔNUS: Análise de performance de usuários
-- ===============================================
-- Nível: Intermediário-Avançado
-- Descrição: Análise de comportamento dos usuários baseada em pedidos e carrinho
-- Dificuldade: Média-Alta

SELECT 
    u.id,
    CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS nome_completo,
    u.email,
    e.cidade,
    e.estado,
    COUNT(DISTINCT p.id) AS total_pedidos,
    COUNT(DISTINCT c.id) AS carrinhos_criados,
    SUM(CASE WHEN p.status_pedido = 'pago' THEN 1 ELSE 0 END) AS pedidos_pagos,
    SUM(CASE WHEN p.status_pedido = 'cancelado' THEN 1 ELSE 0 END) AS pedidos_cancelados,
    COALESCE(SUM(CASE WHEN p.status_pedido = 'pago' THEN p.valor_total ELSE 0 END), 0) AS valor_total_gasto,
    ROUND(AVG(CASE WHEN p.status_pedido = 'pago' THEN p.valor_total ELSE NULL END), 2) AS ticket_medio,
    MAX(p.data_pedido) AS ultimo_pedido,
    DATEDIFF(CURDATE(), MAX(p.data_pedido)) AS dias_desde_ultimo_pedido
FROM Usuario u
LEFT JOIN Endereco e ON u.id = e.usuario_id
LEFT JOIN Pedido p ON u.id = p.usuario_id
LEFT JOIN Carrinho c ON u.id = c.usuario_id
GROUP BY u.id, u.primeiro_nome, u.sobrenome, u.email, e.cidade, e.estado
HAVING total_pedidos > 0
ORDER BY valor_total_gasto DESC, total_pedidos DESC;
