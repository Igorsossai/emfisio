USE emfisio_db;
GO

-- =====================================
-- AULA 14 - UPDATE, DELETE LÓGICO E FILTROS
-- =====================================

-- 1. Conferir cliente antes de atualizar
SELECT *
FROM clientes
WHERE id = 1;

-- 2. Atualizar telefone de cliente
UPDATE clientes
SET telefone = '(27) 98888-0000'
WHERE id = 1;

-- 3. Atualizar vários dados do cliente
UPDATE clientes
SET 
    nome = 'Ana Paula Silva',
    idade = 43,
    telefone = '(27) 98888-0000'
WHERE id = 1;

-- 4. Conferir cliente atualizado
SELECT *
FROM clientes
WHERE id = 1;

-- 5. Inativar cliente
UPDATE clientes
SET ativo = 0
WHERE id = 3;

-- 6. Reativar cliente
UPDATE clientes
SET ativo = 1
WHERE id = 3;

-- 7. Atualizar profissional
UPDATE profissionais
SET
    especialidade = 'Pilates e Reabilitação',
    percentual_comissao = 35.00,
    disponivel = 1
WHERE id = 2;

-- 8. Inativar profissional
UPDATE profissionais
SET 
    ativo = 0,
    disponivel = 0
WHERE id = 3;

-- 9. Reativar profissional
UPDATE profissionais
SET
    ativo = 1,
    disponivel = 1
WHERE id = 3;

-- 10. Atualizar serviço
UPDATE servicos
SET
    nome = 'Sessão de Fisioterapia Ortopédica',
    duracao_minutos = 60,
    valor = 180.00
WHERE id = 1;

-- 11. Inativar serviço
UPDATE servicos
SET ativo = 0
WHERE id = 3;

-- 12. Clientes ativos
SELECT *
FROM clientes
WHERE ativo = 1;

-- 13. Clientes inativos
SELECT *
FROM clientes
WHERE ativo = 0;

-- 14. Profissionais ativos e disponíveis
SELECT *
FROM profissionais
WHERE ativo = 1
AND disponivel = 1;

-- 15. Profissionais ativos, mas indisponíveis
SELECT *
FROM profissionais
WHERE ativo = 1
AND disponivel = 0;

-- 16. Serviços ativos
SELECT *
FROM servicos
WHERE ativo = 1;

-- 17. Buscar cliente por parte do nome
SELECT *
FROM clientes
WHERE nome LIKE '%Ana%';

-- 18. Buscar profissional por especialidade
SELECT *
FROM profissionais
WHERE especialidade LIKE '%Pilates%';

-- 19. Serviços com valor entre 100 e 180
SELECT *
FROM servicos
WHERE valor BETWEEN 100.00 AND 180.00;

-- 20. Clientes entre 30 e 50 anos
SELECT *
FROM clientes
WHERE idade BETWEEN 30 AND 50;

-- 21. Agendamentos realizados
SELECT *
FROM agendamentos
WHERE status_agendamento = 'REALIZADO';

-- 22. Agendamentos pendentes de pagamento
SELECT *
FROM agendamentos
WHERE status_pagamento = 'PENDENTE';

-- 23. Atualizar status de pagamento
UPDATE agendamentos
SET status_pagamento = 'PAGO'
WHERE id = 3;

-- 24. Atualizar status de atendimento
UPDATE agendamentos
SET status_agendamento = 'REALIZADO'
WHERE id = 3;

-- 25. Agendamentos que geram comissão
SELECT
    a.id,
    c.nome AS cliente,
    p.nome AS profissional,
    s.nome AS servico,
    s.valor,
    p.percentual_comissao,
    (s.valor * p.percentual_comissao / 100) AS valor_comissao
FROM agendamentos a
INNER JOIN clientes c ON c.id = a.cliente_id
INNER JOIN profissionais p ON p.id = a.profissional_id
INNER JOIN servicos s ON s.id = a.servico_id
WHERE a.status_pagamento = 'PAGO'
AND a.status_agendamento = 'REALIZADO';

-- 26. Faturamento total
SELECT
    SUM(s.valor) AS faturamento_total
FROM agendamentos a
INNER JOIN servicos s ON s.id = a.servico_id
WHERE a.status_pagamento = 'PAGO';

-- 27. Faltas e cancelamentos
SELECT
    a.id,
    c.nome AS cliente,
    p.nome AS profissional,
    s.nome AS servico,
    a.status_agendamento,
    a.status_pagamento
FROM agendamentos a
INNER JOIN clientes c ON c.id = a.cliente_id
INNER JOIN profissionais p ON p.id = a.profissional_id
INNER JOIN servicos s ON s.id = a.servico_id
WHERE a.status_agendamento = 'FALTOU'
OR a.status_agendamento = 'CANCELADO';

-- 28. Atualizar horas antes do atendimento
UPDATE agendamentos
SET horas_antes_atendimento = 8
WHERE id = 1;

-- 29. Cancelamentos não permitidos
SELECT *
FROM agendamentos
WHERE horas_antes_atendimento < 24;

-- 30. Cancelamentos permitidos
SELECT *
FROM agendamentos
WHERE horas_antes_atendimento >= 24;