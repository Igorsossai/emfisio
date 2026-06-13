USE emfisio_db;
GO

-- =====================================
-- AULA 19 - TRANSAÇÕES E TRATAMENTO DE ERROS
-- =====================================

-- TESTE DIDÁTICO COM ROLLBACK
BEGIN TRANSACTION;

UPDATE clientes
SET telefone = '(27) 00000-0000'
WHERE id = 1;

SELECT *
FROM clientes
WHERE id = 1;

ROLLBACK;

SELECT *
FROM clientes
WHERE id = 1;
GO

-- TESTE DIDÁTICO COM COMMIT
BEGIN TRANSACTION;

UPDATE clientes
SET telefone = '(27) 91111-2222'
WHERE id = 1;

COMMIT;

SELECT *
FROM clientes
WHERE id = 1;
GO

IF OBJECT_ID('log_erros_sistema', 'U') IS NOT NULL
    DROP TABLE log_erros_sistema;
GO

CREATE TABLE log_erros_sistema (
    id INT IDENTITY(1,1) PRIMARY KEY,
    procedure_origem VARCHAR(100) NULL,
    numero_erro INT NULL,
    mensagem_erro VARCHAR(1000) NOT NULL,
    linha_erro INT NULL,
    data_erro DATETIME NOT NULL DEFAULT GETDATE()
);
GO

IF OBJECT_ID('sp_registrar_pagamento_seguro', 'P') IS NOT NULL
    DROP PROCEDURE sp_registrar_pagamento_seguro;
GO

CREATE PROCEDURE sp_registrar_pagamento_seguro
    @agendamento_id INT,
    @valor DECIMAL(10,2),
    @forma_pagamento VARCHAR(30),
    @status_pagamento VARCHAR(20)
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        IF NOT EXISTS (
            SELECT 1
            FROM agendamentos
            WHERE id = @agendamento_id
        )
        BEGIN
            RAISERROR('Agendamento não encontrado.', 16, 1);
        END;

        IF @valor < 0
        BEGIN
            RAISERROR('Valor do pagamento não pode ser negativo.', 16, 1);
        END;

        IF @forma_pagamento NOT IN ('PIX', 'DINHEIRO', 'CARTAO', 'TRANSFERENCIA')
        BEGIN
            RAISERROR('Forma de pagamento inválida.', 16, 1);
        END;

        IF @status_pagamento NOT IN ('PENDENTE', 'PAGO', 'CANCELADO', 'ESTORNADO')
        BEGIN
            RAISERROR('Status de pagamento inválido.', 16, 1);
        END;

        INSERT INTO pagamentos (
            agendamento_id,
            valor,
            forma_pagamento,
            status_pagamento,
            data_pagamento
        )
        VALUES (
            @agendamento_id,
            @valor,
            @forma_pagamento,
            @status_pagamento,
            CASE
                WHEN @status_pagamento = 'PAGO' THEN GETDATE()
                ELSE NULL
            END
        );

        UPDATE agendamentos
        SET status_pagamento = @status_pagamento
        WHERE id = @agendamento_id;

        COMMIT;

        SELECT
            SCOPE_IDENTITY() AS novo_pagamento_id,
            'Pagamento registrado com segurança.' AS mensagem;
    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0
            ROLLBACK;

        SELECT
            ERROR_NUMBER() AS numero_erro,
            ERROR_MESSAGE() AS mensagem_erro,
            ERROR_LINE() AS linha_erro;
    END CATCH;
END;
GO

IF OBJECT_ID('sp_registrar_pagamento_e_comissao', 'P') IS NOT NULL
    DROP PROCEDURE sp_registrar_pagamento_e_comissao;
GO

CREATE PROCEDURE sp_registrar_pagamento_e_comissao
    @agendamento_id INT,
    @valor DECIMAL(10,2),
    @forma_pagamento VARCHAR(30),
    @status_pagamento VARCHAR(20)
AS
BEGIN
    DECLARE @novo_pagamento_id INT;

    BEGIN TRY
        BEGIN TRANSACTION;

        IF NOT EXISTS (
            SELECT 1
            FROM agendamentos
            WHERE id = @agendamento_id
        )
        BEGIN
            RAISERROR('Agendamento não encontrado.', 16, 1);
        END;

        IF @valor < 0
        BEGIN
            RAISERROR('Valor do pagamento não pode ser negativo.', 16, 1);
        END;

        IF @forma_pagamento NOT IN ('PIX', 'DINHEIRO', 'CARTAO', 'TRANSFERENCIA')
        BEGIN
            RAISERROR('Forma de pagamento inválida.', 16, 1);
        END;

        IF @status_pagamento NOT IN ('PENDENTE', 'PAGO', 'CANCELADO', 'ESTORNADO')
        BEGIN
            RAISERROR('Status de pagamento inválido.', 16, 1);
        END;

        INSERT INTO pagamentos (
            agendamento_id,
            valor,
            forma_pagamento,
            status_pagamento,
            data_pagamento
        )
        VALUES (
            @agendamento_id,
            @valor,
            @forma_pagamento,
            @status_pagamento,
            CASE
                WHEN @status_pagamento = 'PAGO' THEN GETDATE()
                ELSE NULL
            END
        );

        SET @novo_pagamento_id = SCOPE_IDENTITY();

        UPDATE agendamentos
        SET status_pagamento = @status_pagamento
        WHERE id = @agendamento_id;

        IF EXISTS (
            SELECT 1
            FROM pagamentos pg
            INNER JOIN agendamentos a ON a.id = pg.agendamento_id
            WHERE pg.id = @novo_pagamento_id
            AND pg.status_pagamento = 'PAGO'
            AND a.status_agendamento = 'REALIZADO'
        )
        BEGIN
            IF NOT EXISTS (
                SELECT 1
                FROM comissoes
                WHERE pagamento_id = @novo_pagamento_id
            )
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
                    pg.id AS pagamento_id,
                    p.percentual_comissao,
                    (pg.valor * p.percentual_comissao / 100) AS valor_comissao
                FROM pagamentos pg
                INNER JOIN agendamentos a ON a.id = pg.agendamento_id
                INNER JOIN profissionais p ON p.id = a.profissional_id
                WHERE pg.id = @novo_pagamento_id;
            END;
        END;

        COMMIT;

        SELECT
            @novo_pagamento_id AS novo_pagamento_id,
            'Pagamento registrado e comissão processada com segurança.' AS mensagem;
    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0
            ROLLBACK;

        SELECT
            ERROR_NUMBER() AS numero_erro,
            ERROR_MESSAGE() AS mensagem_erro,
            ERROR_LINE() AS linha_erro;
    END CATCH;
END;
GO

IF OBJECT_ID('sp_atualizar_status_agendamento_seguro', 'P') IS NOT NULL
    DROP PROCEDURE sp_atualizar_status_agendamento_seguro;
GO

CREATE PROCEDURE sp_atualizar_status_agendamento_seguro
    @agendamento_id INT,
    @status_agendamento VARCHAR(20)
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        IF NOT EXISTS (
            SELECT 1
            FROM agendamentos
            WHERE id = @agendamento_id
        )
        BEGIN
            RAISERROR('Agendamento não encontrado.', 16, 1);
        END;

        IF @status_agendamento NOT IN ('AGENDADO', 'CONFIRMADO', 'REALIZADO', 'CANCELADO', 'FALTOU', 'REAGENDADO')
        BEGIN
            RAISERROR('Status de agendamento inválido.', 16, 1);
        END;

        UPDATE agendamentos
        SET status_agendamento = @status_agendamento
        WHERE id = @agendamento_id;

        COMMIT;

        SELECT 'Status do agendamento atualizado com segurança.' AS mensagem;
    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0
            ROLLBACK;

        SELECT
            ERROR_NUMBER() AS numero_erro,
            ERROR_MESSAGE() AS mensagem_erro,
            ERROR_LINE() AS linha_erro;
    END CATCH;
END;
GO

IF OBJECT_ID('sp_inativar_cliente_seguro', 'P') IS NOT NULL
    DROP PROCEDURE sp_inativar_cliente_seguro;
GO

CREATE PROCEDURE sp_inativar_cliente_seguro
    @cliente_id INT
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        IF NOT EXISTS (
            SELECT 1
            FROM clientes
            WHERE id = @cliente_id
        )
        BEGIN
            RAISERROR('Cliente não encontrado.', 16, 1);
        END;

        UPDATE clientes
        SET ativo = 0
        WHERE id = @cliente_id;

        COMMIT;

        SELECT 'Cliente inativado com segurança.' AS mensagem;
    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0
            ROLLBACK;

        SELECT
            ERROR_NUMBER() AS numero_erro,
            ERROR_MESSAGE() AS mensagem_erro,
            ERROR_LINE() AS linha_erro;
    END CATCH;
END;
GO

IF OBJECT_ID('sp_estornar_pagamento_seguro', 'P') IS NOT NULL
    DROP PROCEDURE sp_estornar_pagamento_seguro;
GO

CREATE PROCEDURE sp_estornar_pagamento_seguro
    @pagamento_id INT
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        IF NOT EXISTS (
            SELECT 1
            FROM pagamentos
            WHERE id = @pagamento_id
        )
        BEGIN
            RAISERROR('Pagamento não encontrado.', 16, 1);
        END;

        IF EXISTS (
            SELECT 1
            FROM pagamentos
            WHERE id = @pagamento_id
            AND status_pagamento = 'ESTORNADO'
        )
        BEGIN
            RAISERROR('Pagamento já está estornado.', 16, 1);
        END;

        DELETE FROM comissoes
        WHERE pagamento_id = @pagamento_id;

        UPDATE pagamentos
        SET
            status_pagamento = 'ESTORNADO',
            data_pagamento = NULL
        WHERE id = @pagamento_id;

        COMMIT;

        SELECT 'Pagamento estornado com segurança e comissão vinculada removida.' AS mensagem;
    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0
            ROLLBACK;

        SELECT
            ERROR_NUMBER() AS numero_erro,
            ERROR_MESSAGE() AS mensagem_erro,
            ERROR_LINE() AS linha_erro;
    END CATCH;
END;
GO

IF OBJECT_ID('sp_cadastrar_servico_seguro', 'P') IS NOT NULL
    DROP PROCEDURE sp_cadastrar_servico_seguro;
GO

CREATE PROCEDURE sp_cadastrar_servico_seguro
    @nome VARCHAR(100),
    @duracao_minutos INT,
    @valor DECIMAL(10,2)
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        IF @nome IS NULL OR LTRIM(RTRIM(@nome)) = ''
        BEGIN
            RAISERROR('Nome do serviço é obrigatório.', 16, 1);
        END;

        IF @duracao_minutos <= 0
        BEGIN
            RAISERROR('Duração do serviço deve ser maior que zero.', 16, 1);
        END;

        IF @valor < 0
        BEGIN
            RAISERROR('Valor do serviço não pode ser negativo.', 16, 1);
        END;

        INSERT INTO servicos (
            nome,
            duracao_minutos,
            valor,
            ativo
        )
        VALUES (
            @nome,
            @duracao_minutos,
            @valor,
            1
        );

        COMMIT;

        SELECT
            SCOPE_IDENTITY() AS novo_servico_id,
            'Serviço cadastrado com segurança.' AS mensagem;
    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0
            ROLLBACK;

        INSERT INTO log_erros_sistema (
            procedure_origem,
            numero_erro,
            mensagem_erro,
            linha_erro
        )
        VALUES (
            ERROR_PROCEDURE(),
            ERROR_NUMBER(),
            ERROR_MESSAGE(),
            ERROR_LINE()
        );

        SELECT
            ERROR_NUMBER() AS numero_erro,
            ERROR_MESSAGE() AS mensagem_erro,
            ERROR_LINE() AS linha_erro;
    END CATCH;
END;
GO

-- =====================================
-- TESTES
-- =====================================

EXEC sp_atualizar_status_agendamento_seguro
    @agendamento_id = 1,
    @status_agendamento = 'REALIZADO';

EXEC sp_registrar_pagamento_seguro
    @agendamento_id = 1,
    @valor = 180.00,
    @forma_pagamento = 'PIX',
    @status_pagamento = 'PAGO';

EXEC sp_registrar_pagamento_seguro
    @agendamento_id = 1,
    @valor = -50.00,
    @forma_pagamento = 'PIX',
    @status_pagamento = 'PAGO';

EXEC sp_registrar_pagamento_e_comissao
    @agendamento_id = 1,
    @valor = 180.00,
    @forma_pagamento = 'PIX',
    @status_pagamento = 'PAGO';

EXEC sp_atualizar_status_agendamento_seguro
    @agendamento_id = 1,
    @status_agendamento = 'FINALIZADÃO';

EXEC sp_cadastrar_servico_seguro
    @nome = '',
    @duracao_minutos = 60,
    @valor = 100.00;

SELECT *
FROM log_erros_sistema
ORDER BY data_erro DESC;

SELECT *
FROM pagamentos
ORDER BY id DESC;

SELECT *
FROM comissoes
ORDER BY id DESC;