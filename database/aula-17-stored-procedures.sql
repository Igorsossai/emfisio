USE emfisio_db;
GO

-- =====================================
-- AULA 17 - STORED PROCEDURES
-- =====================================

IF OBJECT_ID('sp_listar_clientes', 'P') IS NOT NULL
    DROP PROCEDURE sp_listar_clientes;
GO

CREATE PROCEDURE sp_listar_clientes
AS
BEGIN
    SELECT
        id,
        nome,
        idade,
        telefone,
        ativo,
        criado_em
    FROM clientes
    ORDER BY nome;
END;
GO

IF OBJECT_ID('sp_buscar_cliente_por_nome', 'P') IS NOT NULL
    DROP PROCEDURE sp_buscar_cliente_por_nome;
GO

CREATE PROCEDURE sp_buscar_cliente_por_nome
    @nomeBusca VARCHAR(100)
AS
BEGIN
    SELECT
        id,
        nome,
        idade,
        telefone,
        ativo,
        criado_em
    FROM clientes
    WHERE nome LIKE '%' + @nomeBusca + '%'
    ORDER BY nome;
END;
GO

IF OBJECT_ID('sp_cadastrar_cliente', 'P') IS NOT NULL
    DROP PROCEDURE sp_cadastrar_cliente;
GO

CREATE PROCEDURE sp_cadastrar_cliente
    @nome VARCHAR(100),
    @idade INT,
    @telefone VARCHAR(20)
AS
BEGIN
    INSERT INTO clientes (
        nome,
        idade,
        telefone,
        ativo
    )
    VALUES (
        @nome,
        @idade,
        @telefone,
        1
    );

    SELECT
        SCOPE_IDENTITY() AS novo_cliente_id,
        'Cliente cadastrado com sucesso.' AS mensagem;
END;
GO

IF OBJECT_ID('sp_editar_cliente', 'P') IS NOT NULL
    DROP PROCEDURE sp_editar_cliente;
GO

CREATE PROCEDURE sp_editar_cliente
    @cliente_id INT,
    @nome VARCHAR(100),
    @idade INT,
    @telefone VARCHAR(20)
AS
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM clientes
        WHERE id = @cliente_id
    )
    BEGIN
        SELECT 'Cliente não encontrado.' AS mensagem;
        RETURN;
    END;

    UPDATE clientes
    SET
        nome = @nome,
        idade = @idade,
        telefone = @telefone
    WHERE id = @cliente_id;

    SELECT 'Cliente atualizado com sucesso.' AS mensagem;
END;
GO

IF OBJECT_ID('sp_inativar_cliente', 'P') IS NOT NULL
    DROP PROCEDURE sp_inativar_cliente;
GO

CREATE PROCEDURE sp_inativar_cliente
    @cliente_id INT
AS
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM clientes
        WHERE id = @cliente_id
    )
    BEGIN
        SELECT 'Cliente não encontrado.' AS mensagem;
        RETURN;
    END;

    UPDATE clientes
    SET ativo = 0
    WHERE id = @cliente_id;

    SELECT 'Cliente inativado com sucesso.' AS mensagem;
END;
GO

IF OBJECT_ID('sp_reativar_cliente', 'P') IS NOT NULL
    DROP PROCEDURE sp_reativar_cliente;
GO

CREATE PROCEDURE sp_reativar_cliente
    @cliente_id INT
AS
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM clientes
        WHERE id = @cliente_id
    )
    BEGIN
        SELECT 'Cliente não encontrado.' AS mensagem;
        RETURN;
    END;

    UPDATE clientes
    SET ativo = 1
    WHERE id = @cliente_id;

    SELECT 'Cliente reativado com sucesso.' AS mensagem;
END;
GO

IF OBJECT_ID('sp_cadastrar_servico', 'P') IS NOT NULL
    DROP PROCEDURE sp_cadastrar_servico;
GO

CREATE PROCEDURE sp_cadastrar_servico
    @nome VARCHAR(100),
    @duracao_minutos INT,
    @valor DECIMAL(10,2)
AS
BEGIN
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

    SELECT
        SCOPE_IDENTITY() AS novo_servico_id,
        'Serviço cadastrado com sucesso.' AS mensagem;
END;
GO

IF OBJECT_ID('sp_inativar_servico', 'P') IS NOT NULL
    DROP PROCEDURE sp_inativar_servico;
GO

CREATE PROCEDURE sp_inativar_servico
    @servico_id INT
AS
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM servicos
        WHERE id = @servico_id
    )
    BEGIN
        SELECT 'Serviço não encontrado.' AS mensagem;
        RETURN;
    END;

    UPDATE servicos
    SET ativo = 0
    WHERE id = @servico_id;

    SELECT 'Serviço inativado com sucesso.' AS mensagem;
END;
GO

IF OBJECT_ID('sp_cadastrar_profissional', 'P') IS NOT NULL
    DROP PROCEDURE sp_cadastrar_profissional;
GO

CREATE PROCEDURE sp_cadastrar_profissional
    @nome VARCHAR(100),
    @especialidade VARCHAR(100),
    @percentual_comissao DECIMAL(5,2),
    @disponivel BIT
AS
BEGIN
    INSERT INTO profissionais (
        nome,
        especialidade,
        percentual_comissao,
        disponivel,
        ativo
    )
    VALUES (
        @nome,
        @especialidade,
        @percentual_comissao,
        @disponivel,
        1
    );

    SELECT
        SCOPE_IDENTITY() AS novo_profissional_id,
        'Profissional cadastrado com sucesso.' AS mensagem;
END;
GO

IF OBJECT_ID('sp_inativar_profissional', 'P') IS NOT NULL
    DROP PROCEDURE sp_inativar_profissional;
GO

CREATE PROCEDURE sp_inativar_profissional
    @profissional_id INT
AS
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM profissionais
        WHERE id = @profissional_id
    )
    BEGIN
        SELECT 'Profissional não encontrado.' AS mensagem;
        RETURN;
    END;

    UPDATE profissionais
    SET
        ativo = 0,
        disponivel = 0
    WHERE id = @profissional_id;

    SELECT 'Profissional inativado com sucesso.' AS mensagem;
END;
GO

IF OBJECT_ID('sp_criar_agendamento', 'P') IS NOT NULL
    DROP PROCEDURE sp_criar_agendamento;
GO

CREATE PROCEDURE sp_criar_agendamento
    @cliente_id INT,
    @profissional_id INT,
    @servico_id INT,
    @status_agendamento VARCHAR(20),
    @horas_antes_atendimento INT
AS
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM clientes
        WHERE id = @cliente_id
        AND ativo = 1
    )
    BEGIN
        SELECT 'Cliente inexistente ou inativo.' AS mensagem;
        RETURN;
    END;

    IF NOT EXISTS (
        SELECT 1
        FROM profissionais
        WHERE id = @profissional_id
        AND ativo = 1
        AND disponivel = 1
    )
    BEGIN
        SELECT 'Profissional inexistente, inativo ou indisponível.' AS mensagem;
        RETURN;
    END;

    IF NOT EXISTS (
        SELECT 1
        FROM servicos
        WHERE id = @servico_id
        AND ativo = 1
    )
    BEGIN
        SELECT 'Serviço inexistente ou inativo.' AS mensagem;
        RETURN;
    END;

    INSERT INTO agendamentos (
        cliente_id,
        profissional_id,
        servico_id,
        status_agendamento,
        status_pagamento,
        horas_antes_atendimento
    )
    VALUES (
        @cliente_id,
        @profissional_id,
        @servico_id,
        @status_agendamento,
        'PENDENTE',
        @horas_antes_atendimento
    );

    SELECT
        SCOPE_IDENTITY() AS novo_agendamento_id,
        'Agendamento criado com sucesso.' AS mensagem;
END;
GO

IF OBJECT_ID('sp_registrar_pagamento', 'P') IS NOT NULL
    DROP PROCEDURE sp_registrar_pagamento;
GO

CREATE PROCEDURE sp_registrar_pagamento
    @agendamento_id INT,
    @valor DECIMAL(10,2),
    @forma_pagamento VARCHAR(30),
    @status_pagamento VARCHAR(20)
AS
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM agendamentos
        WHERE id = @agendamento_id
    )
    BEGIN
        SELECT 'Agendamento não encontrado.' AS mensagem;
        RETURN;
    END;

    IF @valor < 0
    BEGIN
        SELECT 'Valor do pagamento não pode ser negativo.' AS mensagem;
        RETURN;
    END;

    IF @forma_pagamento NOT IN ('PIX', 'DINHEIRO', 'CARTAO', 'TRANSFERENCIA')
    BEGIN
        SELECT 'Forma de pagamento inválida.' AS mensagem;
        RETURN;
    END;

    IF @status_pagamento NOT IN ('PENDENTE', 'PAGO', 'CANCELADO', 'ESTORNADO')
    BEGIN
        SELECT 'Status de pagamento inválido.' AS mensagem;
        RETURN;
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

    SELECT
        SCOPE_IDENTITY() AS novo_pagamento_id,
        'Pagamento registrado com sucesso.' AS mensagem;
END;
GO

IF OBJECT_ID('sp_gerar_comissao', 'P') IS NOT NULL
    DROP PROCEDURE sp_gerar_comissao;
GO

CREATE PROCEDURE sp_gerar_comissao
    @pagamento_id INT
AS
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pagamentos
        WHERE id = @pagamento_id
    )
    BEGIN
        SELECT 'Pagamento não encontrado.' AS mensagem;
        RETURN;
    END;

    IF EXISTS (
        SELECT 1
        FROM comissoes
        WHERE pagamento_id = @pagamento_id
    )
    BEGIN
        SELECT 'Comissão já gerada para este pagamento.' AS mensagem;
        RETURN;
    END;

    IF NOT EXISTS (
        SELECT 1
        FROM pagamentos pg
        INNER JOIN agendamentos a ON a.id = pg.agendamento_id
        WHERE pg.id = @pagamento_id
        AND pg.status_pagamento = 'PAGO'
        AND a.status_agendamento = 'REALIZADO'
    )
    BEGIN
        SELECT 'Comissão não pode ser gerada. Pagamento precisa estar PAGO e atendimento REALIZADO.' AS mensagem;
        RETURN;
    END;

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
    WHERE pg.id = @pagamento_id;

    SELECT 'Comissão gerada com sucesso.' AS mensagem;
END;
GO

IF OBJECT_ID('sp_gerar_comissoes_pendentes', 'P') IS NOT NULL
    DROP PROCEDURE sp_gerar_comissoes_pendentes;
GO

CREATE PROCEDURE sp_gerar_comissoes_pendentes
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
        pg.id AS pagamento_id,
        p.percentual_comissao,
        (pg.valor * p.percentual_comissao / 100) AS valor_comissao
    FROM pagamentos pg
    INNER JOIN agendamentos a ON a.id = pg.agendamento_id
    INNER JOIN profissionais p ON p.id = a.profissional_id
    WHERE pg.status_pagamento = 'PAGO'
    AND a.status_agendamento = 'REALIZADO'
    AND NOT EXISTS (
        SELECT 1
        FROM comissoes cm
        WHERE cm.pagamento_id = pg.id
    );

    SELECT 'Comissões pendentes geradas com sucesso.' AS mensagem;
END;
GO

IF OBJECT_ID('sp_resumo_financeiro', 'P') IS NOT NULL
    DROP PROCEDURE sp_resumo_financeiro;
GO

CREATE PROCEDURE sp_resumo_financeiro
AS
BEGIN
    SELECT *
    FROM vw_resumo_financeiro;
END;
GO

IF OBJECT_ID('sp_pagamentos_pendentes', 'P') IS NOT NULL
    DROP PROCEDURE sp_pagamentos_pendentes;
GO

CREATE PROCEDURE sp_pagamentos_pendentes
AS
BEGIN
    SELECT *
    FROM vw_pagamentos_pendentes
    ORDER BY cliente;
END;
GO

IF OBJECT_ID('sp_comissoes_por_profissional', 'P') IS NOT NULL
    DROP PROCEDURE sp_comissoes_por_profissional;
GO

CREATE PROCEDURE sp_comissoes_por_profissional
AS
BEGIN
    SELECT *
    FROM vw_comissoes_por_profissional
    ORDER BY total_comissao DESC;
END;
GO

IF OBJECT_ID('sp_atualizar_status_agendamento', 'P') IS NOT NULL
    DROP PROCEDURE sp_atualizar_status_agendamento;
GO

CREATE PROCEDURE sp_atualizar_status_agendamento
    @agendamento_id INT,
    @status_agendamento VARCHAR(20)
AS
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM agendamentos
        WHERE id = @agendamento_id
    )
    BEGIN
        SELECT 'Agendamento não encontrado.' AS mensagem;
        RETURN;
    END;

    IF @status_agendamento NOT IN ('AGENDADO', 'CONFIRMADO', 'REALIZADO', 'CANCELADO', 'FALTOU', 'REAGENDADO')
    BEGIN
        SELECT 'Status de agendamento inválido.' AS mensagem;
        RETURN;
    END;

    UPDATE agendamentos
    SET status_agendamento = @status_agendamento
    WHERE id = @agendamento_id;

    SELECT 'Status do agendamento atualizado com sucesso.' AS mensagem;
END;
GO

-- =====================================
-- TESTES DAS PROCEDURES
-- =====================================

EXEC sp_listar_clientes;

EXEC sp_buscar_cliente_por_nome @nomeBusca = 'Ana';

EXEC sp_cadastrar_cliente
    @nome = 'Fernanda Lima',
    @idade = 29,
    @telefone = '(27) 95555-4444';

EXEC sp_cadastrar_servico
    @nome = 'Pilates Individual',
    @duracao_minutos = 60,
    @valor = 140.00;

EXEC sp_cadastrar_profissional
    @nome = 'Camila Rocha',
    @especialidade = 'Pilates',
    @percentual_comissao = 35.00,
    @disponivel = 1;

EXEC sp_resumo_financeiro;

EXEC sp_pagamentos_pendentes;

EXEC sp_comissoes_por_profissional;