USE ecommerce_bd;

CREATE TABLE IF NOT EXISTS Log_Auditoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tabela VARCHAR(100) NOT NULL,
    operacao VARCHAR(20) NOT NULL,
    registro_id INT NULL,
    data_evento DATETIME DEFAULT CURRENT_TIMESTAMP,
    detalhes TEXT NULL
);

DELIMITER $$

-- Trigger 1 (auditoria): registra alterações em Pedido
-- atualiza a tabela de logs automaticamente
CREATE TRIGGER tr_pedido_after_update_log
AFTER UPDATE ON Pedido
FOR EACH ROW
BEGIN
    IF (OLD.status_pedido <> NEW.status_pedido) OR (OLD.valor_total <> NEW.valor_total) THEN
        INSERT INTO Log_Auditoria(tabela, operacao, registro_id, detalhes)
        VALUES (
            'Pedido',
            'UPDATE',
            NEW.id,
            CONCAT('status: ', OLD.status_pedido, ' -> ', NEW.status_pedido,
                   '; valor_total: ', IFNULL(OLD.valor_total,0), ' -> ', IFNULL(NEW.valor_total,0))
        );
    END IF;
END$$

-- Trigger 2: valida e atualiza automaticamente valor_total do Pedido quando ItemPedido é inserido
CREATE TRIGGER tr_itempedido_after_insert_atualiza_pedido
AFTER INSERT ON ItemPedido
FOR EACH ROW
BEGIN
    DECLARE v_novo_total DECIMAL(10,2);
    SELECT COALESCE(SUM(subtotal), 0.00) INTO v_novo_total
    FROM ItemPedido
    WHERE pedido_id = NEW.pedido_id;
    
    UPDATE Pedido
    SET valor_total = v_novo_total
    WHERE id = NEW.pedido_id;
END$$

DELIMITER ;
SHOW TRIGGERS WHERE `Table` = 'Pedido';
SHOW TRIGGERS WHERE `Table` = 'ItemPedido';

