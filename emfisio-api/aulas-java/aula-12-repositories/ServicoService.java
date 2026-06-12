import java.util.Scanner;

public class ServicoService {

    private ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public void cadastrar(Scanner scanner) {
        System.out.println();
        System.out.println("=== Cadastro de Serviço ===");

        System.out.println("Digite o nome do serviço:");
        String nome = scanner.nextLine();

        System.out.println("Digite a duração em minutos:");
        int duracao = scanner.nextInt();

        System.out.println("Digite o valor do serviço:");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        Servico servico = new Servico(nome, duracao, valor, true);

        servicoRepository.salvar(servico);

        System.out.println("Serviço cadastrado com sucesso.");
    }

    public void listarTodos() {
        System.out.println();
        System.out.println("=== Lista de Serviços ===");

        if (servicoRepository.estaVazio()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        for (int i = 0; i < servicoRepository.listarTodos().size(); i++) {
            System.out.println("Código: " + i);
            servicoRepository.listarTodos().get(i).exibirDados();
        }
    }

    public void listarAtivos() {
        System.out.println();
        System.out.println("=== Serviços Ativos ===");

        if (servicoRepository.estaVazio()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        boolean encontrou = false;

        for (int i = 0; i < servicoRepository.listarTodos().size(); i++) {
            Servico servico = servicoRepository.listarTodos().get(i);

            if (servico.isAtivo()) {
                System.out.println("Código: " + i);
                servico.exibirDados();
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum serviço ativo encontrado.");
        }
    }

    public void buscarPorNome(Scanner scanner) {
        System.out.println();
        System.out.println("=== Buscar Serviço por Nome ===");

        if (servicoRepository.estaVazio()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        System.out.println("Digite o nome do serviço:");
        String nomeBusca = scanner.nextLine();

        boolean encontrou = false;

        for (Servico servico : servicoRepository.listarTodos()) {
            if (servico.getNome().equalsIgnoreCase(nomeBusca)) {
                System.out.println("Serviço encontrado:");
                servico.exibirDados();
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Serviço não encontrado.");
        }
    }

    public void editar(Scanner scanner) {
        System.out.println();
        System.out.println("=== Editar Serviço ===");

        if (servicoRepository.estaVazio()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        listarTodos();

        System.out.println("Digite o código do serviço que deseja editar:");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        Servico servico = servicoRepository.buscarPorCodigo(codigo);

        if (servico == null) {
            System.out.println("Código inválido.");
            return;
        }

        System.out.println("Digite o novo nome:");
        String novoNome = scanner.nextLine();

        System.out.println("Digite a nova duração em minutos:");
        int novaDuracao = scanner.nextInt();

        System.out.println("Digite o novo valor:");
        double novoValor = scanner.nextDouble();
        scanner.nextLine();

        servico.setNome(novoNome);
        servico.setDuracaoMinutos(novaDuracao);
        servico.setValor(novoValor);

        System.out.println("Serviço atualizado com sucesso.");
    }

    public void inativar(Scanner scanner) {
        System.out.println();
        System.out.println("=== Inativar Serviço ===");

        if (servicoRepository.estaVazio()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        listarTodos();

        System.out.println("Digite o código do serviço que deseja inativar:");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        Servico servico = servicoRepository.buscarPorCodigo(codigo);

        if (servico == null) {
            System.out.println("Código inválido.");
            return;
        }

        servico.inativar();

        System.out.println("Serviço inativado com sucesso.");
    }

    public Servico buscarPorCodigo(int codigo) {
        return servicoRepository.buscarPorCodigo(codigo);
    }

    public int contarTodos() {
        return servicoRepository.contarTodos();
    }

    public int contarAtivos() {
        int total = 0;

        for (Servico servico : servicoRepository.listarTodos()) {
            if (servico.isAtivo()) {
                total++;
            }
        }

        return total;
    }
}