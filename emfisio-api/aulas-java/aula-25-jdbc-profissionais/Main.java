import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ProfissionalRepository profissionalRepository = new ProfissionalRepository();
        ProfissionalService profissionalService = new ProfissionalService(profissionalRepository);

        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();

            System.out.println("Escolha uma opção:");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarProfissional(scanner, profissionalService);
                    break;
                case 2:
                    listarProfissionais(profissionalService);
                    break;
                case 3:
                    listarProfissionaisAtivos(profissionalService);
                    break;
                case 4:
                    listarProfissionaisAtivosDisponiveis(profissionalService);
                    break;
                case 5:
                    buscarProfissionalPorNome(scanner, profissionalService);
                    break;
                case 6:
                    buscarProfissionalPorId(scanner, profissionalService);
                    break;
                case 7:
                    editarProfissional(scanner, profissionalService);
                    break;
                case 8:
                    inativarProfissional(scanner, profissionalService);
                    break;
                case 9:
                    reativarProfissional(scanner, profissionalService);
                    break;
                case 10:
                    alterarDisponibilidade(scanner, profissionalService);
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
        System.out.println("        EMFISIO - AULA 25            ");
        System.out.println("      Módulo de Profissionais        ");
        System.out.println("=====================================");
        System.out.println("1 - Cadastrar profissional");
        System.out.println("2 - Listar profissionais");
        System.out.println("3 - Listar profissionais ativos");
        System.out.println("4 - Listar ativos e disponíveis");
        System.out.println("5 - Buscar profissional por nome");
        System.out.println("6 - Buscar profissional por ID");
        System.out.println("7 - Editar profissional");
        System.out.println("8 - Inativar profissional");
        System.out.println("9 - Reativar profissional");
        System.out.println("10 - Alterar disponibilidade");
        System.out.println("0 - Sair");
        System.out.println("=====================================");
    }

    public static void cadastrarProfissional(
            Scanner scanner,
            ProfissionalService profissionalService
    ) {
        System.out.println();
        System.out.println("=== Cadastro de Profissional ===");

        System.out.println("Digite o nome do profissional:");
        String nome = scanner.nextLine();

        System.out.println("Digite a especialidade:");
        String especialidade = scanner.nextLine();

        System.out.println("Digite o percentual de comissão:");
        double percentualComissao = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("O profissional está disponível? (true/false):");
        boolean disponivel = scanner.nextBoolean();
        scanner.nextLine();

        boolean cadastrou = profissionalService.cadastrar(
                nome,
                especialidade,
                percentualComissao,
                disponivel
        );

        if (cadastrou) {
            System.out.println("Profissional cadastrado com sucesso!");
        } else {
            System.out.println("Não foi possível cadastrar o profissional.");
        }
    }

    public static void listarProfissionais(ProfissionalService profissionalService) {
        System.out.println();
        System.out.println("=== Profissionais cadastrados ===");

        ArrayList<Profissional> profissionais = profissionalService.listarTodos();

        if (profissionais.isEmpty()) {
            System.out.println("Nenhum profissional encontrado.");
            return;
        }

        for (Profissional profissional : profissionais) {
            profissional.exibirDados();
        }
    }

    public static void listarProfissionaisAtivos(ProfissionalService profissionalService) {
        System.out.println();
        System.out.println("=== Profissionais ativos ===");

        ArrayList<Profissional> profissionais = profissionalService.listarAtivos();

        if (profissionais.isEmpty()) {
            System.out.println("Nenhum profissional ativo encontrado.");
            return;
        }

        for (Profissional profissional : profissionais) {
            profissional.exibirDados();
        }
    }

    public static void listarProfissionaisAtivosDisponiveis(
            ProfissionalService profissionalService
    ) {
        System.out.println();
        System.out.println("=== Profissionais ativos e disponíveis ===");

        ArrayList<Profissional> profissionais =
                profissionalService.listarAtivosDisponiveis();

        if (profissionais.isEmpty()) {
            System.out.println("Nenhum profissional ativo e disponível encontrado.");
            return;
        }

        for (Profissional profissional : profissionais) {
            profissional.exibirDados();
        }
    }

    public static void buscarProfissionalPorNome(
            Scanner scanner,
            ProfissionalService profissionalService
    ) {
        System.out.println();
        System.out.println("=== Buscar Profissional por Nome ===");

        System.out.println("Digite parte do nome:");
        String nomeBusca = scanner.nextLine();

        ArrayList<Profissional> profissionais =
                profissionalService.buscarPorNome(nomeBusca);

        if (profissionais.isEmpty()) {
            System.out.println("Nenhum profissional encontrado.");
            return;
        }

        for (Profissional profissional : profissionais) {
            profissional.exibirDados();
        }
    }

    public static void buscarProfissionalPorId(
            Scanner scanner,
            ProfissionalService profissionalService
    ) {
        System.out.println();
        System.out.println("=== Buscar Profissional por ID ===");

        System.out.println("Digite o código do profissional:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Profissional profissional = profissionalService.buscarPorId(id);

        if (profissional == null) {
            System.out.println("Profissional não encontrado.");
            return;
        }

        profissional.exibirDados();
    }

    public static void editarProfissional(
            Scanner scanner,
            ProfissionalService profissionalService
    ) {
        System.out.println();
        System.out.println("=== Editar Profissional ===");

        System.out.println("Digite o código do profissional que deseja editar:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Profissional profissionalExistente = profissionalService.buscarPorId(id);

        if (profissionalExistente == null) {
            System.out.println("Profissional não encontrado.");
            return;
        }

        System.out.println("Profissional encontrado:");
        profissionalExistente.exibirDados();

        System.out.println("Digite o novo nome:");
        String novoNome = scanner.nextLine();

        System.out.println("Digite a nova especialidade:");
        String novaEspecialidade = scanner.nextLine();

        System.out.println("Digite o novo percentual de comissão:");
        double novoPercentualComissao = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("O profissional está disponível? (true/false):");
        boolean novoDisponivel = scanner.nextBoolean();
        scanner.nextLine();

        boolean editou = profissionalService.editar(
                id,
                novoNome,
                novaEspecialidade,
                novoPercentualComissao,
                novoDisponivel
        );

        if (editou) {
            System.out.println("Profissional atualizado com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar o profissional.");
        }
    }

    public static void inativarProfissional(
            Scanner scanner,
            ProfissionalService profissionalService
    ) {
        System.out.println();
        System.out.println("=== Inativar Profissional ===");

        System.out.println("Digite o código do profissional que deseja inativar:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Profissional profissional = profissionalService.buscarPorId(id);

        if (profissional == null) {
            System.out.println("Profissional não encontrado.");
            return;
        }

        System.out.println("Profissional encontrado:");
        profissional.exibirDados();

        boolean inativou = profissionalService.inativar(id);

        if (inativou) {
            System.out.println("Profissional inativado com sucesso!");
        } else {
            System.out.println("Não foi possível inativar o profissional.");
        }
    }

    public static void reativarProfissional(
            Scanner scanner,
            ProfissionalService profissionalService
    ) {
        System.out.println();
        System.out.println("=== Reativar Profissional ===");

        System.out.println("Digite o código do profissional que deseja reativar:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Profissional profissional = profissionalService.buscarPorId(id);

        if (profissional == null) {
            System.out.println("Profissional não encontrado.");
            return;
        }

        System.out.println("Profissional encontrado:");
        profissional.exibirDados();

        boolean reativou = profissionalService.reativar(id);

        if (reativou) {
            System.out.println("Profissional reativado com sucesso!");
        } else {
            System.out.println("Não foi possível reativar o profissional.");
        }
    }

    public static void alterarDisponibilidade(
            Scanner scanner,
            ProfissionalService profissionalService
    ) {
        System.out.println();
        System.out.println("=== Alterar Disponibilidade ===");

        System.out.println("Digite o código do profissional:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Profissional profissional = profissionalService.buscarPorId(id);

        if (profissional == null) {
            System.out.println("Profissional não encontrado.");
            return;
        }

        System.out.println("Profissional encontrado:");
        profissional.exibirDados();

        System.out.println("Informe a nova disponibilidade (true/false):");
        boolean disponivel = scanner.nextBoolean();
        scanner.nextLine();

        boolean alterou = profissionalService.alterarDisponibilidade(id, disponivel);

        if (alterou) {
            System.out.println("Disponibilidade alterada com sucesso!");
        } else {
            System.out.println("Não foi possível alterar a disponibilidade.");
        }
    }
}