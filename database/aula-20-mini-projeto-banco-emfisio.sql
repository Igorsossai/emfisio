USE emfisio_db;
GO

-- =====================================
-- AULA 20 - MINI PROJETO BANCO EMFISIO
-- =====================================

-- 1. Cadastrar cliente
EXEC sp_cadastrar_cliente
    @nome = 'Beatriz Almeida',
    @idade = 34,
    @telefone = '(27) 94444-1111';

-- 2. Cadastrar profissional
EXEC sp_cadastrar_profissional
    @nome = 'Gustavo Nunes',
    @especialidade = 'Pilates e Reabilitação',
    @percentual_comissao = 35.00,
    @disponivel = 1;

-- 3. Cadastrar serviço
EXEC sp_cadastrar_servico
    @nome = 'Avaliação Funcional',
    @duracao_minutos = 50,
    @valor = 130.00;

-- 4. Buscar IDs
DECLARE @cliente_id INT;
DECLARE @profissional_id INT;
DECLARE @servico_id INT;
DECLARE @agendamento_id INT;

SELECT @cliente_id = id
FROM clientes
WHERE nome = 'Beatriz Almeida';

SELECT @profissional_id = id
FROM profissionais
WHERE nome = 'Gustavo Nunes';

SELECT @servico_id = id
FROM servicos
WHERE nome = 'Avaliação Funcional';

SELECT
    @cliente_id AS cliente_id,
    @profissional_id AS profissional_id,
    @servico_id AS servico_id;

-- 5. Criar agendamento
EXEC sp_criar_agendamento
    @cliente_id = @cliente_id,
    @profissional_id = @profissional_id,
    @servico_id = @servico_id,
    @status_agendamento = 'AGENDADO',
    @horas_antes_atendimento = 48;

-- 6. Capturar agendamento criado
SELECT TOP 1 @agendamento_id = agendamento_id
FROM vw_agendamentos_completos
WHERE cliente = 'Beatriz Almeida'
ORDER BY criado_em DESC;

SELECT @agendamento_id AS agendamento_criado;

-- 7. Marcar como realizado
EXEC sp_atualizar_status_agendamento_seguro
    @agendamento_id = @agendamento_id,
    @status_agendamento = 'REALIZADO';

-- 8. Registrar pagamento e processar comissão
EXEC sp_registrar_pagamento_e_comissao
    @agendamento_id = @agendamento_id,
    @valor = 130.00,
    @forma_pagamento = 'PIX',
    @status_pagamento = 'PAGO';

-- 9. Consultar agendamento completo
SELECT *
FROM vw_agendamentos_completos
WHERE agendamento_id = @agendamento_id;

-- 10. Consultar pagamento completo
SELECT *
FROM vw_pagamentos_completos
WHERE agendamento_id = @agendamento_id;

-- 11. Consultar comissão
SELECT *
FROM vw_comissoes_completas
WHERE agendamento_id = @agendamento_id;

-- 12. Consultar resumo financeiro
SELECT *
FROM vw_resumo_financeiro;

-- 13. Consultar indicadores operacionais
SELECT *
FROM vw_indicadores_operacionais;

-- 14. Consultar faturamento por profissional
SELECT *
FROM vw_faturamento_por_profissional
ORDER BY total_recebido DESC;

-- 15. Consultar comissões por profissional
SELECT *
FROM vw_comissoes_por_profissional
ORDER BY total_comissao DESC;

-- 16. Consultar líquido por profissional
SELECT *
FROM vw_liquido_por_profissional
ORDER BY liquido_clinica DESC;

-- 17. Consultar auditoria
SELECT *
FROM auditoria_sistema
ORDER BY data_evento DESC;

-- 18. Testar erro e rollback
EXEC sp_registrar_pagamento_seguro
    @agendamento_id = @agendamento_id,
    @valor = -130.00,
    @forma_pagamento = 'PIX',
    @status_pagamento = 'PAGO';

-- 19. Conferir se pagamento negativo foi salvo
SELECT *
FROM pagamentos
WHERE valor < 0;

-- 20. Testar log de erro com serviço inválido
EXEC sp_cadastrar_servico_seguro
    @nome = '',
    @duracao_minutos = 60,
    @valor = 100.00;

SELECT *
FROM log_erros_sistema
ORDER BY data_erro DESC;

-- 21. Testar DELETE lógico
DELETE FROM clientes
WHERE nome = 'Beatriz Almeida';

SELECT *
FROM clientes
WHERE nome = 'Beatriz Almeida';

SELECT *
FROM auditoria_sistema
ORDER BY data_evento DESC;

-- 22. Reativar cliente para manter base de teste
UPDATE clientes
SET ativo = 1
WHERE nome = 'Beatriz Almeida';

-- 23. Relatório final do fluxo
SELECT *
FROM vw_agendamentos_completos
WHERE cliente = 'Beatriz Almeida';

SELECT *
FROM vw_pagamentos_completos
WHERE cliente = 'Beatriz Almeida';

SELECT *
FROM vw_comissoes_completas
WHERE cliente = 'Beatriz Almeida';

SELECT *
FROM vw_resumo_financeiro;

SELECT *
FROM vw_indicadores_operacionais;