import java.util.ArrayList;
import java.util.Scanner;

public class ProfissionalService {

    private ArrayList<Profissional> profissionais;

    public ProfissionalService() {
        this.profissionais = new ArrayList<>();
    }

    public ArrayList<Profissional> getProfissionais() {
        return profissionais;
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
        profissionais.add(profissional);

        System.out.println("Profissional cadastrado com sucesso.");
    }

    public void listarTodos() {
        System.out.println();
        System.out.println("=== Lista de Profissionais ===");

        if (profissionais.isEmpty()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        for (int i = 0; i < profissionais.size(); i++) {
            System.out.println("Código: " + i);
            profissionais.get(i).exibirDados();
        }
    }

    public void listarAtivosDisponiveis() {
        System.out.println();
        System.out.println("=== Profissionais Ativos e Disponíveis ===");

        if (profissionais.isEmpty()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        boolean encontrou = false;

        for (int i = 0; i < profissionais.size(); i++) {
            Profissional profissional = profissionais.get(i);

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

        if (profissionais.isEmpty()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        System.out.println("Digite o nome do profissional:");
        String nomeBusca = scanner.nextLine();

        boolean encontrou = false;

        for (Profissional profissional : profissionais) {
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

        if (profissionais.isEmpty()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        listarTodos();

        System.out.println("Digite o código do profissional que deseja editar:");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        if (!codigoValido(codigo)) {
            System.out.println("Código inválido.");
            return;
        }

        Profissional profissional = profissionais.get(codigo);

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

        if (profissionais.isEmpty()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        listarTodos();

        System.out.println("Digite o código do profissional que deseja inativar:");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        if (!codigoValido(codigo)) {
            System.out.println("Código inválido.");
            return;
        }

        Profissional profissional = profissionais.get(codigo);
        profissional.inativar();

        System.out.println("Profissional inativado com sucesso.");
    }

    public Profissional buscarPorCodigo(int codigo) {
        if (!codigoValido(codigo)) {
            return null;
        }

        return profissionais.get(codigo);
    }

    public boolean codigoValido(int codigo) {
        return codigo >= 0 && codigo < profissionais.size();
    }

    public int contarTodos() {
        return profissionais.size();
    }

    public int contarAtivos() {
        int total = 0;

        for (Profissional profissional : profissionais) {
            if (profissional.isAtivo()) {
                total++;
            }
        }

        return total;
    }

    public int contarDisponiveis() {
        int total = 0;

        for (Profissional profissional : profissionais) {
            if (profissional.isDisponivel()) {
                total++;
            }
        }

        return total;
    }
}