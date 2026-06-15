import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ClienteRepository clienteRepository = new ClienteRepository();

        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();

            System.out.println("Escolha uma opção:");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarCliente(scanner, clienteRepository);
                    break;
                case 2:
                    listarClientes(clienteRepository);
                    break;
                case 3:
                    listarClientesAtivos(clienteRepository);
                    break;
                case 4:
                    buscarClientePorNome(scanner, clienteRepository);
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
        System.out.println("        EMFISIO - AULA 22            ");
        System.out.println("     JDBC - INSERT pelo Java         ");
        System.out.println("=====================================");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Listar clientes");
        System.out.println("3 - Listar clientes ativos");
        System.out.println("4 - Buscar cliente por nome");
        System.out.println("0 - Sair");
        System.out.println("=====================================");
    }

    public static void cadastrarCliente(Scanner scanner, ClienteRepository clienteRepository) {
        System.out.println();
        System.out.println("=== Cadastro de Cliente ===");

        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();

        System.out.println("Digite a idade:");
        int idade = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o telefone:");
        String telefone = scanner.nextLine();

        if (nome == null || nome.isBlank()) {
            System.out.println("Nome inválido. O nome não pode ser vazio.");
            return;
        }

        if (idade <= 0) {
            System.out.println("Idade inválida. A idade deve ser maior que zero.");
            return;
        }

        if (telefone == null || telefone.isBlank()) {
            System.out.println("Telefone inválido. O telefone não pode ser vazio.");
            return;
        }

        Cliente cliente = new Cliente(nome, idade, telefone, true);

        boolean cadastrou = clienteRepository.cadastrar(cliente);

        if (cadastrou) {
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Não foi possível cadastrar o cliente.");
        }
    }

    public static void listarClientes(ClienteRepository clienteRepository) {
        System.out.println();
        System.out.println("=== Clientes cadastrados ===");

        ArrayList<Cliente> clientes = clienteRepository.listarTodos();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return;
        }

        for (Cliente cliente : clientes) {
            cliente.exibirDados();
        }
    }

    public static void listarClientesAtivos(ClienteRepository clienteRepository) {
        System.out.println();
        System.out.println("=== Clientes ativos ===");

        ArrayList<Cliente> clientes = clienteRepository.listarAtivos();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente ativo encontrado.");
            return;
        }

        for (Cliente cliente : clientes) {
            cliente.exibirDados();
        }
    }

    public static void buscarClientePorNome(Scanner scanner, ClienteRepository clienteRepository) {
        System.out.println();
        System.out.println("=== Buscar Cliente por Nome ===");

        System.out.println("Digite parte do nome:");
        String nomeBusca = scanner.nextLine();

        if (nomeBusca == null || nomeBusca.isBlank()) {
            System.out.println("Busca inválida.");
            return;
        }

        ArrayList<Cliente> clientes = clienteRepository.buscarPorNome(nomeBusca);

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return;
        }

        for (Cliente cliente : clientes) {
            cliente.exibirDados();
        }
    }
}