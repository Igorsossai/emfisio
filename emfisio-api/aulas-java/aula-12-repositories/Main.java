import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ClienteRepository clienteRepository = new ClienteRepository();
        ProfissionalRepository profissionalRepository = new ProfissionalRepository();
        ServicoRepository servicoRepository = new ServicoRepository();
        AgendamentoRepository agendamentoRepository = new AgendamentoRepository();

        ClienteService clienteService = new ClienteService(clienteRepository);
        ProfissionalService profissionalService = new ProfissionalService(profissionalRepository);
        ServicoService servicoService = new ServicoService(servicoRepository);
        AgendamentoService agendamentoService = new AgendamentoService(agendamentoRepository);

        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();

            System.out.println("Escolha uma opção:");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    clienteService.cadastrar(scanner);
                    break;
                case 2:
                    clienteService.listarTodos();
                    break;
                case 3:
                    clienteService.buscarPorNome(scanner);
                    break;
                case 4:
                    clienteService.editar(scanner);
                    break;
                case 5:
                    clienteService.inativar(scanner);
                    break;
                case 6:
                    clienteService.listarAtivos();
                    break;

                case 7:
                    profissionalService.cadastrar(scanner);
                    break;
                case 8:
                    profissionalService.listarTodos();
                    break;
                case 9:
                    profissionalService.buscarPorNome(scanner);
                    break;
                case 10:
                    profissionalService.editar(scanner);
                    break;
                case 11:
                    profissionalService.inativar(scanner);
                    break;
                case 12:
                    profissionalService.listarAtivosDisponiveis();
                    break;

                case 13:
                    servicoService.cadastrar(scanner);
                    break;
                case 14:
                    servicoService.listarTodos();
                    break;
                case 15:
                    servicoService.buscarPorNome(scanner);
                    break;
                case 16:
                    servicoService.editar(scanner);
                    break;
                case 17:
                    servicoService.inativar(scanner);
                    break;
                case 18:
                    servicoService.listarAtivos();
                    break;

                case 19:
                    agendamentoService.criar(scanner, clienteService, profissionalService, servicoService);
                    break;
                case 20:
                    agendamentoService.listarTodos();
                    break;
                case 21:
                    exibirResumoGeral(clienteService, profissionalService, servicoService, agendamentoService);
                    break;
                case 22:
                    agendamentoService.listarComComissao();
                    break;
                case 23:
                    System.out.println("Faturamento total: R$ " + agendamentoService.calcularFaturamentoTotal());
                    break;

                case 0:
                    System.out.println("Encerrando o EMFISIO...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }

        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println();
        System.out.println("=====================================");
        System.out.println("              EMFISIO                ");
        System.out.println("      Aula 12 - Repositories         ");
        System.out.println("=====================================");
        System.out.println("1  - Cadastrar cliente");
        System.out.println("2  - Listar clientes");
        System.out.println("3  - Buscar cliente por nome");
        System.out.println("4  - Editar cliente");
        System.out.println("5  - Inativar cliente");
        System.out.println("6  - Listar clientes ativos");
        System.out.println("-------------------------------------");
        System.out.println("7  - Cadastrar profissional");
        System.out.println("8  - Listar profissionais");
        System.out.println("9  - Buscar profissional por nome");
        System.out.println("10 - Editar profissional");
        System.out.println("11 - Inativar profissional");
        System.out.println("12 - Listar profissionais ativos e disponíveis");
        System.out.println("-------------------------------------");
        System.out.println("13 - Cadastrar serviço");
        System.out.println("14 - Listar serviços");
        System.out.println("15 - Buscar serviço por nome");
        System.out.println("16 - Editar serviço");
        System.out.println("17 - Inativar serviço");
        System.out.println("18 - Listar serviços ativos");
        System.out.println("-------------------------------------");
        System.out.println("19 - Criar agendamento");
        System.out.println("20 - Listar agendamentos");
        System.out.println("21 - Exibir resumo geral");
        System.out.println("22 - Listar agendamentos com comissão");
        System.out.println("23 - Exibir faturamento total");
        System.out.println("0  - Sair");
        System.out.println("=====================================");
    }

    public static void exibirResumoGeral(
            ClienteService clienteService,
            ProfissionalService profissionalService,
            ServicoService servicoService,
            AgendamentoService agendamentoService
    ) {
        System.out.println();
        System.out.println("=== Resumo Geral do Sistema ===");

        System.out.println("Total de clientes: " + clienteService.contarTodos());
        System.out.println("Clientes ativos: " + clienteService.contarAtivos());

        System.out.println("Total de profissionais: " + profissionalService.contarTodos());
        System.out.println("Profissionais ativos: " + profissionalService.contarAtivos());
        System.out.println("Profissionais disponíveis: " + profissionalService.contarDisponiveis());

        System.out.println("Total de serviços: " + servicoService.contarTodos());
        System.out.println("Serviços ativos: " + servicoService.contarAtivos());

        System.out.println("Total de agendamentos: " + agendamentoService.contarTodos());
        System.out.println("Agendamentos com comissão gerada: " + agendamentoService.contarComissoesGeradas());
        System.out.println("Faturamento total: R$ " + agendamentoService.calcularFaturamentoTotal());
    }
}