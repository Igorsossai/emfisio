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
            System.out.println("Cliente inválido.");
        }
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        if (profissional != null) {
            this.profissional = profissional;
        } else {
            System.out.println("Profissional inválido.");
        }
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        if (servico != null) {
            this.servico = servico;
        } else {
            System.out.println("Serviço inválido.");
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
            System.out.println("Horas inválidas.");
        }
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

    public void exibirResumo() {
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Profissional: " + profissional.getNome());
        System.out.println("Serviço: " + servico.getNome());
        System.out.println("Valor: R$ " + servico.getValor());
        System.out.println("Status atendimento: " + statusAgendamento);
        System.out.println("Status pagamento: " + statusPagamento);

        if (deveGerarComissao()) {
            System.out.println("Comissão: R$ " + calcularComissao());
        } else {
            System.out.println("Comissão: não gerada");
        }

        System.out.println("-----------------------------------");
    }
}