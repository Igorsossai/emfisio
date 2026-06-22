import java.util.ArrayList;

public class ServicoService {

    private ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public boolean cadastrar(
            String nome,
            int duracaoMinutos,
            double valor
    ) {
        if (!dadosValidos(nome, duracaoMinutos, valor)) {
            return false;
        }

        if (servicoRepository.existePorNome(nome)) {
            System.out.println("Já existe um serviço com este nome.");
            return false;
        }

        Servico servico = new Servico(
                nome,
                duracaoMinutos,
                valor,
                true
        );

        return servicoRepository.cadastrar(servico);
    }

    public boolean editar(
            int id,
            String nome,
            int duracaoMinutos,
            double valor
    ) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return false;
        }

        if (!dadosValidos(nome, duracaoMinutos, valor)) {
            return false;
        }

        Servico servicoExistente = servicoRepository.buscarPorId(id);

        if (servicoExistente == null) {
            System.out.println("Serviço não encontrado.");
            return false;
        }

        return servicoRepository.editar(
                id,
                nome,
                duracaoMinutos,
                valor
        );
    }

    public boolean inativar(int id) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return false;
        }

        Servico servico = servicoRepository.buscarPorId(id);

        if (servico == null) {
            System.out.println("Serviço não encontrado.");
            return false;
        }

        if (!servico.isAtivo()) {
            System.out.println("Este serviço já está inativo.");
            return false;
        }

        return servicoRepository.inativar(id);
    }

    public boolean reativar(int id) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return false;
        }

        Servico servico = servicoRepository.buscarPorId(id);

        if (servico == null) {
            System.out.println("Serviço não encontrado.");
            return false;
        }

        if (servico.isAtivo()) {
            System.out.println("Este serviço já está ativo.");
            return false;
        }

        return servicoRepository.reativar(id);
    }

    public Servico buscarPorId(int id) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return null;
        }

        return servicoRepository.buscarPorId(id);
    }

    public ArrayList<Servico> buscarPorNome(String nomeBusca) {
        if (nomeBusca == null || nomeBusca.isBlank()) {
            System.out.println("Busca inválida.");
            return new ArrayList<>();
        }

        return servicoRepository.buscarPorNome(nomeBusca);
    }

    public ArrayList<Servico> listarTodos() {
        return servicoRepository.listarTodos();
    }

    public ArrayList<Servico> listarAtivos() {
        return servicoRepository.listarAtivos();
    }

    public int contarAtivos() {
        return servicoRepository.contarAtivos();
    }

    private boolean dadosValidos(
            String nome,
            int duracaoMinutos,
            double valor
    ) {
        if (nome == null || nome.isBlank()) {
            System.out.println("Nome inválido. O nome do serviço não pode ser vazio.");
            return false;
        }

        if (duracaoMinutos <= 0) {
            System.out.println("Duração inválida. A duração deve ser maior que zero.");
            return false;
        }

        if (valor < 0) {
            System.out.println("Valor inválido. O valor não pode ser negativo.");
            return false;
        }

        return true;
    }
}