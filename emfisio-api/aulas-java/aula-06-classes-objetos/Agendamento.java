public class Agendamento {

    Cliente cliente;
    Profissional profissional;
    Servico servico;
    String statusAgendamento;
    String statusPagamento;
    int horasAntesAtendimento;

    public Agendamento(
            Cliente cliente,
            Profissional profissional,
            Servico servico,
            String statusAgendamento,
            String statusPagamento,
            int horasAntesAtendimento
    ) {
        this.cliente = cliente;
        this.profissional = profissional;
        this.servico = servico;
        this.statusAgendamento = statusAgendamento;
        this.statusPagamento = statusPagamento;
        this.horasAntesAtendimento = horasAntesAtendimento;
    }

    public void exibirResumo() {
        System.out.println("=====================================");
        System.out.println("        RESUMO DO AGENDAMENTO        ");
        System.out.println("=====================================");
        System.out.println("Cliente: " + cliente.nome);
        System.out.println("Profissional: " + profissional.nome);
        System.out.println("Serviço: " + servico.nome);
        System.out.println("Valor: R$ " + servico.valor);
        System.out.println("Status do atendimento: " + statusAgendamento);
        System.out.println("Status do pagamento: " + statusPagamento);
    }

    public boolean podeAgendar() {
        return cliente.podeAgendar()
                && profissional.podeAtender()
                && servico.podeSerAgendado();
    }

    public boolean podeCancelar() {
        return horasAntesAtendimento >= 24;
    }

    public boolean deveGerarComissao() {
        return statusPagamento.equals("PAGO")
                && statusAgendamento.equals("REALIZADO");
    }

    public double calcularComissao() {
        return servico.valor * profissional.percentualComissao / 100;
    }

    public void processarAgendamento() {
        System.out.println();
        System.out.println("=== Validação do Agendamento ===");

        if (podeAgendar()) {
            System.out.println("Agendamento permitido.");
        } else {
            System.out.println("Agendamento não permitido.");

            if (!cliente.podeAgendar()) {
                System.out.println("Motivo: cliente inativo.");
            }

            if (!profissional.podeAtender()) {
                System.out.println("Motivo: profissional indisponível.");
            }

            if (!servico.podeSerAgendado()) {
                System.out.println("Motivo: serviço inativo.");
            }
        }
    }

    public void processarCancelamento() {
        System.out.println();
        System.out.println("=== Validação de Cancelamento ===");

        if (podeCancelar()) {
            System.out.println("Cancelamento permitido.");
        } else {
            System.out.println("Cancelamento não permitido.");
            System.out.println("Motivo: antecedência inferior a 24 horas.");
        }
    }

    public void processarComissao() {
        System.out.println();
        System.out.println("=== Cálculo de Comissão ===");

        if (deveGerarComissao()) {
            double valorComissao = calcularComissao();

            System.out.println("Comissão gerada com sucesso.");
            System.out.println("Percentual: " + profissional.percentualComissao + "%");
            System.out.println("Valor da comissão: R$ " + valorComissao);
        } else {
            System.out.println("Comissão não gerada.");

            if (!statusPagamento.equals("PAGO")) {
                System.out.println("Motivo: pagamento não confirmado.");
            }

            if (!statusAgendamento.equals("REALIZADO")) {
                System.out.println("Motivo: atendimento não realizado.");
            }
        }
    }

}