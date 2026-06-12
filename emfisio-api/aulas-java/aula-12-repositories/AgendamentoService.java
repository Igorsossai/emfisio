import java.util.Scanner;

public class AgendamentoService {

    private AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public void criar(
            Scanner scanner,
            ClienteService clienteService,
            ProfissionalService profissionalService,
            ServicoService servicoService
    ) {
        System.out.println();
        System.out.println("=== Criar Agendamento ===");

        if (clienteService.contarTodos() == 0) {
            System.out.println("Não é possível criar agendamento. Nenhum cliente cadastrado.");
            return;
        }

        if (profissionalService.contarTodos() == 0) {
            System.out.println("Não é possível criar agendamento. Nenhum profissional cadastrado.");
            return;
        }

        if (servicoService.contarTodos() == 0) {
            System.out.println("Não é possível criar agendamento. Nenhum serviço cadastrado.");
            return;
        }

        clienteService.listarAtivos();
        System.out.println("Digite o código do cliente:");
        int codigoCliente = scanner.nextInt();

        profissionalService.listarAtivosDisponiveis();
        System.out.println("Digite o código do profissional:");
        int codigoProfissional = scanner.nextInt();

        servicoService.listarAtivos();
        System.out.println("Digite o código do serviço:");
        int codigoServico = scanner.nextInt();
        scanner.nextLine();

        Cliente clienteSelecionado = clienteService.buscarPorCodigo(codigoCliente);
        Profissional profissionalSelecionado = profissionalService.buscarPorCodigo(codigoProfissional);
        Servico servicoSelecionado = servicoService.buscarPorCodigo(codigoServico);

        if (clienteSelecionado == null) {
            System.out.println("Código de cliente inválido.");
            return;
        }

        if (profissionalSelecionado == null) {
            System.out.println("Código de profissional inválido.");
            return;
        }

        if (servicoSelecionado == null) {
            System.out.println("Código de serviço inválido.");
            return;
        }

        if (!clienteSelecionado.isAtivo()) {
            System.out.println("Cliente inativo. Agendamento não permitido.");
            return;
        }

        if (!profissionalSelecionado.isAtivo()) {
            System.out.println("Profissional inativo. Agendamento não permitido.");
            return;
        }

        if (!profissionalSelecionado.isDisponivel()) {
            System.out.println("Profissional indisponível. Agendamento não permitido.");
            return;
        }

        if (!servicoSelecionado.isAtivo()) {
            System.out.println("Serviço inativo. Agendamento não permitido.");
            return;
        }

        System.out.println("Digite o status do atendimento: AGENDADO, CONFIRMADO, REALIZADO, CANCELADO ou FALTOU");
        String statusAgendamento = scanner.nextLine();

        System.out.println("Digite o status do pagamento: PAGO, PENDENTE, CANCELADO ou ESTORNADO");
        String statusPagamento = scanner.nextLine();

        System.out.println("Digite quantas horas faltam para o atendimento:");
        int horasAntesAtendimento = scanner.nextInt();
        scanner.nextLine();

        Agendamento agendamento = new Agendamento(
                clienteSelecionado,
                profissionalSelecionado,
                servicoSelecionado,
                statusAgendamento,
                statusPagamento,
                horasAntesAtendimento
        );

        agendamentoRepository.salvar(agendamento);

        System.out.println("Agendamento criado com sucesso.");
    }

    public void listarTodos() {
        System.out.println();
        System.out.println("=== Lista de Agendamentos ===");

        if (agendamentoRepository.estaVazio()) {
            System.out.println("Nenhum agendamento cadastrado.");
            return;
        }

        for (int i = 0; i < agendamentoRepository.listarTodos().size(); i++) {
            System.out.println("Agendamento código: " + i);
            agendamentoRepository.listarTodos().get(i).exibirResumo();
        }
    }

    public void listarComComissao() {
        System.out.println();
        System.out.println("=== Agendamentos com Comissão ===");

        if (agendamentoRepository.estaVazio()) {
            System.out.println("Nenhum agendamento cadastrado.");
            return;
        }

        boolean encontrou = false;

        for (Agendamento agendamento : agendamentoRepository.listarTodos()) {
            if (agendamento.deveGerarComissao()) {
                agendamento.exibirResumo();
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum agendamento gerou comissão.");
        }
    }

    public int contarTodos() {
        return agendamentoRepository.contarTodos();
    }

    public int contarComissoesGeradas() {
        int total = 0;

        for (Agendamento agendamento : agendamentoRepository.listarTodos()) {
            if (agendamento.deveGerarComissao()) {
                total++;
            }
        }

        return total;
    }

    public double calcularFaturamentoTotal() {
        double total = 0;

        for (Agendamento agendamento : agendamentoRepository.listarTodos()) {
            if (agendamento.pagamentoConfirmado()) {
                total += agendamento.getValorServico();
            }
        }

        return total;
    }
}