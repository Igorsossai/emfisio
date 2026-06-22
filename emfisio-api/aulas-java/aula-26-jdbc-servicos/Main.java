import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ServicoRepository servicoRepository = new ServicoRepository();
        ServicoService servicoService = new ServicoService(servicoRepository);

        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();

            System.out.println("Escolha uma opção:");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarServico(scanner, servicoService);
                    break;
                case 2:
                    listarServicos(servicoService);
                    break;
                case 3:
                    listarServicosAtivos(servicoService);
                    break;
                case 4:
                    buscarServicoPorNome(scanner, servicoService);
                    break;
                case 5:
                    buscarServicoPorId(scanner, servicoService);
                    break;
                case 6:
                    editarServico(scanner, servicoService);
                    break;
                case 7:
                    inativarServico(scanner, servicoService);
                    break;
                case 8:
                    reativarServico(scanner, servicoService);
                    break;
                case 9:
                    contarServicosAtivos(servicoService);
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
        System.out.println("        EMFISIO - AULA 26            ");
        System.out.println("         Módulo de Serviços          ");
        System.out.println("=====================================");
        System.out.println("1 - Cadastrar serviço");
        System.out.println("2 - Listar serviços");
        System.out.println("3 - Listar serviços ativos");
        System.out.println("4 - Buscar serviço por nome");
        System.out.println("5 - Buscar serviço por ID");
        System.out.println("6 - Editar serviço");
        System.out.println("7 - Inativar serviço");
        System.out.println("8 - Reativar serviço");
        System.out.println("9 - Contar serviços ativos");
        System.out.println("0 - Sair");
        System.out.println("=====================================");
    }

    public static void cadastrarServico(
            Scanner scanner,
            ServicoService servicoService
    ) {
        System.out.println();
        System.out.println("=== Cadastro de Serviço ===");

        System.out.println("Digite o nome do serviço:");
        String nome = scanner.nextLine();

        System.out.println("Digite a duração em minutos:");
        int duracaoMinutos = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o valor do serviço:");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        boolean cadastrou = servicoService.cadastrar(
                nome,
                duracaoMinutos,
                valor
        );

        if (cadastrou) {
            System.out.println("Serviço cadastrado com sucesso!");
        } else {
            System.out.println("Não foi possível cadastrar o serviço.");
        }
    }

    public static void listarServicos(ServicoService servicoService) {
        System.out.println();
        System.out.println("=== Serviços cadastrados ===");

        ArrayList<Servico> servicos = servicoService.listarTodos();

        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço encontrado.");
            return;
        }

        for (Servico servico : servicos) {
            servico.exibirDados();
        }
    }

    public static void listarServicosAtivos(ServicoService servicoService) {
        System.out.println();
        System.out.println("=== Serviços ativos ===");

        ArrayList<Servico> servicos = servicoService.listarAtivos();

        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço ativo encontrado.");
            return;
        }

        for (Servico servico : servicos) {
            servico.exibirDados();
        }
    }

    public static void buscarServicoPorNome(
            Scanner scanner,
            ServicoService servicoService
    ) {
        System.out.println();
        System.out.println("=== Buscar Serviço por Nome ===");

        System.out.println("Digite parte do nome:");
        String nomeBusca = scanner.nextLine();

        ArrayList<Servico> servicos = servicoService.buscarPorNome(nomeBusca);

        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço encontrado.");
            return;
        }

        for (Servico servico : servicos) {
            servico.exibirDados();
        }
    }

    public static void buscarServicoPorId(
            Scanner scanner,
            ServicoService servicoService
    ) {
        System.out.println();
        System.out.println("=== Buscar Serviço por ID ===");

        System.out.println("Digite o código do serviço:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Servico servico = servicoService.buscarPorId(id);

        if (servico == null) {
            System.out.println("Serviço não encontrado.");
            return;
        }

        servico.exibirDados();
    }

    public static void editarServico(
            Scanner scanner,
            ServicoService servicoService
    ) {
        System.out.println();
        System.out.println("=== Editar Serviço ===");

        System.out.println("Digite o código do serviço que deseja editar:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Servico servicoExistente = servicoService.buscarPorId(id);

        if (servicoExistente == null) {
            System.out.println("Serviço não encontrado.");
            return;
        }

        System.out.println("Serviço encontrado:");
        servicoExistente.exibirDados();

        System.out.println("Digite o novo nome:");
        String novoNome = scanner.nextLine();

        System.out.println("Digite a nova duração em minutos:");
        int novaDuracao = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o novo valor:");
        double novoValor = scanner.nextDouble();
        scanner.nextLine();

        boolean editou = servicoService.editar(
                id,
                novoNome,
                novaDuracao,
                novoValor
        );

        if (editou) {
            System.out.println("Serviço atualizado com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar o serviço.");
        }
    }

    public static void inativarServico(
            Scanner scanner,
            ServicoService servicoService
    ) {
        System.out.println();
        System.out.println("=== Inativar Serviço ===");

        System.out.println("Digite o código do serviço que deseja inativar:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Servico servico = servicoService.buscarPorId(id);

        if (servico == null) {
            System.out.println("Serviço não encontrado.");
            return;
        }

        System.out.println("Serviço encontrado:");
        servico.exibirDados();

        boolean inativou = servicoService.inativar(id);

        if (inativou) {
            System.out.println("Serviço inativado com sucesso!");
        } else {
            System.out.println("Não foi possível inativar o serviço.");
        }
    }

    public static void reativarServico(
            Scanner scanner,
            ServicoService servicoService
    ) {
        System.out.println();
        System.out.println("=== Reativar Serviço ===");

        System.out.println("Digite o código do serviço que deseja reativar:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Servico servico = servicoService.buscarPorId(id);

        if (servico == null) {
            System.out.println("Serviço não encontrado.");
            return;
        }

        System.out.println("Serviço encontrado:");
        servico.exibirDados();

        boolean reativou = servicoService.reativar(id);

        if (reativou) {
            System.out.println("Serviço reativado com sucesso!");
        } else {
            System.out.println("Não foi possível reativar o serviço.");
        }
    }

    public static void contarServicosAtivos(ServicoService servicoService) {
        System.out.println();
        System.out.println("=== Total de Serviços Ativos ===");

        int total = servicoService.contarAtivos();

        System.out.println("Total de serviços ativos: " + total);
    }
}