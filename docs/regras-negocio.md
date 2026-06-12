# Regras de Negócio — EMFISIO

## Clientes

RN01 - Todo cliente deve possuir nome, telefone e status.

RN02 - O cliente poderá estar com status ativo ou inativo.

RN03 - Clientes inativos não devem ser utilizados em novos agendamentos.

## Profissionais

RN04 - Todo profissional deve possuir nome, especialidade e percentual de comissão.

RN05 - O profissional poderá bloquear horários da própria agenda.

RN06 - O profissional só poderá receber comissão após confirmação do pagamento.

## Serviços

RN07 - Todo serviço deve possuir nome, duração e valor.

RN08 - Os serviços poderão ser classificados como sessão avulsa, pacote ou mensalidade.

## Agendamentos

RN09 - Um agendamento deve estar vinculado a um cliente, profissional e serviço.

RN10 - O sistema não deve permitir dois agendamentos no mesmo horário para o mesmo profissional.

RN11 - O cancelamento deve respeitar antecedência mínima de 24 horas.

RN12 - A regra de antecedência mínima para cancelamento poderá ser alterada pelo administrador.

RN13 - Um agendamento poderá ter os seguintes status:

- Agendado
- Confirmado
- Realizado
- Cancelado
- Faltou
- Reagendado

## Faltas

RN14 - Uma falta não deve gerar cobrança.

RN15 - Uma falta não deve gerar comissão para o profissional.

## Pagamentos

RN16 - O pagamento poderá ocorrer antes ou depois da sessão.

RN17 - O pagamento poderá ter os seguintes status:

- Pendente
- Pago
- Cancelado
- Estornado

RN18 - A comissão só deverá ser gerada após o pagamento ser confirmado como pago.

## Comissões

RN19 - A comissão será calculada com base no percentual definido para o profissional.

RN20 - O valor da comissão será calculado sobre o valor efetivamente pago.

## Dashboard

RN21 - O dashboard deve exibir informações de faturamento, atendimentos, cancelamentos, faltas e comissões.

RN22 - Os indicadores devem poder ser filtrados por período.