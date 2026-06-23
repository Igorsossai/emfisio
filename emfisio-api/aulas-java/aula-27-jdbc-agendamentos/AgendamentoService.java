import java.util.ArrayList;

public class AgendamentoService {

    private AgendamentoRepository agendamentoRepository;
    private ClienteRepository clienteRepository;
    private ProfissionalRepository profissionalRepository;
    private ServicoRepository servicoRepository;

    public AgendamentoService(
            AgendamentoRepository agendamentoRepository,
            ClienteRepository clienteRepository,
            ProfissionalRepository profissionalRepository,
            ServicoRepository servicoRepository
    ) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.profissionalRepository = profissionalRepository;
        this.servicoRepository = servicoRepository;
    }

    public boolean cadastrar(
            int clienteId,
            int profissionalId,
            int servicoId,
            int horasAntesAtendimento
    ) {
        if (clienteId <= 0 || profissionalId <= 0 || servicoId <= 0) {
            System.out.println("IDs inválidos.");
            return false;
        }

        if (horasAntesAtendimento < 0) {
            System.out.println("Horas antes do atendimento não pode ser negativa.");
            return false;
        }

        Cliente cliente = clienteRepository.buscarPorId(clienteId);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return false;
        }

        if (!cliente.isAtivo()) {
            System.out.println("Cliente inativo não pode agendar.");
            return false;
        }

        Profissional profissional = profissionalRepository.buscarPorId(profissionalId);

        if (profissional == null) {
            System.out.println("Profissional não encontrado.");
            return false;
        }

        if (!profissional.isAtivo()) {
            System.out.println("Profissional inativo não pode atender.");
            return false;
        }

        if (!profissional.isDisponivel()) {
            System.out.println("Profissional indisponível para atendimento.");
            return false;
        }

        Servico servico = servicoRepository.buscarPorId(servicoId);

        if (servico == null) {
            System.out.println("Serviço não encontrado.");
            return false;
        }

        if (!servico.isAtivo()) {
            System.out.println("Serviço inativo não pode ser agendado.");
            return false;
        }

        Agendamento agendamento = new Agendamento(
                clienteId,
                profissionalId,
                servicoId,
                "AGENDADO",
                "PENDENTE",
                horasAntesAtendimento
        );

        return agendamentoRepository.cadastrar(agendamento);
    }

    public boolean atualizarStatus(int id, String novoStatus) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return false;
        }

        if (!statusAgendamentoValido(novoStatus)) {
            System.out.println("Status de agendamento inválido.");
            return false;
        }

        Agendamento agendamento = agendamentoRepository.buscarPorId(id);

        if (agendamento == null) {
            System.out.println("Agendamento não encontrado.");
            return false;
        }

        return agendamentoRepository.atualizarStatus(id, novoStatus.toUpperCase());
    }

    public boolean cancelar(int id) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return false;
        }

        Agendamento agendamento = agendamentoRepository.buscarPorId(id);

        if (agendamento == null) {
            System.out.println("Agendamento não encontrado.");
            return false;
        }

        if (agendamento.getStatusAgendamento().equals("CANCELADO")) {
            System.out.println("Este agendamento já está cancelado.");
            return false;
        }

        if (agendamento.getStatusAgendamento().equals("REALIZADO")) {
            System.out.println("Atendimento já realizado não pode ser cancelado.");
            return false;
        }

        if (agendamento.getHorasAntesAtendimento() < 24) {
            System.out.println("Cancelamento não permitido. Antecedência inferior a 24 horas.");
            return false;
        }

        return agendamentoRepository.cancelar(id);
    }

    public Agendamento buscarPorId(int id) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return null;
        }

        return agendamentoRepository.buscarPorId(id);
    }

    public ArrayList<Agendamento> listarTodos() {
        return agendamentoRepository.listarTodos();
    }

    public ArrayList<Agendamento> listarPorStatus(String status) {
        if (!statusAgendamentoValido(status)) {
            System.out.println("Status inválido.");
            return new ArrayList<>();
        }

        return agendamentoRepository.listarPorStatus(status.toUpperCase());
    }

    private boolean statusAgendamentoValido(String status) {
        if (status == null || status.isBlank()) {
            return false;
        }

        String statusFormatado = status.toUpperCase();

        return statusFormatado.equals("AGENDADO")
                || statusFormatado.equals("CONFIRMADO")
                || statusFormatado.equals("REALIZADO")
                || statusFormatado.equals("CANCELADO")
                || statusFormatado.equals("FALTOU")
                || statusFormatado.equals("REAGENDADO");
    }
}