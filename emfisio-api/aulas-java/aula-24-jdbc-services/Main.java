import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ClienteRepository clienteRepository = new ClienteRepository();
        ClienteService clienteService = new ClienteService(clienteRepository);

        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();

            System.out.println("Escolha uma opção:");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarCliente(scanner, clienteService);
                    break;
                case 2:
                    listarClientes(clienteService);
                    break;
                case 3:
                    listarClientesAtivos(clienteService);
                    break;
                case 4:
                    buscarClientePorNome(scanner, clienteService);
                    break;
                case 5:
                    buscarClientePorId(scanner, clienteService);
                    break;
                case 6:
                    editarCliente(scanner, clienteService);
                    break;
                case 7:
                    inativarCliente(scanner, clienteService);
                    break;
                case 8:
                    reativarCliente(scanner, clienteService);
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
        System.out.println("        EMFISIO - AULA 24            ");
        System.out.println("        JDBC com Services            ");
        System.out.println("=====================================");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Listar clientes");
        System.out.println("3 - Listar clientes ativos");
        System.out.println("4 - Buscar cliente por nome");
        System.out.println("5 - Buscar cliente por ID");
        System.out.println("6 - Editar cliente");
        System.out.println("7 - Inativar cliente");
        System.out.println("8 - Reativar cliente");
        System.out.println("0 - Sair");
        System.out.println("=====================================");
    }

    public static void cadastrarCliente(Scanner scanner, ClienteService clienteService) {
        System.out.println();
        System.out.println("=== Cadastro de Cliente ===");

        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();

        System.out.println("Digite a idade:");
        int idade = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o telefone:");
        String telefone = scanner.nextLine();

        boolean cadastrou = clienteService.cadastrar(nome, idade, telefone);

        if (cadastrou) {
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Não foi possível cadastrar o cliente.");
        }
    }

    public static void listarClientes(ClienteService clienteService) {
        System.out.println();
        System.out.println("=== Clientes cadastrados ===");

        ArrayList<Cliente> clientes = clienteService.listarTodos();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return;
        }

        for (Cliente cliente : clientes) {
            cliente.exibirDados();
        }
    }

    public static void listarClientesAtivos(ClienteService clienteService) {
        System.out.println();
        System.out.println("=== Clientes ativos ===");

        ArrayList<Cliente> clientes = clienteService.listarAtivos();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente ativo encontrado.");
            return;
        }

        for (Cliente cliente : clientes) {
            cliente.exibirDados();
        }
    }

    public static void buscarClientePorNome(Scanner scanner, ClienteService clienteService) {
        System.out.println();
        System.out.println("=== Buscar Cliente por Nome ===");

        System.out.println("Digite parte do nome:");
        String nomeBusca = scanner.nextLine();

        ArrayList<Cliente> clientes = clienteService.buscarPorNome(nomeBusca);

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return;
        }

        for (Cliente cliente : clientes) {
            cliente.exibirDados();
        }
    }

    public static void buscarClientePorId(Scanner scanner, ClienteService clienteService) {
        System.out.println();
        System.out.println("=== Buscar Cliente por ID ===");

        System.out.println("Digite o código do cliente:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = clienteService.buscarPorId(id);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        cliente.exibirDados();
    }

    public static void editarCliente(Scanner scanner, ClienteService clienteService) {
        System.out.println();
        System.out.println("=== Editar Cliente ===");

        System.out.println("Digite o código do cliente que deseja editar:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Cliente clienteExistente = clienteService.buscarPorId(id);

        if (clienteExistente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("Cliente encontrado:");
        clienteExistente.exibirDados();

        System.out.println("Digite o novo nome:");
        String novoNome = scanner.nextLine();

        System.out.println("Digite a nova idade:");
        int novaIdade = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o novo telefone:");
        String novoTelefone = scanner.nextLine();

        boolean editou = clienteService.editar(id, novoNome, novaIdade, novoTelefone);

        if (editou) {
            System.out.println("Cliente atualizado com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar o cliente.");
        }
    }

    public static void inativarCliente(Scanner scanner, ClienteService clienteService) {
        System.out.println();
        System.out.println("=== Inativar Cliente ===");

        System.out.println("Digite o código do cliente que deseja inativar:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = clienteService.buscarPorId(id);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("Cliente encontrado:");
        cliente.exibirDados();

        boolean inativou = clienteService.inativar(id);

        if (inativou) {
            System.out.println("Cliente inativado com sucesso!");
        } else {
            System.out.println("Não foi possível inativar o cliente.");
        }
    }

    public static void reativarCliente(Scanner scanner, ClienteService clienteService) {
        System.out.println();
        System.out.println("=== Reativar Cliente ===");

        System.out.println("Digite o código do cliente que deseja reativar:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = clienteService.buscarPorId(id);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("Cliente encontrado:");
        cliente.exibirDados();

        boolean reativou = clienteService.reativar(id);

        if (reativou) {
            System.out.println("Cliente reativado com sucesso!");
        } else {
            System.out.println("Não foi possível reativar o cliente.");
        }
    }
}