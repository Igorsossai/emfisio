public class Agendamento {

    private Cliente cliente;
    private Profissional profissional;
    private Servico servico;
    private String statusAgendamento;
    private String statusPagamento;
    private int horasAntesAtendimento;

    public Agendamento(
            Cliente cliente,
            Profissional profissional,
            Servico servico,
            String statusAgendamento,
            String statusPagamento,
            int horasAntesAtendimento
    ) {
        setCliente(cliente);
        setProfissional(profissional);
        setServico(servico);
        setStatusAgendamento(statusAgendamento);
        setStatusPagamento(statusPagamento);
        setHorasAntesAtendimento(horasAntesAtendimento);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (cliente != null) {
            this.cliente = cliente;
        } else {
            System.out.println("Cliente inválido. O agendamento precisa de um cliente.");
        }
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        if (profissional != null) {
            this.profissional = profissional;
        } else {
            System.out.println("Profissional inválido. O agendamento precisa de um profissional.");
        }
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        if (servico != null) {
            this.servico = servico;
        } else {
            System.out.println("Serviço inválido. O agendamento precisa de um serviço.");
        }
    }

    public String getStatusAgendamento() {
        return statusAgendamento;
    }

    public void setStatusAgendamento(String statusAgendamento) {
        if (statusAgendamento != null && !statusAgendamento.isBlank()) {
            this.statusAgendamento = statusAgendamento.toUpperCase();
        } else {
            System.out.println("Status do agendamento inválido.");
        }
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        if (statusPagamento != null && !statusPagamento.isBlank()) {
            this.statusPagamento = statusPagamento.toUpperCase();
        } else {
            System.out.println("Status do pagamento inválido.");
        }
    }

    public int getHorasAntesAtendimento() {
        return horasAntesAtendimento;
    }

    public void setHorasAntesAtendimento(int horasAntesAtendimento) {
        if (horasAntesAtendimento >= 0) {
            this.horasAntesAtendimento = horasAntesAtendimento;
        } else {
            System.out.println("Horas inválidas. O valor não pode ser negativo.");
        }
    }

    public void exibirResumo() {
        System.out.println("=====================================");
        System.out.println("        RESUMO DO AGENDAMENTO        ");
        System.out.println("=====================================");
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Profissional: " + profissional.getNome());
        System.out.println("Serviço: " + servico.getNome());
        System.out.println("Valor: R$ " + servico.getValor());
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
        return servico.getValor() * profissional.getPercentualComissao() / 100;
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
            System.out.println("Percentual: " + profissional.getPercentualComissao() + "%");
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