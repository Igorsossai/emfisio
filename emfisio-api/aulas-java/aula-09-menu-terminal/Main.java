import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Profissional> profissionais = new ArrayList<>();
        ArrayList<Servico> servicos = new ArrayList<>();
        ArrayList<Agendamento> agendamentos = new ArrayList<>();

        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();

            System.out.println("Escolha uma opção:");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarCliente(scanner, clientes);
                    break;

                case 2:
                    listarClientes(clientes);
                    break;

                case 3:
                    cadastrarProfissional(scanner, profissionais);
                    break;

                case 4:
                    listarProfissionais(profissionais);
                    break;

                case 5:
                    cadastrarServico(scanner, servicos);
                    break;

                case 6:
                    listarServicos(servicos);
                    break;

                case 7:
                    criarAgendamento(scanner, clientes, profissionais, servicos, agendamentos);
                    break;

                case 8:
                    listarAgendamentos(agendamentos);
                    break;

                case 9:
                    exibirResumoGeral(clientes, profissionais, servicos, agendamentos);
                    break;

                case 0:
                    System.out.println("Encerrando o EMFISIO...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }

        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println();
        System.out.println("=====================================");
        System.out.println("              EMFISIO                ");
        System.out.println("=====================================");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Listar clientes");
        System.out.println("3 - Cadastrar profissional");
        System.out.println("4 - Listar profissionais");
        System.out.println("5 - Cadastrar serviço");
        System.out.println("6 - Listar serviços");
        System.out.println("7 - Criar agendamento");
        System.out.println("8 - Listar agendamentos");
        System.out.println("9 - Exibir resumo geral");
        System.out.println("0 - Sair");
        System.out.println("=====================================");
    }

    public static void cadastrarCliente(Scanner scanner, ArrayList<Cliente> clientes) {
        System.out.println();
        System.out.println("=== Cadastro de Cliente ===");

        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();

        System.out.println("Digite a idade:");
        int idade = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o telefone:");
        String telefone = scanner.nextLine();

        System.out.println("Cliente está ativo? Digite true ou false:");
        boolean ativo = scanner.nextBoolean();
        scanner.nextLine();

        Cliente cliente = new Cliente(nome, idade, telefone, ativo);
        clientes.add(cliente);

        System.out.println("Cliente cadastrado com sucesso.");
    }

    public static void listarClientes(ArrayList<Cliente> clientes) {
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

    public static void cadastrarProfissional(Scanner scanner, ArrayList<Profissional> profissionais) {
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

        Profissional profissional = new Profissional(nome, especialidade, percentualComissao, disponivel);
        profissionais.add(profissional);

        System.out.println("Profissional cadastrado com sucesso.");
    }

    public static void listarProfissionais(ArrayList<Profissional> profissionais) {
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

    public static void cadastrarServico(Scanner scanner, ArrayList<Servico> servicos) {
        System.out.println();
        System.out.println("=== Cadastro de Serviço ===");

        System.out.println("Digite o nome do serviço:");
        String nome = scanner.nextLine();

        System.out.println("Digite a duração em minutos:");
        int duracao = scanner.nextInt();

        System.out.println("Digite o valor do serviço:");
        double valor = scanner.nextDouble();

        System.out.println("Serviço está ativo? Digite true ou false:");
        boolean ativo = scanner.nextBoolean();
        scanner.nextLine();

        Servico servico = new Servico(nome, duracao, valor, ativo);
        servicos.add(servico);

        System.out.println("Serviço cadastrado com sucesso.");
    }

    public static void listarServicos(ArrayList<Servico> servicos) {
        System.out.println();
        System.out.println("=== Lista de Serviços ===");

        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        for (int i = 0; i < servicos.size(); i++) {
            System.out.println("Código: " + i);
            servicos.get(i).exibirDados();
        }
    }

    public static void criarAgendamento(
            Scanner scanner,
            ArrayList<Cliente> clientes,
            ArrayList<Profissional> profissionais,
            ArrayList<Servico> servicos,
            ArrayList<Agendamento> agendamentos
    ) {
        System.out.println();
        System.out.println("=== Criar Agendamento ===");

        if (clientes.isEmpty()) {
            System.out.println("Não é possível criar agendamento. Nenhum cliente cadastrado.");
            return;
        }

        if (profissionais.isEmpty()) {
            System.out.println("Não é possível criar agendamento. Nenhum profissional cadastrado.");
            return;
        }

        if (servicos.isEmpty()) {
            System.out.println("Não é possível criar agendamento. Nenhum serviço cadastrado.");
            return;
        }

        listarClientes(clientes);
        System.out.println("Digite o código do cliente:");
        int codigoCliente = scanner.nextInt();

        listarProfissionais(profissionais);
        System.out.println("Digite o código do profissional:");
        int codigoProfissional = scanner.nextInt();

        listarServicos(servicos);
        System.out.println("Digite o código do serviço:");
        int codigoServico = scanner.nextInt();
        scanner.nextLine();

        if (codigoCliente < 0 || codigoCliente >= clientes.size()) {
            System.out.println("Código de cliente inválido.");
            return;
        }

        if (codigoProfissional < 0 || codigoProfissional >= profissionais.size()) {
            System.out.println("Código de profissional inválido.");
            return;
        }

        if (codigoServico < 0 || codigoServico >= servicos.size()) {
            System.out.println("Código de serviço inválido.");
            return;
        }

        System.out.println("Digite o status do atendimento: AGENDADO, CONFIRMADO, REALIZADO, CANCELADO ou FALTOU");
        String statusAgendamento = scanner.nextLine();

        System.out.println("Digite o status do pagamento: PAGO, PENDENTE, CANCELADO ou ESTORNADO");
        String statusPagamento = scanner.nextLine();

        System.out.println("Digite quantas horas faltam para o atendimento:");
        int horasAntesAtendimento = scanner.nextInt();
        scanner.nextLine();

        Agendamento agendamento = new Agendamento(
                clientes.get(codigoCliente),
                profissionais.get(codigoProfissional),
                servicos.get(codigoServico),
                statusAgendamento,
                statusPagamento,
                horasAntesAtendimento
        );

        agendamentos.add(agendamento);

        System.out.println("Agendamento criado com sucesso.");
    }

    public static void listarAgendamentos(ArrayList<Agendamento> agendamentos) {
        System.out.println();
        System.out.println("=== Lista de Agendamentos ===");

        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento cadastrado.");
            return;
        }

        for (int i = 0; i < agendamentos.size(); i++) {
            System.out.println("Agendamento código: " + i);
            agendamentos.get(i).exibirResumo();
        }
    }

    public static void exibirResumoGeral(
            ArrayList<Cliente> clientes,
            ArrayList<Profissional> profissionais,
            ArrayList<Servico> servicos,
            ArrayList<Agendamento> agendamentos
    ) {
        System.out.println();
        System.out.println("=== Resumo Geral do Sistema ===");

        int totalClientesAtivos = 0;
        int totalProfissionaisDisponiveis = 0;
        int totalServicosAtivos = 0;
        int totalComissoesGeradas = 0;

        for (Cliente cliente : clientes) {
            if (cliente.isAtivo()) {
                totalClientesAtivos++;
            }
        }

        for (Profissional profissional : profissionais) {
            if (profissional.isDisponivel()) {
                totalProfissionaisDisponiveis++;
            }
        }

        for (Servico servico : servicos) {
            if (servico.isAtivo()) {
                totalServicosAtivos++;
            }
        }

        for (Agendamento agendamento : agendamentos) {
            if (agendamento.deveGerarComissao()) {
                totalComissoesGeradas++;
            }
        }

        System.out.println("Total de clientes: " + clientes.size());
        System.out.println("Clientes ativos: " + totalClientesAtivos);

        System.out.println("Total de profissionais: " + profissionais.size());
        System.out.println("Profissionais disponíveis: " + totalProfissionaisDisponiveis);

        System.out.println("Total de serviços: " + servicos.size());
        System.out.println("Serviços ativos: " + totalServicosAtivos);

        System.out.println("Total de agendamentos: " + agendamentos.size());
        System.out.println("Agendamentos com comissão gerada: " + totalComissoesGeradas);
    }
}