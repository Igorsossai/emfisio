import java.util.ArrayList;
import java.util.Scanner;

public class ClienteService {

    private ArrayList<Cliente> clientes;

    public ClienteService() {
        this.clientes = new ArrayList<>();
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void cadastrar(Scanner scanner) {
        System.out.println();
        System.out.println("=== Cadastro de Cliente ===");

        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();

        System.out.println("Digite a idade:");
        int idade = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o telefone:");
        String telefone = scanner.nextLine();

        Cliente cliente = new Cliente(nome, idade, telefone, true);
        clientes.add(cliente);

        System.out.println("Cliente cadastrado com sucesso.");
    }

    public void listarTodos() {
        System.out.println();
        System.out.println("=== Lista de Clientes ===");

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (int i = 0; i < clientes.size(); i++) {
            System.out.println("Código: " + i);
            clientes.get(i).exibirDados();
        }
    }

    public void listarAtivos() {
        System.out.println();
        System.out.println("=== Clientes Ativos ===");

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        boolean encontrou = false;

        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);

            if (cliente.isAtivo()) {
                System.out.println("Código: " + i);
                cliente.exibirDados();
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum cliente ativo encontrado.");
        }
    }

    public void buscarPorNome(Scanner scanner) {
        System.out.println();
        System.out.println("=== Buscar Cliente por Nome ===");

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("Digite o nome do cliente:");
        String nomeBusca = scanner.nextLine();

        boolean encontrou = false;

        for (Cliente cliente : clientes) {
            if (cliente.getNome().equalsIgnoreCase(nomeBusca)) {
                System.out.println("Cliente encontrado:");
                cliente.exibirDados();
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Cliente não encontrado.");
        }
    }

    public void editar(Scanner scanner) {
        System.out.println();
        System.out.println("=== Editar Cliente ===");

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        listarTodos();

        System.out.println("Digite o código do cliente que deseja editar:");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        if (!codigoValido(codigo)) {
            System.out.println("Código inválido.");
            return;
        }

        Cliente cliente = clientes.get(codigo);

        System.out.println("Digite o novo nome:");
        String novoNome = scanner.nextLine();

        System.out.println("Digite a nova idade:");
        int novaIdade = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o novo telefone:");
        String novoTelefone = scanner.nextLine();

        cliente.setNome(novoNome);
        cliente.setIdade(novaIdade);
        cliente.setTelefone(novoTelefone);

        System.out.println("Cliente atualizado com sucesso.");
    }

    public void inativar(Scanner scanner) {
        System.out.println();
        System.out.println("=== Inativar Cliente ===");

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        listarTodos();

        System.out.println("Digite o código do cliente que deseja inativar:");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        if (!codigoValido(codigo)) {
            System.out.println("Código inválido.");
            return;
        }

        Cliente cliente = clientes.get(codigo);
        cliente.inativar();

        System.out.println("Cliente inativado com sucesso.");
    }

    public Cliente buscarPorCodigo(int codigo) {
        if (!codigoValido(codigo)) {
            return null;
        }

        return clientes.get(codigo);
    }

    public boolean codigoValido(int codigo) {
        return codigo >= 0 && codigo < clientes.size();
    }

    public int contarAtivos() {
        int total = 0;

        for (Cliente cliente : clientes) {
            if (cliente.isAtivo()) {
                total++;
            }
        }

        return total;
    }

    public int contarTodos() {
        return clientes.size();
    }
}