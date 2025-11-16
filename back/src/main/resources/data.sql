MERGE INTO produtos (ID, ATIVO, CATEGORIA, CODIGO, NOME, PRECO_UNITARIO, QUANTIDADE_ESTOQUE) VALUES
(1, true, 'Motor', 'MT-001', 'Filtro de Óleo', 29.90, 50),
(2, true, 'Motor', 'MT-002', 'Filtro de Ar', 45.00, 30),
(3, true, 'Elétrica', 'EL-001', 'Bateria 60Ah', 420.00, 12),
(4, true, 'Elétrica', 'EL-002', 'Alternador 90A', 680.00, 5),
(5, true, 'Suspensão', 'SP-001', 'Amortecedor Dianteiro', 250.00, 20),
(6, true, 'Suspensão', 'SP-002', 'Mola Traseira', 180.00, 18),
(7, true, 'Freios', 'FR-001', 'Pastilha de Freio', 95.00, 40),
(8, true, 'Freios', 'FR-002', 'Disco de Freio', 210.00, 25),
(9, false, 'Acessórios', 'AC-001', 'Tapete Automotivo', 75.00, 0),
(10, true, 'Acessórios', 'AC-002', 'Lâmpada LED H7', 35.00, 60);

MERGE INTO usuarios (id, ativo, email, nome_completo, perfil, senha) VALUES
(1, TRUE, 'admin@sistema.com', 'Administrador do Sistema', 'ADMIN', 'senhaAdmin123'),
(2, TRUE, 'operador@sistema.com', 'Operador Padrão', 'OPERADOR', 'senhaOperador123');

MERGE INTO vendas (id, data_venda, total, troco, valor_recebido, usuario_id) VALUES
(2, '2025-11-06 13:36:03.222437', 115.11, 4.89, 120.00, 2),
(7, '2025-11-07 10:21:15.883122', 240.00, 10.00, 250.00, 2),
(3, '2025-11-07 17:45:54.112903', 89.50, 0.50, 90.00, 2),
(4, '2025-11-08 09:15:12.445877', 360.99, 39.01, 400.00, 1),
(5, '2025-11-08 18:04:33.716542', 128.00, 2.00, 130.00, 2);

MERGE INTO itens_venda (id, preco_unitario, quantidade, nome_produto_snapshot, produto_id, venda_id) VALUES
(1, 29.90, 2, 'TESTE', 1, 2),
(2, 35.00, 1, 'TESTE',10, 2),
(3, 20.31, 1, 'TESTE',7, 2),
(4, 95.00, 2, 'TESTE',7, 7),
(5, 35.00, 1, 'TESTE',10, 7),
(6, 15.00, 1, 'TESTE',1, 7),
(7, 29.90, 1, 'TESTE',1, 3),
(8, 35.00, 1, 'TESTE',10, 3),
(9, 24.60, 1, 'TESTE',7, 3),
(10, 250.00, 1, 'TESTE',5, 4),
(11, 95.00, 1, 'TESTE',7, 4),
(12, 15.99, 1, 'TESTE',1, 4),
(13, 95.00, 1, 'TESTE',7, 5),
(14, 33.00, 1, 'TESTE',10, 5);


MERGE INTO movimentos_estoque (id, motivo, data, quantidade, tipo, nome_produto_snapshot, produto_id, usuario_id) VALUES
-- VENDA 2
(2, 'Venda ID: 2', '2025-11-06 13:36:03', -2, 'SAIDA_VENDA', 'TESTE', 1, 2),
(3, 'Venda ID: 2', '2025-11-06 13:36:03', -1, 'SAIDA_VENDA', 'TESTE',10, 2),
(4, 'Venda ID: 2', '2025-11-06 13:36:03', -1, 'SAIDA_VENDA', 'TESTE',7, 2),

-- VENDA 7
(5, 'Venda ID: 7', '2025-11-07 10:21:15', -2, 'SAIDA_VENDA', 'TESTE',7, 2),
(6, 'Venda ID: 7', '2025-11-07 10:21:15', -1, 'SAIDA_VENDA', 'TESTE',10, 2),
(7, 'Venda ID: 7', '2025-11-07 10:21:15', -1, 'SAIDA_VENDA', 'TESTE',1, 2),

-- VENDA 3
(8, 'Venda ID: 3', '2025-11-07 17:45:54', -1, 'SAIDA_VENDA', 'TESTE',1, 2),
(9, 'Venda ID: 3', '2025-11-07 17:45:54', -1, 'SAIDA_VENDA', 'TESTE',10, 2),
(10, 'Venda ID: 3', '2025-11-07 17:45:54', -1, 'SAIDA_VENDA', 'TESTE',7, 2),

-- VENDA 4
(11, 'Venda ID: 4', '2025-11-08 09:15:12', -1, 'SAIDA_VENDA', 'TESTE',5, 2),
(12, 'Venda ID: 4', '2025-11-08 09:15:12', -1, 'SAIDA_VENDA', 'TESTE',7, 2),
(13, 'Venda ID: 4', '2025-11-08 09:15:12', -1, 'SAIDA_VENDA', 'TESTE',1, 2),

-- VENDA 5
(14, 'Venda ID: 5', '2025-11-08 18:04:33', -1, 'SAIDA_VENDA', 'TESTE',7, 2),
(15, 'Venda ID: 5', '2025-11-08 18:04:33', -1, 'SAIDA_VENDA', 'TESTE',10, 2);

-- -- PRODUTOS (último ID = 10)
-- ALTER TABLE PRODUTOS
--     ALTER COLUMN ID RESTART WITH 11;
--
-- -- ITENS_VENDA (último ID = 14)
-- ALTER TABLE ITENS_VENDA
--     ALTER COLUMN ID RESTART WITH 15;
--
-- -- MOVIMENTOS_ESTOQUE (último ID = 15)
-- ALTER TABLE MOVIMENTOS_ESTOQUE
--     ALTER COLUMN ID RESTART WITH 16;
--
-- -- VENDAS (último ID = 7)
-- ALTER TABLE VENDAS
--     ALTER COLUMN ID RESTART WITH 6;
--
-- -- USUARIOS (IDs inseridos = 1 e 2)
-- ALTER TABLE USUARIOS
--     ALTER COLUMN ID RESTART WITH 3;