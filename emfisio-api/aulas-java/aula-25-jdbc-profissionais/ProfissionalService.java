import java.util.ArrayList;

public class ProfissionalService {

    private ProfissionalRepository profissionalRepository;

    public ProfissionalService(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    public boolean cadastrar(
            String nome,
            String especialidade,
            double percentualComissao,
            boolean disponivel
    ) {
        if (!dadosValidos(nome, especialidade, percentualComissao)) {
            return false;
        }

        Profissional profissional = new Profissional(
                nome,
                especialidade,
                percentualComissao,
                disponivel,
                true
        );

        return profissionalRepository.cadastrar(profissional);
    }

    public boolean editar(
            int id,
            String nome,
            String especialidade,
            double percentualComissao,
            boolean disponivel
    ) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return false;
        }

        if (!dadosValidos(nome, especialidade, percentualComissao)) {
            return false;
        }

        Profissional profissionalExistente = profissionalRepository.buscarPorId(id);

        if (profissionalExistente == null) {
            System.out.println("Profissional não encontrado.");
            return false;
        }

        return profissionalRepository.editar(
                id,
                nome,
                especialidade,
                percentualComissao,
                disponivel
        );
    }

    public boolean inativar(int id) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return false;
        }

        Profissional profissional = profissionalRepository.buscarPorId(id);

        if (profissional == null) {
            System.out.println("Profissional não encontrado.");
            return false;
        }

        if (!profissional.isAtivo()) {
            System.out.println("Este profissional já está inativo.");
            return false;
        }

        return profissionalRepository.inativar(id);
    }

    public boolean reativar(int id) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return false;
        }

        Profissional profissional = profissionalRepository.buscarPorId(id);

        if (profissional == null) {
            System.out.println("Profissional não encontrado.");
            return false;
        }

        if (profissional.isAtivo()) {
            System.out.println("Este profissional já está ativo.");
            return false;
        }

        return profissionalRepository.reativar(id);
    }

    public boolean alterarDisponibilidade(int id, boolean disponivel) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return false;
        }

        Profissional profissional = profissionalRepository.buscarPorId(id);

        if (profissional == null) {
            System.out.println("Profissional não encontrado.");
            return false;
        }

        if (!profissional.isAtivo()) {
            System.out.println("Profissional inativo não pode ter disponibilidade alterada.");
            return false;
        }

        return profissionalRepository.alterarDisponibilidade(id, disponivel);
    }

    public Profissional buscarPorId(int id) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return null;
        }

        return profissionalRepository.buscarPorId(id);
    }

    public ArrayList<Profissional> buscarPorNome(String nomeBusca) {
        if (nomeBusca == null || nomeBusca.isBlank()) {
            System.out.println("Busca inválida.");
            return new ArrayList<>();
        }

        return profissionalRepository.buscarPorNome(nomeBusca);
    }

    public ArrayList<Profissional> listarTodos() {
        return profissionalRepository.listarTodos();
    }

    public ArrayList<Profissional> listarAtivos() {
        return profissionalRepository.listarAtivos();
    }

    public ArrayList<Profissional> listarAtivosDisponiveis() {
        return profissionalRepository.listarAtivosDisponiveis();
    }

    private boolean dadosValidos(
            String nome,
            String especialidade,
            double percentualComissao
    ) {
        if (nome == null || nome.isBlank()) {
            System.out.println("Nome inválido. O nome não pode ser vazio.");
            return false;
        }

        if (especialidade == null || especialidade.isBlank()) {
            System.out.println("Especialidade inválida. A especialidade não pode ser vazia.");
            return false;
        }

        if (percentualComissao < 0 || percentualComissao > 100) {
            System.out.println("Percentual de comissão inválido. Informe um valor entre 0 e 100.");
            return false;
        }

        return true;
    }
}