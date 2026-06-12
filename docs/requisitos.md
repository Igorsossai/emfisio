# Requisitos do Sistema — EMFISIO

## Objetivo do sistema

O sistema EMFISIO tem como objetivo auxiliar na gestão de atendimentos, agenda, pagamentos, comissões e processos administrativos de uma clínica de fisioterapia e pilates.

## Perfis de usuário

### Administrador

Usuário com acesso completo ao sistema.

Permissões:

- Gerenciar clientes
- Gerenciar profissionais
- Gerenciar serviços
- Gerenciar agendamentos
- Gerenciar pagamentos
- Visualizar dashboard
- Gerar relatórios
- Configurar regras do sistema

### Profissional

Usuário responsável pelos atendimentos.

Permissões:

- Visualizar agenda
- Bloquear horários
- Consultar clientes vinculados
- Visualizar comissões

### Recepção

Usuário responsável por atividades administrativas.

Permissões:

- Cadastrar clientes
- Realizar agendamentos
- Confirmar pagamentos
- Consultar agenda

### Cliente

Usuário que poderá futuramente acessar o sistema para agendar ou consultar seus atendimentos.

Permissões:

- Visualizar horários disponíveis
- Solicitar agendamento
- Consultar seus atendimentos

## Requisitos funcionais

RF01 - O sistema deve permitir cadastrar clientes.

RF02 - O sistema deve permitir editar dados dos clientes.

RF03 - O sistema deve permitir cadastrar profissionais.

RF04 - O sistema deve permitir cadastrar serviços.

RF05 - O sistema deve permitir realizar agendamentos.

RF06 - O sistema deve permitir cancelar agendamentos.

RF07 - O sistema deve permitir registrar faltas.

RF08 - O sistema deve permitir registrar pagamentos.

RF09 - O sistema deve calcular comissões dos profissionais.

RF10 - O sistema deve exibir indicadores em dashboard.

RF11 - O sistema deve permitir bloquear horários na agenda.

RF12 - O sistema deve permitir consultar relatórios por período.

## Requisitos não funcionais

RNF01 - O sistema deve ser desenvolvido com Java e Spring Boot no backend.

RNF02 - O sistema deve ser desenvolvido com React no frontend.

RNF03 - O sistema deve utilizar SQL Server como banco de dados.

RNF04 - O sistema deve utilizar APIs REST para comunicação entre frontend e backend.

RNF05 - O sistema deve utilizar Git para versionamento de código.

RNF06 - O sistema deve ter documentação técnica no repositório.

RNF07 - O sistema deve ter organização de tarefas com metodologia ágil.