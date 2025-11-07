USE ecommerce_bd;

DELIMITER $$

-- Função 1 (condicional): calcula desconto baseado no valor total do pedido
-- Regra: pedidos maiores têm desconto maior
CREATE FUNCTION fn_calcular_desconto_por_valor(
    p_valor_total DECIMAL(10,2)
) RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE v_desconto DECIMAL(10,2) DEFAULT 0.00;
    
    IF p_valor_total IS NULL OR p_valor_total <= 0 THEN
        RETURN 0.00;
    ELSEIF p_valor_total >= 1000.00 THEN
        SET v_desconto = p_valor_total * 0.10; 
    ELSEIF p_valor_total >= 500.00 THEN
        SET v_desconto = p_valor_total * 0.05;
    ELSEIF p_valor_total >= 200.00 THEN
        SET v_desconto = p_valor_total * 0.02; 
    ELSE
        SET v_desconto = 0.00; 
    END IF;
    
    RETURN ROUND(v_desconto, 2);
END$$

-- Função 2 (condicional): verifica se um produto tem estoque suficiente para atender uma quantidade
-- Regra: evita vendas de produtos sem estoque disponível
CREATE FUNCTION fn_produto_tem_estoque_suficiente(
    p_produto_id INT,
    p_quantidade_solicitada INT
) RETURNS TINYINT(1)
DETERMINISTIC
BEGIN
    DECLARE v_estoque_atual INT;
    SELECT quantidade_estoque INTO v_estoque_atual
    FROM Produto
    WHERE id = p_produto_id;

    IF v_estoque_atual IS NULL THEN
        RETURN 0;
    END IF;
    IF v_estoque_atual >= p_quantidade_solicitada THEN
        RETURN 1; 
    ELSE
        RETURN 0;  
    END IF;
END$$

DELIMITER ;
SHOW FUNCTION STATUS WHERE Db = 'ecommerce_bd';

