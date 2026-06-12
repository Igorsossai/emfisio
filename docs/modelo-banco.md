# Modelo Inicial do Banco de Dados — EMFISIO

## Banco de dados

Nome sugerido:

emfisio_db

## Tabelas iniciais

### usuarios

Tabela responsável por armazenar os usuários do sistema.

Campos previstos:

- id
- nome
- email
- senha
- perfil
- status
- criado_em
- atualizado_em

### clientes

Tabela responsável por armazenar os clientes da clínica.

Campos previstos:

- id
- nome
- cpf
- data_nascimento
- telefone
- email
- endereco
- observacoes
- status
- criado_em
- atualizado_em

### profissionais

Tabela responsável por armazenar os profissionais da clínica.

Campos previstos:

- id
- nome
- cpf
- crefito
- especialidade
- telefone
- email
- percentual_comissao
- status
- criado_em
- atualizado_em

### servicos

Tabela responsável por armazenar os serviços oferecidos.

Campos previstos:

- id
- nome
- descricao
- duracao_minutos
- valor
- tipo
- status
- criado_em
- atualizado_em

### agendamentos

Tabela responsável por armazenar os atendimentos agendados.

Campos previstos:

- id
- cliente_id
- profissional_id
- servico_id
- data_hora_inicio
- data_hora_fim
- status
- observacoes
- criado_em
- atualizado_em

### pagamentos

Tabela responsável por armazenar os pagamentos.

Campos previstos:

- id
- agendamento_id
- valor
- forma_pagamento
- status
- data_pagamento
- criado_em
- atualizado_em

### comissoes

Tabela responsável por armazenar as comissões dos profissionais.

Campos previstos:

- id
- pagamento_id
- profissional_id
- valor_base
- percentual
- valor_comissao
- status
- criado_em
- atualizado_em

### bloqueios_agenda

Tabela responsável por armazenar bloqueios de horários dos profissionais.

Campos previstos:

- id
- profissional_id
- data_hora_inicio
- data_hora_fim
- motivo
- criado_em
- atualizado_em

## Relacionamentos iniciais

- Um cliente pode ter vários agendamentos.
- Um profissional pode ter vários agendamentos.
- Um serviço pode estar em vários agendamentos.
- Um agendamento pode ter um pagamento.
- Um pagamento pode gerar uma comissão.
- Um profissional pode ter vários bloqueios de agenda.