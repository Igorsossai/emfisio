import java.util.Scanner;

public class ProfissionalService {

    private ProfissionalRepository profissionalRepository;

    public ProfissionalService(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    public void cadastrar(Scanner scanner) {
        System.out.println();
        System.out.println("=== Cadastro de Profissional ===");

        System.out.println("Digite o nome do profissional:");
        String nome = scanner.nextLine();

        System.out.println("Digite a especialidade:");
        String especialidade = scanner.nextLine();

        System.out.println("Digite o percentual de comissão:");
        double percentualComissao = scanner.nextDouble();

        System.out.println("Profissional está disponível? Digite true ou false:");
        boolean disponivel = scanner.nextBoolean();
        scanner.nextLine();

        Profissional profissional = new Profissional(nome, especialidade, percentualComissao, disponivel, true);

        profissionalRepository.salvar(profissional);

        System.out.println("Profissional cadastrado com sucesso.");
    }

    public void listarTodos() {
        System.out.println();
        System.out.println("=== Lista de Profissionais ===");

        if (profissionalRepository.estaVazio()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        for (int i = 0; i < profissionalRepository.listarTodos().size(); i++) {
            System.out.println("Código: " + i);
            profissionalRepository.listarTodos().get(i).exibirDados();
        }
    }

    public void listarAtivosDisponiveis() {
        System.out.println();
        System.out.println("=== Profissionais Ativos e Disponíveis ===");

        if (profissionalRepository.estaVazio()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        boolean encontrou = false;

        for (int i = 0; i < profissionalRepository.listarTodos().size(); i++) {
            Profissional profissional = profissionalRepository.listarTodos().get(i);

            if (profissional.isAtivo() && profissional.isDisponivel()) {
                System.out.println("Código: " + i);
                profissional.exibirDados();
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum profissional ativo e disponível encontrado.");
        }
    }

    public void buscarPorNome(Scanner scanner) {
        System.out.println();
        System.out.println("=== Buscar Profissional por Nome ===");

        if (profissionalRepository.estaVazio()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        System.out.println("Digite o nome do profissional:");
        String nomeBusca = scanner.nextLine();

        boolean encontrou = false;

        for (Profissional profissional : profissionalRepository.listarTodos()) {
            if (profissional.getNome().equalsIgnoreCase(nomeBusca)) {
                System.out.println("Profissional encontrado:");
                profissional.exibirDados();
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Profissional não encontrado.");
        }
    }

    public void editar(Scanner scanner) {
        System.out.println();
        System.out.println("=== Editar Profissional ===");

        if (profissionalRepository.estaVazio()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        listarTodos();

        System.out.println("Digite o código do profissional que deseja editar:");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        Profissional profissional = profissionalRepository.buscarPorCodigo(codigo);

        if (profissional == null) {
            System.out.println("Código inválido.");
            return;
        }

        System.out.println("Digite o novo nome:");
        String novoNome = scanner.nextLine();

        System.out.println("Digite a nova especialidade:");
        String novaEspecialidade = scanner.nextLine();

        System.out.println("Digite o novo percentual de comissão:");
        double novoPercentual = scanner.nextDouble();

        System.out.println("Profissional está disponível? Digite true ou false:");
        boolean novoDisponivel = scanner.nextBoolean();
        scanner.nextLine();

        profissional.setNome(novoNome);
        profissional.setEspecialidade(novaEspecialidade);
        profissional.setPercentualComissao(novoPercentual);
        profissional.setDisponivel(novoDisponivel);

        System.out.println("Profissional atualizado com sucesso.");
    }

    public void inativar(Scanner scanner) {
        System.out.println();
        System.out.println("=== Inativar Profissional ===");

        if (profissionalRepository.estaVazio()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        listarTodos();

        System.out.println("Digite o código do profissional que deseja inativar:");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        Profissional profissional = profissionalRepository.buscarPorCodigo(codigo);

        if (profissional == null) {
            System.out.println("Código inválido.");
            return;
        }

        profissional.inativar();

        System.out.println("Profissional inativado com sucesso.");
    }

    public Profissional buscarPorCodigo(int codigo) {
        return profissionalRepository.buscarPorCodigo(codigo);
    }

    public int contarTodos() {
        return profissionalRepository.contarTodos();
    }

    public int contarAtivos() {
        int total = 0;

        for (Profissional profissional : profissionalRepository.listarTodos()) {
            if (profissional.isAtivo()) {
                total++;
            }
        }

        return total;
    }

    public int contarDisponiveis() {
        int total = 0;

        for (Profissional profissional : profissionalRepository.listarTodos()) {
            if (profissional.isDisponivel()) {
                total++;
            }
        }

        return total;
    }
}