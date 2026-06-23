import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ClienteRepository clienteRepository = new ClienteRepository();
        ProfissionalRepository profissionalRepository = new ProfissionalRepository();
        ServicoRepository servicoRepository = new ServicoRepository();
        AgendamentoRepository agendamentoRepository = new AgendamentoRepository();

        AgendamentoService agendamentoService = new AgendamentoService(
                agendamentoRepository,
                clienteRepository,
                profissionalRepository,
                servicoRepository
        );

        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();

            System.out.println("Escolha uma opção:");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    criarAgendamento(scanner, agendamentoService);
                    break;
                case 2:
                    listarAgendamentos(agendamentoService);
                    break;
                case 3:
                    buscarAgendamentoPorId(scanner, agendamentoService);
                    break;
                case 4:
                    atualizarStatus(scanner, agendamentoService);
                    break;
                case 5:
                    cancelarAgendamento(scanner, agendamentoService);
                    break;
                case 6:
                    listarPorStatus(scanner, agendamentoService);
                    break;
                case 0:
                    System.out.println("Encerrando o EMFISIO...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println();
        System.out.println("=====================================");
        System.out.println("        EMFISIO - AULA 27            ");
        System.out.println("        Módulo de Agendamentos       ");
        System.out.println("=====================================");
        System.out.println("1 - Criar agendamento");
        System.out.println("2 - Listar agendamentos");
        System.out.println("3 - Buscar agendamento por ID");
        System.out.println("4 - Atualizar status do agendamento");
        System.out.println("5 - Cancelar agendamento");
        System.out.println("6 - Listar agendamentos por status");
        System.out.println("0 - Sair");
        System.out.println("=====================================");
    }

    public static void criarAgendamento(
            Scanner scanner,
            AgendamentoService agendamentoService
    ) {
        System.out.println();
        System.out.println("=== Criar Agendamento ===");

        System.out.println("Digite o ID do cliente:");
        int clienteId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o ID do profissional:");
        int profissionalId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o ID do serviço:");
        int servicoId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite as horas antes do atendimento:");
        int horasAntes = scanner.nextInt();
        scanner.nextLine();

        boolean cadastrou = agendamentoService.cadastrar(
                clienteId,
                profissionalId,
                servicoId,
                horasAntes
        );

        if (cadastrou) {
            System.out.println("Agendamento criado com sucesso!");
        } else {
            System.out.println("Não foi possível criar o agendamento.");
        }
    }

    public static void listarAgendamentos(AgendamentoService agendamentoService) {
        System.out.println();
        System.out.println("=== Agendamentos cadastrados ===");

        ArrayList<Agendamento> agendamentos = agendamentoService.listarTodos();

        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado.");
            return;
        }

        for (Agendamento agendamento : agendamentos) {
            agendamento.exibirDados();
        }
    }

    public static void buscarAgendamentoPorId(
            Scanner scanner,
            AgendamentoService agendamentoService
    ) {
        System.out.println();
        System.out.println("=== Buscar Agendamento por ID ===");

        System.out.println("Digite o código do agendamento:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Agendamento agendamento = agendamentoService.buscarPorId(id);

        if (agendamento == null) {
            System.out.println("Agendamento não encontrado.");
            return;
        }

        agendamento.exibirDados();
    }

    public static void atualizarStatus(
            Scanner scanner,
            AgendamentoService agendamentoService
    ) {
        System.out.println();
        System.out.println("=== Atualizar Status do Agendamento ===");

        System.out.println("Digite o código do agendamento:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o novo status:");
        System.out.println("Opções: AGENDADO, CONFIRMADO, REALIZADO, CANCELADO, FALTOU, REAGENDADO");
        String novoStatus = scanner.nextLine();

        boolean atualizou = agendamentoService.atualizarStatus(id, novoStatus);

        if (atualizou) {
            System.out.println("Status atualizado com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar o status.");
        }
    }

    public static void cancelarAgendamento(
            Scanner scanner,
            AgendamentoService agendamentoService
    ) {
        System.out.println();
        System.out.println("=== Cancelar Agendamento ===");

        System.out.println("Digite o código do agendamento:");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean cancelou = agendamentoService.cancelar(id);

        if (cancelou) {
            System.out.println("Agendamento cancelado com sucesso!");
        } else {
            System.out.println("Não foi possível cancelar o agendamento.");
        }
    }

    public static void listarPorStatus(
            Scanner scanner,
            AgendamentoService agendamentoService
    ) {
        System.out.println();
        System.out.println("=== Listar por Status ===");

        System.out.println("Digite o status:");
        System.out.println("Opções: AGENDADO, CONFIRMADO, REALIZADO, CANCELADO, FALTOU, REAGENDADO");
        String status = scanner.nextLine();

        ArrayList<Agendamento> agendamentos = agendamentoService.listarPorStatus(status);

        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado para este status.");
            return;
        }

        for (Agendamento agendamento : agendamentos) {
            agendamento.exibirDados();
        }
    }
}