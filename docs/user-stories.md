# User Stories — EMFISIO

## Gestão de Clientes

### US01 - Cadastrar cliente

Como usuário administrativo,
quero cadastrar clientes,
para manter os dados dos pacientes organizados no sistema.

Critérios de aceite:

- O sistema deve permitir informar nome, telefone, e-mail, CPF e data de nascimento.
- O campo nome deve ser obrigatório.
- O campo telefone deve ser obrigatório.
- Após salvar, o cliente deve aparecer na lista de clientes.

## Gestão de Profissionais

### US02 - Cadastrar profissional

Como administrador,
quero cadastrar profissionais,
para disponibilizá-los na agenda de atendimentos.

Critérios de aceite:

- O sistema deve permitir informar nome, especialidade e percentual de comissão.
- O profissional deve possuir status ativo ou inativo.
- Apenas profissionais ativos devem aparecer para agendamento.

## Gestão de Serviços

### US03 - Cadastrar serviço

Como administrador,
quero cadastrar serviços,
para controlar os atendimentos oferecidos pela clínica.

Critérios de aceite:

- O sistema deve permitir informar nome, duração e valor.
- O serviço deve possuir tipo: avulso, pacote ou mensalidade.
- Apenas serviços ativos devem aparecer para agendamento.

## Agenda

### US04 - Realizar agendamento

Como usuário da recepção,
quero agendar um atendimento,
para organizar a agenda dos profissionais.

Critérios de aceite:

- O sistema deve permitir selecionar cliente, profissional, serviço, data e horário.
- O sistema não deve permitir conflito de horário para o mesmo profissional.
- O agendamento deve iniciar com status agendado.

## Pagamentos

### US05 - Registrar pagamento

Como usuário administrativo,
quero registrar pagamentos,
para controlar os recebimentos da clínica.

Critérios de aceite:

- O pagamento deve estar vinculado a um agendamento.
- O sistema deve permitir informar valor, forma de pagamento e status.
- Quando o pagamento for confirmado como pago, o sistema deve gerar comissão.

## Comissões

### US06 - Calcular comissão

Como administrador,
quero que o sistema calcule comissões automaticamente,
para controlar os valores devidos aos profissionais.

Critérios de aceite:

- A comissão deve ser calculada com base no percentual do profissional.
- A comissão só deve ser gerada para pagamentos confirmados.
- Faltas não devem gerar comissão.

## Dashboard

### US07 - Visualizar indicadores

Como administrador,
quero visualizar indicadores do sistema,
para acompanhar resultados financeiros e operacionais.

Critérios de aceite:

- O sistema deve exibir total recebido.
- O sistema deve exibir total pendente.
- O sistema deve exibir número de atendimentos realizados.
- O sistema deve exibir número de faltas e cancelamentos.