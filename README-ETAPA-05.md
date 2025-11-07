# ETAPA 05 - Funções, Procedimentos e Triggers

## Arquivos SQL Organizados

Os arquivos SQL foram organizados em 3 arquivos separados e limpos:

1. **06-funcoes.sql** - Contém as 2 funções SQL
2. **07-procedures.sql** - Contém as 2 procedures SQL
3. **08-triggers.sql** - Contém a tabela de logs e os 2 triggers

## Ordem de Execução

Execute os arquivos SQL na seguinte ordem no MySQL Workbench:

1. **Primeiro**: Execute `06-funcoes.sql`
2. **Segundo**: Execute `07-procedures.sql`
3. **Terceiro**: Execute `08-triggers.sql`

## Conteúdo de Cada Arquivo

### 06-funcoes.sql
- `fn_calcular_desconto_por_valor` - Calcula desconto progressivo por valor do pedido (com condicional)
- `fn_produto_estoque_baixo` - Verifica se produto está com estoque baixo

### 07-procedures.sql
- `sp_atualizar_status_pedido` - Atualiza status do pedido com validação de transição
- `sp_processar_pagamento_baixar_estoque` - Processa pagamento e baixa estoque usando CURSOR

### 08-triggers.sql
- Criação da tabela `Log_Auditoria`
- `tr_pedido_after_update_log` - Registra alterações em Pedido na tabela de logs
- `tr_itempedido_after_insert_atualiza_pedido` - Atualiza valor_total do pedido automaticamente

## Integração na Aplicação

### Função
- **fn_produto_estoque_baixo**: Usada na lista de produtos (`/ecommerce-dashboard/produtos`)
  - Mostra badge "Fn: Baixo" para produtos com estoque baixo

### Procedure
- **sp_atualizar_status_pedido**: Usada na lista de pedidos (`/ecommerce-dashboard/pedidos`)
  - Botão "Atualizar Status" chama a procedure

### Trigger
- **tr_pedido_after_update_log**: Funciona automaticamente
  - Quando status do pedido é atualizado, registra em `Log_Auditoria`
  - Visualize em `/ecommerce-dashboard/logs`

## Verificação

Após executar os arquivos, verifique:

```sql

SHOW FUNCTION STATUS WHERE Db = 'ecommerce_bd';


SHOW PROCEDURE STATUS WHERE Db = 'ecommerce_bd';

SHOW TRIGGERS WHERE `Table` = 'Pedido';
SHOW TRIGGERS WHERE `Table` = 'ItemPedido';
```

