USE emfisio_db;
GO

-- =====================================
-- AULA 18 - TRIGGERS
-- =====================================

IF OBJECT_ID('auditoria_sistema', 'U') IS NOT NULL
    DROP TABLE auditoria_sistema;
GO

CREATE TABLE auditoria_sistema (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tabela_origem VARCHAR(100) NOT NULL,
    registro_id INT NOT NULL,
    acao VARCHAR(50) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    data_evento DATETIME NOT NULL DEFAULT GETDATE()
);
GO

IF OBJECT_ID('trg_auditar_cliente_inativado', 'TR') IS NOT NULL
    DROP TRIGGER trg_auditar_cliente_inativado;
GO

CREATE TRIGGER trg_auditar_cliente_inativado
ON clientes
AFTER UPDATE
AS
BEGIN
    INSERT INTO auditoria_sistema (
        tabela_origem,
        registro_id,
        acao,
        descricao
    )
    SELECT
        'clientes',
        i.id,
        'CLIENTE_INATIVADO',
        'Cliente ' + i.nome + ' foi inativado.'
    FROM inserted i
    INNER JOIN deleted d ON d.id = i.id
    WHERE d.ativo = 1
    AND i.ativo = 0;
END;
GO

IF OBJECT_ID('trg_auditar_profissional_inativado', 'TR') IS NOT NULL
    DROP TRIGGER trg_auditar_profissional_inativado;
GO

CREATE TRIGGER trg_auditar_profissional_inativado
ON profissionais
AFTER UPDATE
AS
BEGIN
    INSERT INTO auditoria_sistema (
        tabela_origem,
        registro_id,
        acao,
        descricao
    )
    SELECT
        'profissionais',
        i.id,
        'PROFISSIONAL_INATIVADO',
        'Profissional ' + i.nome + ' foi inativado.'
    FROM inserted i
    INNER JOIN deleted d ON d.id = i.id
    WHERE d.ativo = 1
    AND i.ativo = 0;
END;
GO

IF OBJECT_ID('trg_auditar_servico_inativado', 'TR') IS NOT NULL
    DROP TRIGGER trg_auditar_servico_inativado;
GO

CREATE TRIGGER trg_auditar_servico_inativado
ON servicos
AFTER UPDATE
AS
BEGIN
    INSERT INTO auditoria_sistema (
        tabela_origem,
        registro_id,
        acao,
        descricao
    )
    SELECT
        'servicos',
        i.id,
        'SERVICO_INATIVADO',
        'Serviço ' + i.nome + ' foi inativado.'
    FROM inserted i
    INNER JOIN deleted d ON d.id = i.id
    WHERE d.ativo = 1
    AND i.ativo = 0;
END;
GO

IF OBJECT_ID('trg_auditar_pagamento_registrado', 'TR') IS NOT NULL
    DROP TRIGGER trg_auditar_pagamento_registrado;
GO

CREATE TRIGGER trg_auditar_pagamento_registrado
ON pagamentos
AFTER INSERT
AS
BEGIN
    INSERT INTO auditoria_sistema (
        tabela_origem,
        registro_id,
        acao,
        descricao
    )
    SELECT
        'pagamentos',
        i.id,
        'PAGAMENTO_REGISTRADO',
        'Pagamento registrado no valor de R$ ' + CAST(i.valor AS VARCHAR(20)) + ' com status ' + i.status_pagamento + '.'
    FROM inserted i;
END;
GO

IF OBJECT_ID('trg_gerar_comissao_apos_pagamento', 'TR') IS NOT NULL
    DROP TRIGGER trg_gerar_comissao_apos_pagamento;
GO

CREATE TRIGGER trg_gerar_comissao_apos_pagamento
ON pagamentos
AFTER INSERT
AS
BEGIN
    INSERT INTO comissoes (
        agendamento_id,
        profissional_id,
        pagamento_id,
        percentual_comissao,
        valor_comissao
    )
    SELECT
        a.id AS agendamento_id,
        p.id AS profissional_id,
        i.id AS pagamento_id,
        p.percentual_comissao,
        (i.valor * p.percentual_comissao / 100) AS valor_comissao
    FROM inserted i
    INNER JOIN agendamentos a ON a.id = i.agendamento_id
    INNER JOIN profissionais p ON p.id = a.profissional_id
    WHERE i.status_pagamento = 'PAGO'
    AND a.status_agendamento = 'REALIZADO'
    AND NOT EXISTS (
        SELECT 1
        FROM comissoes cm
        WHERE cm.pagamento_id = i.id
    );
END;
GO

IF OBJECT_ID('trg_auditar_comissao_gerada', 'TR') IS NOT NULL
    DROP TRIGGER trg_auditar_comissao_gerada;
GO

CREATE TRIGGER trg_auditar_comissao_gerada
ON comissoes
AFTER INSERT
AS
BEGIN
    INSERT INTO auditoria_sistema (
        tabela_origem,
        registro_id,
        acao,
        descricao
    )
    SELECT
        'comissoes',
        i.id,
        'COMISSAO_GERADA',
        'Comissão gerada no valor de R$ ' + CAST(i.valor_comissao AS VARCHAR(20)) + '.'
    FROM inserted i;
END;
GO

IF OBJECT_ID('trg_delete_cliente_vira_inativacao', 'TR') IS NOT NULL
    DROP TRIGGER trg_delete_cliente_vira_inativacao;
GO

CREATE TRIGGER trg_delete_cliente_vira_inativacao
ON clientes
INSTEAD OF DELETE
AS
BEGIN
    UPDATE c
    SET ativo = 0
    FROM clientes c
    INNER JOIN deleted d ON d.id = c.id;

    SELECT 'Cliente inativado logicamente. Exclusão física não realizada.' AS mensagem;
END;
GO

-- =====================================
-- TESTES DAS TRIGGERS
-- =====================================

-- Teste de auditoria de cliente inativado
UPDATE clientes
SET ativo = 0
WHERE id = 1;

SELECT *
FROM auditoria_sistema
ORDER BY data_evento DESC;

-- Reativar cliente para outros testes
UPDATE clientes
SET ativo = 1
WHERE id = 1;

-- Teste de pagamento com comissão automática
UPDATE agendamentos
SET status_agendamento = 'REALIZADO'
WHERE id = 1;

INSERT INTO pagamentos (
    agendamento_id,
    valor,
    forma_pagamento,
    status_pagamento,
    data_pagamento
)
VALUES (1, 180.00, 'PIX', 'PAGO', GETDATE());

SELECT *
FROM pagamentos
ORDER BY criado_em DESC;

SELECT *
FROM comissoes
ORDER BY data_geracao DESC;

SELECT *
FROM auditoria_sistema
ORDER BY data_evento DESC;

-- Teste de delete lógico
DELETE FROM clientes
WHERE id = 1;

SELECT *
FROM clientes
WHERE id = 1;

SELECT *
FROM auditoria_sistema
ORDER BY data_evento DESC;

-- Ver triggers existentes
SELECT
    name,
    create_date,
    modify_date
FROM sys.triggers;