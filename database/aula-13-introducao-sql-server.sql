CREATE DATABASE emfisio_db;
GO

USE emfisio_db;
GO

CREATE TABLE clientes (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    ativo BIT NOT NULL DEFAULT 1,
    criado_em DATETIME NOT NULL DEFAULT GETDATE()
);

CREATE TABLE profissionais (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especialidade VARCHAR(100) NOT NULL,
    percentual_comissao DECIMAL(5,2) NOT NULL,
    disponivel BIT NOT NULL DEFAULT 1,
    ativo BIT NOT NULL DEFAULT 1,
    criado_em DATETIME NOT NULL DEFAULT GETDATE()
);

CREATE TABLE servicos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    duracao_minutos INT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    ativo BIT NOT NULL DEFAULT 1,
    criado_em DATETIME NOT NULL DEFAULT GETDATE()
);

CREATE TABLE agendamentos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    cliente_id INT NOT NULL,
    profissional_id INT NOT NULL,
    servico_id INT NOT NULL,
    status_agendamento VARCHAR(20) NOT NULL,
    status_pagamento VARCHAR(20) NOT NULL,
    horas_antes_atendimento INT NOT NULL,
    criado_em DATETIME NOT NULL DEFAULT GETDATE(),

    CONSTRAINT fk_agendamentos_clientes
        FOREIGN KEY (cliente_id) REFERENCES clientes(id),

    CONSTRAINT fk_agendamentos_profissionais
        FOREIGN KEY (profissional_id) REFERENCES profissionais(id),

    CONSTRAINT fk_agendamentos_servicos
        FOREIGN KEY (servico_id) REFERENCES servicos(id)
);

INSERT INTO clientes (nome, idade, telefone, ativo)
VALUES ('Ana Paula', 42, '(27) 99999-0000', 1);

INSERT INTO clientes (nome, idade, telefone, ativo)
VALUES ('João Pereira', 38, '(27) 98888-1111', 1);

INSERT INTO clientes (nome, idade, telefone, ativo)
VALUES ('Maria Souza', 51, '(27) 97777-2222', 0);

INSERT INTO profissionais (nome, especialidade, percentual_comissao, disponivel, ativo)
VALUES ('Elisangela Morozini', 'Fisioterapia Pélvica', 40.00, 1, 1);

INSERT INTO profissionais (nome, especialidade, percentual_comissao, disponivel, ativo)
VALUES ('Mariana Silva', 'Pilates', 35.00, 1, 1);

INSERT INTO profissionais (nome, especialidade, percentual_comissao, disponivel, ativo)
VALUES ('Rafael Costa', 'Osteopatia', 45.00, 0, 1);

INSERT INTO servicos (nome, duracao_minutos, valor, ativo)
VALUES ('Sessão de Fisioterapia', 60, 150.00, 1);

INSERT INTO servicos (nome, duracao_minutos, valor, ativo)
VALUES ('Pilates', 60, 120.00, 1);

INSERT INTO servicos (nome, duracao_minutos, valor, ativo)
VALUES ('Osteopatia', 50, 180.00, 0);

INSERT INTO agendamentos (
    cliente_id,
    profissional_id,
    servico_id,
    status_agendamento,
    status_pagamento,
    horas_antes_atendimento
)
VALUES (1, 1, 1, 'REALIZADO', 'PAGO', 30);

INSERT INTO agendamentos (
    cliente_id,
    profissional_id,
    servico_id,
    status_agendamento,
    status_pagamento,
    horas_antes_atendimento
)
VALUES (2, 2, 2, 'FALTOU', 'PAGO', 10);

INSERT INTO agendamentos (
    cliente_id,
    profissional_id,
    servico_id,
    status_agendamento,
    status_pagamento,
    horas_antes_atendimento
)
VALUES (3, 3, 3, 'AGENDADO', 'PENDENTE', 48);

SELECT *
FROM clientes;

SELECT *
FROM clientes
WHERE ativo = 1;

SELECT *
FROM profissionais
WHERE ativo = 1
AND disponivel = 1;

SELECT *
FROM servicos
WHERE ativo = 1;

SELECT *
FROM clientes
ORDER BY nome ASC;

SELECT *
FROM clientes
WHERE nome LIKE '%Ana%';

SELECT
    a.id,
    c.nome AS cliente,
    p.nome AS profissional,
    s.nome AS servico,
    s.valor,
    a.status_agendamento,
    a.status_pagamento,
    a.horas_antes_atendimento
FROM agendamentos a
INNER JOIN clientes c ON c.id = a.cliente_id
INNER JOIN profissionais p ON p.id = a.profissional_id
INNER JOIN servicos s ON s.id = a.servico_id;

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

SELECT
    SUM(s.valor) AS faturamento_total
FROM agendamentos a
INNER JOIN servicos s ON s.id = a.servico_id
WHERE a.status_pagamento = 'PAGO';

SELECT COUNT(*) AS total_clientes
FROM clientes;

SELECT COUNT(*) AS total_clientes_ativos
FROM clientes
WHERE ativo = 1;

SELECT
    status_agendamento,
    COUNT(*) AS total
FROM agendamentos
GROUP BY status_agendamento;