USE emfisio_db;
GO

-- =====================================
-- AULA 15 - PAGAMENTOS E COMISSÕES
-- =====================================

CREATE TABLE pagamentos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    agendamento_id INT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    forma_pagamento VARCHAR(30) NOT NULL,
    status_pagamento VARCHAR(20) NOT NULL,
    data_pagamento DATETIME NULL,
    criado_em DATETIME NOT NULL DEFAULT GETDATE(),

    CONSTRAINT fk_pagamentos_agendamentos
        FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id)
);

CREATE TABLE comissoes (
    id INT IDENTITY(1,1) PRIMARY KEY,
    agendamento_id INT NOT NULL,
    profissional_id INT NOT NULL,
    pagamento_id INT NOT NULL,
    percentual_comissao DECIMAL(5,2) NOT NULL,
    valor_comissao DECIMAL(10,2) NOT NULL,
    data_geracao DATETIME NOT NULL DEFAULT GETDATE(),

    CONSTRAINT fk_comissoes_agendamentos
        FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id),

    CONSTRAINT fk_comissoes_profissionais
        FOREIGN KEY (profissional_id) REFERENCES profissionais(id),

    CONSTRAINT fk_comissoes_pagamentos
        FOREIGN KEY (pagamento_id) REFERENCES pagamentos(id)
);

ALTER TABLE pagamentos
ADD CONSTRAINT chk_pagamentos_status
CHECK (status_pagamento IN ('PENDENTE', 'PAGO', 'CANCELADO', 'ESTORNADO'));

ALTER TABLE pagamentos
ADD CONSTRAINT chk_pagamentos_forma
CHECK (forma_pagamento IN ('PIX', 'DINHEIRO', 'CARTAO', 'TRANSFERENCIA'));

ALTER TABLE pagamentos
ADD CONSTRAINT chk_pagamentos_valor
CHECK (valor >= 0);

ALTER TABLE comissoes
ADD CONSTRAINT chk_comissoes_valor
CHECK (valor_comissao >= 0);

ALTER TABLE comissoes
ADD CONSTRAINT chk_comissoes_percentual
CHECK (percentual_comissao >= 0 AND percentual_comissao <= 100);

INSERT INTO pagamentos (
    agendamento_id,
    valor,
    forma_pagamento,
    status_pagamento,
    data_pagamento
)
VALUES (1, 180.00, 'PIX', 'PAGO', GETDATE());

INSERT INTO pagamentos (
    agendamento_id,
    valor,
    forma_pagamento,
    status_pagamento,
    data_pagamento
)
VALUES (2, 120.00, 'DINHEIRO', 'PAGO', GETDATE());

INSERT INTO pagamentos (
    agendamento_id,
    valor,
    forma_pagamento,
    status_pagamento,
    data_pagamento
)
VALUES (3, 180.00, 'CARTAO', 'PAGO', GETDATE());

INSERT INTO pagamentos (
    agendamento_id,
    valor,
    forma_pagamento,
    status_pagamento,
    data_pagamento
)
VALUES (1, 180.00, 'PIX', 'PENDENTE', NULL);

SELECT *
FROM pagamentos;

SELECT *
FROM pagamentos
WHERE status_pagamento = 'PAGO';

SELECT *
FROM pagamentos
WHERE status_pagamento = 'PENDENTE';

SELECT
    pg.id AS pagamento_id,
    a.id AS agendamento_id,
    c.nome AS cliente,
    s.nome AS servico,
    pg.valor,
    pg.forma_pagamento,
    pg.status_pagamento,
    pg.data_pagamento
FROM pagamentos pg
INNER JOIN agendamentos a ON a.id = pg.agendamento_id
INNER JOIN clientes c ON c.id = a.cliente_id
INNER JOIN servicos s ON s.id = a.servico_id;

SELECT
    a.id AS agendamento_id,
    p.id AS profissional_id,
    pg.id AS pagamento_id,
    p.percentual_comissao,
    pg.valor,
    (pg.valor * p.percentual_comissao / 100) AS valor_comissao
FROM pagamentos pg
INNER JOIN agendamentos a ON a.id = pg.agendamento_id
INNER JOIN profissionais p ON p.id = a.profissional_id
WHERE pg.status_pagamento = 'PAGO'
AND a.status_agendamento = 'REALIZADO';

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
    FROM comissoes c
    WHERE c.pagamento_id = pg.id
);

SELECT *
FROM comissoes;

SELECT
    cm.id AS comissao_id,
    c.nome AS cliente,
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

SELECT
    SUM(valor) AS total_recebido
FROM pagamentos
WHERE status_pagamento = 'PAGO';

SELECT
    ISNULL(SUM(valor), 0) AS total_pendente
FROM pagamentos
WHERE status_pagamento = 'PENDENTE';

SELECT
    ISNULL(SUM(valor_comissao), 0) AS total_comissoes
FROM comissoes;

SELECT
    p.nome AS profissional,
    SUM(cm.valor_comissao) AS total_comissao
FROM comissoes cm
INNER JOIN profissionais p ON p.id = cm.profissional_id
GROUP BY p.nome
ORDER BY total_comissao DESC;

SELECT
    forma_pagamento,
    SUM(valor) AS total
FROM pagamentos
WHERE status_pagamento = 'PAGO'
GROUP BY forma_pagamento
ORDER BY total DESC;

SELECT
    status_pagamento,
    COUNT(*) AS quantidade,
    ISNULL(SUM(valor), 0) AS total
FROM pagamentos
GROUP BY status_pagamento;

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
    ), 0) AS total_comissoes;

SELECT
    pg.id AS pagamento_id,
    a.id AS agendamento_id,
    c.nome AS cliente,
    p.nome AS profissional,
    pg.valor,
    pg.status_pagamento,
    a.status_agendamento
FROM pagamentos pg
INNER JOIN agendamentos a ON a.id = pg.agendamento_id
INNER JOIN clientes c ON c.id = a.cliente_id
INNER JOIN profissionais p ON p.id = a.profissional_id
WHERE pg.status_pagamento = 'PAGO'
AND a.status_agendamento = 'REALIZADO'
AND NOT EXISTS (
    SELECT 1
    FROM comissoes cm
    WHERE cm.pagamento_id = pg.id
);

SELECT
    pg.id AS pagamento_id,
    a.id AS agendamento_id,
    c.nome AS cliente,
    p.nome AS profissional,
    pg.valor,
    a.status_agendamento,
    pg.status_pagamento
FROM pagamentos pg
INNER JOIN agendamentos a ON a.id = pg.agendamento_id
INNER JOIN clientes c ON c.id = a.cliente_id
INNER JOIN profissionais p ON p.id = a.profissional_id
WHERE pg.status_pagamento = 'PAGO'
AND a.status_agendamento <> 'REALIZADO';

CREATE INDEX idx_pagamentos_status
ON pagamentos(status_pagamento);

CREATE INDEX idx_pagamentos_agendamento
ON pagamentos(agendamento_id);

CREATE INDEX idx_comissoes_profissional
ON comissoes(profissional_id);