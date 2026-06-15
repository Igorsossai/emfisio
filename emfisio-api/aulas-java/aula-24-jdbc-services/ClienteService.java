import java.util.ArrayList;

public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public boolean cadastrar(String nome, int idade, String telefone) {
        if (!dadosValidos(nome, idade, telefone)) {
            return false;
        }

        Cliente cliente = new Cliente(nome, idade, telefone, true);

        return clienteRepository.cadastrar(cliente);
    }

    public boolean editar(int id, String nome, int idade, String telefone) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return false;
        }

        if (!dadosValidos(nome, idade, telefone)) {
            return false;
        }

        Cliente clienteExistente = clienteRepository.buscarPorId(id);

        if (clienteExistente == null) {
            System.out.println("Cliente não encontrado.");
            return false;
        }

        return clienteRepository.editar(id, nome, idade, telefone);
    }

    public boolean inativar(int id) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return false;
        }

        Cliente cliente = clienteRepository.buscarPorId(id);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return false;
        }

        if (!cliente.isAtivo()) {
            System.out.println("Este cliente já está inativo.");
            return false;
        }

        return clienteRepository.inativar(id);
    }

    public boolean reativar(int id) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return false;
        }

        Cliente cliente = clienteRepository.buscarPorId(id);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return false;
        }

        if (cliente.isAtivo()) {
            System.out.println("Este cliente já está ativo.");
            return false;
        }

        return clienteRepository.reativar(id);
    }

    public Cliente buscarPorId(int id) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return null;
        }

        return clienteRepository.buscarPorId(id);
    }

    public ArrayList<Cliente> buscarPorNome(String nomeBusca) {
        if (nomeBusca == null || nomeBusca.isBlank()) {
            System.out.println("Busca inválida.");
            return new ArrayList<>();
        }

        return clienteRepository.buscarPorNome(nomeBusca);
    }

    public ArrayList<Cliente> listarTodos() {
        return clienteRepository.listarTodos();
    }

    public ArrayList<Cliente> listarAtivos() {
        return clienteRepository.listarAtivos();
    }

    private boolean dadosValidos(String nome, int idade, String telefone) {
        if (nome == null || nome.isBlank()) {
            System.out.println("Nome inválido. O nome não pode ser vazio.");
            return false;
        }

        if (idade <= 0) {
            System.out.println("Idade inválida. A idade deve ser maior que zero.");
            return false;
        }

        if (telefone == null || telefone.isBlank()) {
            System.out.println("Telefone inválido. O telefone não pode ser vazio.");
            return false;
        }

        return true;
    }
}