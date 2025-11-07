USE ecommerce_bd;

DELIMITER $$

-- Procedure 1 (atualização direta): atualizar status do pedido com validação de transição
CREATE PROCEDURE sp_atualizar_status_pedido(
    IN p_pedido_id INT,
    IN p_novo_status VARCHAR(20)
)
BEGIN
    DECLARE v_atual VARCHAR(20);
    DECLARE v_permitido TINYINT DEFAULT 0;

    SELECT status_pedido INTO v_atual FROM Pedido WHERE id = p_pedido_id;
    IF v_atual IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Pedido não encontrado';
    END IF;

    IF v_atual = 'aberto' AND (p_novo_status IN ('pago','cancelado')) THEN
        SET v_permitido = 1;
    ELSEIF v_atual = 'pago' AND (p_novo_status IN ('enviado','cancelado')) THEN
        SET v_permitido = 1;
    ELSEIF v_atual = 'enviado' AND p_novo_status = 'enviado' THEN
        SET v_permitido = 1;
    ELSEIF v_atual = p_novo_status THEN
        SET v_permitido = 1; 
    END IF;

    IF v_permitido = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Transição de status não permitida';
    END IF;

    UPDATE Pedido
    SET status_pedido = p_novo_status
    WHERE id = p_pedido_id;
END$$

-- Procedure 2 (CURSOR): processa pagamento confirmado e baixa estoque item a item
-- Justificativa do cursor: precisa iterar ItemPedido, validar estoque por item, aplicar regras específicas
-- (ex: produtos com estoque crítico, produtos com preço diferente, calcular margem por item)
CREATE PROCEDURE sp_processar_pagamento_baixar_estoque(
    IN p_pedido_id INT
)
BEGIN
    DECLARE v_produto_id INT;
    DECLARE v_quantidade INT;
    DECLARE v_preco_unit DECIMAL(10,2);
    DECLARE v_estoque_atual INT;
    DECLARE v_preco_produto DECIMAL(10,2);
    DECLARE v_done INT DEFAULT 0;
    DECLARE v_itens_processados INT DEFAULT 0;
    DECLARE v_itens_com_problema INT DEFAULT 0;

    DECLARE cur_itens CURSOR FOR
        SELECT produto_id, quantidade, preco_unitario
        FROM ItemPedido
        WHERE pedido_id = p_pedido_id;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = 1;

    IF (SELECT COUNT(*) FROM Pedido WHERE id = p_pedido_id AND status_pedido = 'pago') = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Pedido não encontrado ou não está com status pago';
    END IF;

    -- processando cada item individualmente usando cursor
    OPEN cur_itens;
    itens_loop: LOOP
        FETCH cur_itens INTO v_produto_id, v_quantidade, v_preco_unit;
        IF v_done = 1 THEN
            LEAVE itens_loop;
        END IF;

        SET v_itens_processados = v_itens_processados + 1;

        SELECT quantidade_estoque, preco INTO v_estoque_atual, v_preco_produto
        FROM Produto
        WHERE id = v_produto_id;

        IF v_estoque_atual IS NULL THEN
            SET v_itens_com_problema = v_itens_com_problema + 1;
            INSERT INTO Log_Auditoria(tabela, operacao, registro_id, detalhes)
            VALUES ('Pedido', 'ERRO', p_pedido_id, 
                   CONCAT('Produto ', v_produto_id, ' não encontrado no estoque'));
        ELSEIF v_estoque_atual < v_quantidade THEN
            SET v_itens_com_problema = v_itens_com_problema + 1;
            INSERT INTO Log_Auditoria(tabela, operacao, registro_id, detalhes)
            VALUES ('Pedido', 'ERRO', p_pedido_id, 
                   CONCAT('Estoque insuficiente. Produto ', v_produto_id, 
                          ': necessário ', v_quantidade, ', disponível ', v_estoque_atual));
        ELSE
            UPDATE Produto
            SET quantidade_estoque = quantidade_estoque - v_quantidade
            WHERE id = v_produto_id;

            IF v_preco_produto <> v_preco_unit THEN
                INSERT INTO Log_Auditoria(tabela, operacao, registro_id, detalhes)
                VALUES ('Pedido', 'AVISO', p_pedido_id, 
                       CONCAT('Produto ', v_produto_id, ' teve preço alterado: pedido=', 
                              v_preco_unit, ', atual=', v_preco_produto));
            END IF;
        END IF;
    END LOOP;
    CLOSE cur_itens;

    INSERT INTO Log_Auditoria(tabela, operacao, registro_id, detalhes)
    VALUES ('Pedido', 'PROCESSADO', p_pedido_id, 
           CONCAT('Processamento concluído. Itens processados: ', v_itens_processados,
                  ', com problemas: ', v_itens_com_problema));
END$$

DELIMITER ;
SHOW PROCEDURE STATUS WHERE Db = 'ecommerce_bd';

