public class Agendamento {

    private int id;
    private int clienteId;
    private int profissionalId;
    private int servicoId;
    private String clienteNome;
    private String profissionalNome;
    private String servicoNome;
    private double valorServico;
    private String statusAgendamento;
    private String statusPagamento;
    private int horasAntesAtendimento;

    public Agendamento(
            int id,
            int clienteId,
            int profissionalId,
            int servicoId,
            String clienteNome,
            String profissionalNome,
            String servicoNome,
            double valorServico,
            String statusAgendamento,
            String statusPagamento,
            int horasAntesAtendimento
    ) {
        this.id = id;
        this.clienteId = clienteId;
        this.profissionalId = profissionalId;
        this.servicoId = servicoId;
        this.clienteNome = clienteNome;
        this.profissionalNome = profissionalNome;
        this.servicoNome = servicoNome;
        this.valorServico = valorServico;
        this.statusAgendamento = statusAgendamento;
        this.statusPagamento = statusPagamento;
        this.horasAntesAtendimento = horasAntesAtendimento;
    }

    public Agendamento(
            int clienteId,
            int profissionalId,
            int servicoId,
            String statusAgendamento,
            String statusPagamento,
            int horasAntesAtendimento
    ) {
        this.clienteId = clienteId;
        this.profissionalId = profissionalId;
        this.servicoId = servicoId;
        this.statusAgendamento = statusAgendamento;
        this.statusPagamento = statusPagamento;
        this.horasAntesAtendimento = horasAntesAtendimento;
    }

    public int getId() {
        return id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public int getProfissionalId() {
        return profissionalId;
    }

    public int getServicoId() {
        return servicoId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public String getProfissionalNome() {
        return profissionalNome;
    }

    public String getServicoNome() {
        return servicoNome;
    }

    public double getValorServico() {
        return valorServico;
    }

    public String getStatusAgendamento() {
        return statusAgendamento;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public int getHorasAntesAtendimento() {
        return horasAntesAtendimento;
    }

    public void exibirDados() {
        System.out.println("Código: " + id);
        System.out.println("Cliente: " + clienteNome);
        System.out.println("Profissional: " + profissionalNome);
        System.out.println("Serviço: " + servicoNome);
        System.out.println("Valor do serviço: R$ " + valorServico);
        System.out.println("Status do agendamento: " + statusAgendamento);
        System.out.println("Status do pagamento: " + statusPagamento);
        System.out.println("Horas antes do atendimento: " + horasAntesAtendimento);
        System.out.println("-----------------------------------");
    }
}