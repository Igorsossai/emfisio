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
                    buscarClientePorNome(scanner, clientes);
                    break;
                case 4:
                    editarCliente(scanner, clientes);
                    break;
                case 5:
                    inativarCliente(scanner, clientes);
                    break;
                case 6:
                    listarClientesAtivos(clientes);
                    break;

                case 7:
                    cadastrarProfissional(scanner, profissionais);
                    break;
                case 8:
                    listarProfissionais(profissionais);
                    break;
                case 9:
                    buscarProfissionalPorNome(scanner, profissionais);
                    break;
                case 10:
                    editarProfissional(scanner, profissionais);
                    break;
                case 11:
                    inativarProfissional(scanner, profissionais);
                    break;

                case 12:
                    cadastrarServico(scanner, servicos);
                    break;
                case 13:
                    listarServicos(servicos);
                    break;
                case 14:
                    buscarServicoPorNome(scanner, servicos);
                    break;
                case 15:
                    editarServico(scanner, servicos);
                    break;
                case 16:
                    inativarServico(scanner, servicos);
                    break;
                case 17:
                    listarServicosAtivos(servicos);
                    break;

                case 18:
                    criarAgendamento(scanner, clientes, profissionais, servicos, agendamentos);
                    break;
                case 19:
                    listarAgendamentos(agendamentos);
                    break;
                case 20:
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
        System.out.println("1  - Cadastrar cliente");
        System.out.println("2  - Listar clientes");
        System.out.println("3  - Buscar cliente por nome");
        System.out.println("4  - Editar cliente");
        System.out.println("5  - Inativar cliente");
        System.out.println("6  - Listar clientes ativos");
        System.out.println("-------------------------------------");
        System.out.println("7  - Cadastrar profissional");
        System.out.println("8  - Listar profissionais");
        System.out.println("9  - Buscar profissional por nome");
        System.out.println("10 - Editar profissional");
        System.out.println("11 - Inativar profissional");
        System.out.println("-------------------------------------");
        System.out.println("12 - Cadastrar serviço");
        System.out.println("13 - Listar serviços");
        System.out.println("14 - Buscar serviço por nome");
        System.out.println("15 - Editar serviço");
        System.out.println("16 - Inativar serviço");
        System.out.println("17 - Listar serviços ativos");
        System.out.println("-------------------------------------");
        System.out.println("18 - Criar agendamento");
        System.out.println("19 - Listar agendamentos");
        System.out.println("20 - Exibir resumo geral");
        System.out.println("0  - Sair");
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

        Cliente cliente = new Cliente(nome, idade, telefone, true);
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

    public static void buscarClientePorNome(Scanner scanner, ArrayList<Cliente> clientes) {
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

    public static void editarCliente(Scanner scanner, ArrayList<Cliente> clientes) {
        System.out.println();
        System.out.println("=== Editar Cliente ===");

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        listarClientes(clientes);

        System.out.println("Digite o código do cliente que deseja editar:");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        if (codigo < 0 || codigo >= clientes.size()) {
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

    public static void inativarCliente(Scanner scanner, ArrayList<Cliente> clientes) {
        System.out.println();
        System.out.println("=== Inativar Cliente ===");

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        listarClientes(clientes);

        System.out.println("Digite o código do cliente que deseja inativar:");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        if (codigo < 0 || codigo >= clientes.size()) {
            System.out.println("Código inválido.");
            return;
        }

        Cliente cliente = clientes.get(codigo);
        cliente.inativar();

        System.out.println("Cliente inativado com sucesso.");
    }

    public static void listarClientesAtivos(ArrayList<Cliente> clientes) {
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

        Profissional profissional = new Profissional(nome, especialidade, percentualComissao, disponivel, true);
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

    public static void buscarProfissionalPorNome(Scanner scanner, ArrayList<Profissional> profissionais) {
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

    public static void editarProfissional(Scanner scanner, ArrayList<Profissional> profissionais) {
        System.out.println();
        System.out.println("=== Editar Profissional ===");

        if (profissionais.isEmpty()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        listarProfissionais(profissionais);

        System.out.println("Digite o código do profissional que deseja editar:");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        if (codigo < 0 || codigo >= profissionais.size()) {
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

    public static void inativarProfissional(Scanner scanner, ArrayList<Profissional> profissionais) {
        System.out.println();
        System.out.println("=== Inativar Profissional ===");

        if (profissionais.isEmpty()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        listarProfissionais(profissionais);

        System.out.println("Digite o código do profissional que deseja inativar:");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        if (codigo < 0 || codigo >= profissionais.size()) {
            System.out.println("Código inválido.");
            return;
        }

        Profissional profissional = profissionais.get(codigo);
        profissional.inativar();

        System.out.println("Profissional inativado com sucesso.");
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
        scanner.nextLine();

        Servico servico = new Servico(nome, duracao, valor, true);
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

    public static void buscarServicoPorNome(Scanner scanner, ArrayList<Servico> servicos) {
        System.out.println();
        System.out.println("=== Buscar Serviço por Nome ===");

        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        System.out.println("Digite o nome do serviço:");
        String nomeBusca = scanner.nextLine();

        boolean encontrou = false;

        for (Servico servico : servicos) {
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

    public static void editarServico(Scanner scanner, ArrayList<Servico> servicos) {
        System.out.println();
        System.out.println("=== Editar Serviço ===");

        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        listarServicos(servicos);

        System.out.println("Digite o código do serviço que deseja editar:");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        if (codigo < 0 || codigo >= servicos.size()) {
            System.out.println("Código inválido.");
            return;
        }

        Servico servico = servicos.get(codigo);

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

    public static void inativarServico(Scanner scanner, ArrayList<Servico> servicos) {
        System.out.println();
        System.out.println("=== Inativar Serviço ===");

        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        listarServicos(servicos);

        System.out.println("Digite o código do serviço que deseja inativar:");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        if (codigo < 0 || codigo >= servicos.size()) {
            System.out.println("Código inválido.");
            return;
        }

        Servico servico = servicos.get(codigo);
        servico.inativar();

        System.out.println("Serviço inativado com sucesso.");
    }

    public static void listarServicosAtivos(ArrayList<Servico> servicos) {
        System.out.println();
        System.out.println("=== Serviços Ativos ===");

        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        boolean encontrou = false;

        for (int i = 0; i < servicos.size(); i++) {
            Servico servico = servicos.get(i);

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

        listarClientesAtivos(clientes);
        System.out.println("Digite o código do cliente:");
        int codigoCliente = scanner.nextInt();

        listarProfissionais(profissionais);
        System.out.println("Digite o código do profissional:");
        int codigoProfissional = scanner.nextInt();

        listarServicosAtivos(servicos);
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

        Cliente clienteSelecionado = clientes.get(codigoCliente);
        Profissional profissionalSelecionado = profissionais.get(codigoProfissional);
        Servico servicoSelecionado = servicos.get(codigoServico);

        if (!clienteSelecionado.isAtivo()) {
            System.out.println("Cliente inativo. Agendamento não permitido.");
            return;
        }

        if (!profissionalSelecionado.isAtivo()) {
            System.out.println("Profissional inativo. Agendamento não permitido.");
            return;
        }

        if (!profissionalSelecionado.isDisponivel()) {
            System.out.println("Profissional indisponível. Agendamento não permitido.");
            return;
        }

        if (!servicoSelecionado.isAtivo()) {
            System.out.println("Serviço inativo. Agendamento não permitido.");
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
                clienteSelecionado,
                profissionalSelecionado,
                servicoSelecionado,
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
        int totalProfissionaisAtivos = 0;
        int totalProfissionaisDisponiveis = 0;
        int totalServicosAtivos = 0;
        int totalComissoesGeradas = 0;

        for (Cliente cliente : clientes) {
            if (cliente.isAtivo()) {
                totalClientesAtivos++;
            }
        }

        for (Profissional profissional : profissionais) {
            if (profissional.isAtivo()) {
                totalProfissionaisAtivos++;
            }

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
        System.out.println("Profissionais ativos: " + totalProfissionaisAtivos);
        System.out.println("Profissionais disponíveis: " + totalProfissionaisDisponiveis);

        System.out.println("Total de serviços: " + servicos.size());
        System.out.println("Serviços ativos: " + totalServicosAtivos);

        System.out.println("Total de agendamentos: " + agendamentos.size());
        System.out.println("Agendamentos com comissão gerada: " + totalComissoesGeradas);
    }
}