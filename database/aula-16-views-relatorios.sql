USE emfisio_db;
GO

-- =====================================
-- AULA 16 - VIEWS PARA RELATÓRIOS
-- =====================================

IF OBJECT_ID('vw_clientes_ativos', 'V') IS NOT NULL
    DROP VIEW vw_clientes_ativos;
GO

CREATE VIEW vw_clientes_ativos AS
SELECT
    id,
    nome,
    idade,
    telefone,
    ativo,
    criado_em
FROM clientes
WHERE ativo = 1;
GO

IF OBJECT_ID('vw_profissionais_disponiveis', 'V') IS NOT NULL
    DROP VIEW vw_profissionais_disponiveis;
GO

CREATE VIEW vw_profissionais_disponiveis AS
SELECT
    id,
    nome,
    especialidade,
    percentual_comissao,
    disponivel,
    ativo,
    criado_em
FROM profissionais
WHERE ativo = 1
AND disponivel = 1;
GO

IF OBJECT_ID('vw_servicos_ativos', 'V') IS NOT NULL
    DROP VIEW vw_servicos_ativos;
GO

CREATE VIEW vw_servicos_ativos AS
SELECT
    id,
    nome,
    duracao_minutos,
    valor,
    ativo,
    criado_em
FROM servicos
WHERE ativo = 1;
GO

IF OBJECT_ID('vw_agendamentos_completos', 'V') IS NOT NULL
    DROP VIEW vw_agendamentos_completos;
GO

CREATE VIEW vw_agendamentos_completos AS
SELECT
    a.id AS agendamento_id,
    c.id AS cliente_id,
    c.nome AS cliente,
    p.id AS profissional_id,
    p.nome AS profissional,
    p.especialidade,
    s.id AS servico_id,
    s.nome AS servico,
    s.valor AS valor_servico,
    a.status_agendamento,
    a.status_pagamento,
    a.horas_antes_atendimento,
    a.criado_em
FROM agendamentos a
INNER JOIN clientes c ON c.id = a.cliente_id
INNER JOIN profissionais p ON p.id = a.profissional_id
INNER JOIN servicos s ON s.id = a.servico_id;
GO

IF OBJECT_ID('vw_agendamentos_realizados', 'V') IS NOT NULL
    DROP VIEW vw_agendamentos_realizados;
GO

CREATE VIEW vw_agendamentos_realizados AS
SELECT
    agendamento_id,
    cliente,
    profissional,
    especialidade,
    servico,
    valor_servico,
    status_agendamento,
    status_pagamento,
    criado_em
FROM vw_agendamentos_completos
WHERE status_agendamento = 'REALIZADO';
GO

IF OBJECT_ID('vw_faltas_cancelamentos', 'V') IS NOT NULL
    DROP VIEW vw_faltas_cancelamentos;
GO

CREATE VIEW vw_faltas_cancelamentos AS
SELECT
    agendamento_id,
    cliente,
    profissional,
    servico,
    valor_servico,
    status_agendamento,
    status_pagamento,
    criado_em
FROM vw_agendamentos_completos
WHERE status_agendamento = 'FALTOU'
OR status_agendamento = 'CANCELADO';
GO

IF OBJECT_ID('vw_pagamentos_completos', 'V') IS NOT NULL
    DROP VIEW vw_pagamentos_completos;
GO

CREATE VIEW vw_pagamentos_completos AS
SELECT
    pg.id AS pagamento_id,
    pg.agendamento_id,
    c.id AS cliente_id,
    c.nome AS cliente,
    p.id AS profissional_id,
    p.nome AS profissional,
    s.id AS servico_id,
    s.nome AS servico,
    pg.valor,
    pg.forma_pagamento,
    pg.status_pagamento,
    pg.data_pagamento,
    pg.criado_em
FROM pagamentos pg
INNER JOIN agendamentos a ON a.id = pg.agendamento_id
INNER JOIN clientes c ON c.id = a.cliente_id
INNER JOIN profissionais p ON p.id = a.profissional_id
INNER JOIN servicos s ON s.id = a.servico_id;
GO

IF OBJECT_ID('vw_pagamentos_pagos', 'V') IS NOT NULL
    DROP VIEW vw_pagamentos_pagos;
GO

CREATE VIEW vw_pagamentos_pagos AS
SELECT
    pagamento_id,
    agendamento_id,
    cliente,
    profissional,
    servico,
    valor,
    forma_pagamento,
    status_pagamento,
    data_pagamento
FROM vw_pagamentos_completos
WHERE status_pagamento = 'PAGO';
GO

IF OBJECT_ID('vw_pagamentos_pendentes', 'V') IS NOT NULL
    DROP VIEW vw_pagamentos_pendentes;
GO

CREATE VIEW vw_pagamentos_pendentes AS
SELECT
    pagamento_id,
    agendamento_id,
    cliente,
    profissional,
    servico,
    valor,
    forma_pagamento,
    status_pagamento,
    data_pagamento
FROM vw_pagamentos_completos
WHERE status_pagamento = 'PENDENTE';
GO

IF OBJECT_ID('vw_comissoes_completas', 'V') IS NOT NULL
    DROP VIEW vw_comissoes_completas;
GO

CREATE VIEW vw_comissoes_completas AS
SELECT
    cm.id AS comissao_id,
    cm.agendamento_id,
    cm.pagamento_id,
    c.nome AS cliente,
    p.id AS profissional_id,
    p.nome AS profissional,
    s.nome AS servico,
    pg.valor AS valor_pago,
    cm.percentual_comissao,
    cm.valor_comissao,
    cm.data_geracao
FROM comissoes cm
INNER JOIN agendamentos a ON a.id = cm.agendamento_id
INNER JOIN clientes c ON c.id = a.cliente_id
INNER JOIN profissionais p ON p.id = cm.profissional_id
INNER JOIN servicos s ON s.id = a.servico_id
INNER JOIN pagamentos pg ON pg.id = cm.pagamento_id;
GO

IF OBJECT_ID('vw_comissoes_por_profissional', 'V') IS NOT NULL
    DROP VIEW vw_comissoes_por_profissional;
GO

CREATE VIEW vw_comissoes_por_profissional AS
SELECT
    profissional_id,
    profissional,
    COUNT(*) AS quantidade_comissoes,
    SUM(valor_pago) AS total_recebido_vinculado,
    SUM(valor_comissao) AS total_comissao
FROM vw_comissoes_completas
GROUP BY
    profissional_id,
    profissional;
GO

IF OBJECT_ID('vw_faturamento_por_forma_pagamento', 'V') IS NOT NULL
    DROP VIEW vw_faturamento_por_forma_pagamento;
GO

CREATE VIEW vw_faturamento_por_forma_pagamento AS
SELECT
    forma_pagamento,
    COUNT(*) AS quantidade_pagamentos,
    SUM(valor) AS total_recebido
FROM pagamentos
WHERE status_pagamento = 'PAGO'
GROUP BY forma_pagamento;
GO

IF OBJECT_ID('vw_faturamento_por_profissional', 'V') IS NOT NULL
    DROP VIEW vw_faturamento_por_profissional;
GO

CREATE VIEW vw_faturamento_por_profissional AS
SELECT
    p.id AS profissional_id,
    p.nome AS profissional,
    COUNT(pg.id) AS quantidade_pagamentos,
    SUM(pg.valor) AS total_recebido
FROM pagamentos pg
INNER JOIN agendamentos a ON a.id = pg.agendamento_id
INNER JOIN profissionais p ON p.id = a.profissional_id
WHERE pg.status_pagamento = 'PAGO'
GROUP BY
    p.id,
    p.nome;
GO

IF OBJECT_ID('vw_liquido_por_profissional', 'V') IS NOT NULL
    DROP VIEW vw_liquido_por_profissional;
GO

CREATE VIEW vw_liquido_por_profissional AS
SELECT
    p.id AS profissional_id,
    p.nome AS profissional,
    ISNULL(SUM(pg.valor), 0) AS total_recebido,
    ISNULL(SUM(cm.valor_comissao), 0) AS total_comissao,
    ISNULL(SUM(pg.valor), 0) - ISNULL(SUM(cm.valor_comissao), 0) AS liquido_clinica
FROM pagamentos pg
INNER JOIN agendamentos a ON a.id = pg.agendamento_id
INNER JOIN profissionais p ON p.id = a.profissional_id
LEFT JOIN comissoes cm ON cm.pagamento_id = pg.id
WHERE pg.status_pagamento = 'PAGO'
GROUP BY
    p.id,
    p.nome;
GO

IF OBJECT_ID('vw_resumo_financeiro', 'V') IS NOT NULL
    DROP VIEW vw_resumo_financeiro;
GO

CREATE VIEW vw_resumo_financeiro AS
SELECT
    ISNULL((
        SELECT SUM(valor)
        FROM pagamentos
        WHERE status_pagamento = 'PAGO'
    ), 0) AS total_recebido,

    ISNULL((
        SELECT SUM(valor)
        FROM pagamentos
        WHERE status_pagamento = 'PENDENTE'
    ), 0) AS total_pendente,

    ISNULL((
        SELECT SUM(valor_comissao)
        FROM comissoes
    ), 0) AS total_comissoes,

    ISNULL((
        SELECT SUM(valor)
        FROM pagamentos
        WHERE status_pagamento = 'PAGO'
    ), 0)
    -
    ISNULL((
        SELECT SUM(valor_comissao)
        FROM comissoes
    ), 0) AS liquido_estimado_clinica;
GO

IF OBJECT_ID('vw_indicadores_operacionais', 'V') IS NOT NULL
    DROP VIEW vw_indicadores_operacionais;
GO

CREATE VIEW vw_indicadores_operacionais AS
SELECT
    (SELECT COUNT(*) FROM clientes) AS total_clientes,
    (SELECT COUNT(*) FROM clientes WHERE ativo = 1) AS clientes_ativos,
    (SELECT COUNT(*) FROM profissionais) AS total_profissionais,
    (SELECT COUNT(*) FROM profissionais WHERE ativo = 1) AS profissionais_ativos,
    (SELECT COUNT(*) FROM profissionais WHERE ativo = 1 AND disponivel = 1) AS profissionais_disponiveis,
    (SELECT COUNT(*) FROM servicos) AS total_servicos,
    (SELECT COUNT(*) FROM servicos WHERE ativo = 1) AS servicos_ativos,
    (SELECT COUNT(*) FROM agendamentos) AS total_agendamentos,
    (SELECT COUNT(*) FROM agendamentos WHERE status_agendamento = 'REALIZADO') AS agendamentos_realizados,
    (SELECT COUNT(*) FROM agendamentos WHERE status_agendamento = 'FALTOU') AS faltas,
    (SELECT COUNT(*) FROM agendamentos WHERE status_agendamento = 'CANCELADO') AS cancelamentos;
GO

IF OBJECT_ID('vw_agendamentos_aptos_comissao', 'V') IS NOT NULL
    DROP VIEW vw_agendamentos_aptos_comissao;
GO

CREATE VIEW vw_agendamentos_aptos_comissao AS
SELECT
    pg.id AS pagamento_id,
    a.id AS agendamento_id,
    c.nome AS cliente,
    p.id AS profissional_id,
    p.nome AS profissional,
    s.nome AS servico,
    pg.valor AS valor_pago,
    p.percentual_comissao,
    (pg.valor * p.percentual_comissao / 100) AS valor_comissao_calculado,
    a.status_agendamento,
    pg.status_pagamento
FROM pagamentos pg
INNER JOIN agendamentos a ON a.id = pg.agendamento_id
INNER JOIN clientes c ON c.id = a.cliente_id
INNER JOIN profissionais p ON p.id = a.profissional_id
INNER JOIN servicos s ON s.id = a.servico_id
WHERE pg.status_pagamento = 'PAGO'
AND a.status_agendamento = 'REALIZADO';
GO

IF OBJECT_ID('vw_pagamentos_sem_comissao', 'V') IS NOT NULL
    DROP VIEW vw_pagamentos_sem_comissao;
GO

CREATE VIEW vw_pagamentos_sem_comissao AS
SELECT
    pg.id AS pagamento_id,
    a.id AS agendamento_id,
    c.nome AS cliente,
    p.nome AS profissional,
    s.nome AS servico,
    pg.valor,
    pg.status_pagamento,
    a.status_agendamento
FROM pagamentos pg
INNER JOIN agendamentos a ON a.id = pg.agendamento_id
INNER JOIN clientes c ON c.id = a.cliente_id
INNER JOIN profissionais p ON p.id = a.profissional_id
INNER JOIN servicos s ON s.id = a.servico_id
WHERE pg.status_pagamento = 'PAGO'
AND a.status_agendamento = 'REALIZADO'
AND NOT EXISTS (
    SELECT 1
    FROM comissoes cm
    WHERE cm.pagamento_id = pg.id
);
GO

IF OBJECT_ID('vw_pagamentos_sem_direito_comissao', 'V') IS NOT NULL
    DROP VIEW vw_pagamentos_sem_direito_comissao;
GO

CREATE VIEW vw_pagamentos_sem_direito_comissao AS
SELECT
    pg.id AS pagamento_id,
    a.id AS agendamento_id,
    c.nome AS cliente,
    p.nome AS profissional,
    s.nome AS servico,
    pg.valor,
    pg.status_pagamento,
    a.status_agendamento
FROM pagamentos pg
INNER JOIN agendamentos a ON a.id = pg.agendamento_id
INNER JOIN clientes c ON c.id = a.cliente_id
INNER JOIN profissionais p ON p.id = a.profissional_id
INNER JOIN servicos s ON s.id = a.servico_id
WHERE pg.status_pagamento = 'PAGO'
AND a.status_agendamento <> 'REALIZADO';
GO

-- =====================================
-- TESTES DAS VIEWS
-- =====================================

SELECT *
FROM vw_clientes_ativos;

SELECT *
FROM vw_profissionais_disponiveis;

SELECT *
FROM vw_servicos_ativos;

SELECT *
FROM vw_agendamentos_completos;

SELECT *
FROM vw_pagamentos_completos;

SELECT *
FROM vw_comissoes_completas;

SELECT *
FROM vw_resumo_financeiro;

SELECT *
FROM vw_indicadores_operacionais;

SELECT *
FROM vw_faturamento_por_profissional
ORDER BY total_recebido DESC;

SELECT *
FROM vw_comissoes_por_profissional
ORDER BY total_comissao DESC;

SELECT *
FROM vw_liquido_por_profissional
ORDER BY liquido_clinica DESC;