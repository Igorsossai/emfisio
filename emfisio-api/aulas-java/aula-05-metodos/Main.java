import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        exibirCabecalho();

        System.out.println("Digite o nome do cliente:");
        String nomeCliente = scanner.nextLine();

        System.out.println("O cliente está ativo? Digite true ou false:");
        boolean clienteAtivo = scanner.nextBoolean();
        scanner.nextLine();

        System.out.println("Digite o nome do profissional:");
        String nomeProfissional = scanner.nextLine();

        System.out.println("O profissional está disponível? Digite true ou false:");
        boolean profissionalDisponivel = scanner.nextBoolean();
        scanner.nextLine();

        System.out.println("Digite o nome do serviço:");
        String nomeServico = scanner.nextLine();

        System.out.println("Digite o valor da sessão:");
        double valorSessao = scanner.nextDouble();

        System.out.println("Digite o percentual de comissão:");
        double percentualComissao = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Digite o status do atendimento: AGENDADO, CONFIRMADO, REALIZADO, CANCELADO ou FALTOU");
        String statusAgendamento = scanner.nextLine().toUpperCase();

        System.out.println("Digite o status do pagamento: PAGO, PENDENTE, CANCELADO ou ESTORNADO");
        String statusPagamento = scanner.nextLine().toUpperCase();

        System.out.println("Digite quantas horas faltam para o atendimento:");
        int horasAntesAtendimento = scanner.nextInt();

        exibirResumo(
                nomeCliente,
                nomeProfissional,
                nomeServico,
                valorSessao,
                statusAgendamento,
                statusPagamento
        );

        validarAgendamento(clienteAtivo, profissionalDisponivel);
        validarStatusAtendimento(statusAgendamento);
        validarCancelamento(horasAntesAtendimento);
        validarPagamento(statusPagamento);
        processarComissao(statusPagamento, statusAgendamento, valorSessao, percentualComissao);

        scanner.close();
    }

    public static void exibirCabecalho() {
        System.out.println("=====================================");
        System.out.println("        EMFISIO - AULA 5             ");
        System.out.println("      Métodos aplicados ao Java      ");
        System.out.println("=====================================");
        System.out.println();
    }

    public static void exibirResumo(
            String nomeCliente,
            String nomeProfissional,
            String nomeServico,
            double valorSessao,
            String statusAgendamento,
            String statusPagamento
    ) {
        System.out.println();
        System.out.println("=====================================");
        System.out.println("        RESUMO DO ATENDIMENTO        ");
        System.out.println("=====================================");
        System.out.println("Cliente: " + nomeCliente);
        System.out.println("Profissional: " + nomeProfissional);
        System.out.println("Serviço: " + nomeServico);
        System.out.println("Valor da sessão: R$ " + valorSessao);
        System.out.println("Status do atendimento: " + statusAgendamento);
        System.out.println("Status do pagamento: " + statusPagamento);
    }

    public static boolean podeAgendar(boolean clienteAtivo, boolean profissionalDisponivel) {
        return clienteAtivo && profissionalDisponivel;
    }

    public static void validarAgendamento(boolean clienteAtivo, boolean profissionalDisponivel) {
        System.out.println();
        System.out.println("=== Validação do Agendamento ===");

        if (podeAgendar(clienteAtivo, profissionalDisponivel)) {
            System.out.println("Agendamento permitido.");
        } else {
            System.out.println("Agendamento não permitido.");

            if (!clienteAtivo) {
                System.out.println("Motivo: cliente inativo.");
            }

            if (!profissionalDisponivel) {
                System.out.println("Motivo: profissional indisponível.");
            }
        }
    }

    public static void validarStatusAtendimento(String statusAgendamento) {
        System.out.println();
        System.out.println("=== Status do Atendimento ===");

        if (statusAgendamento.equals("AGENDADO")) {
            System.out.println("Atendimento agendado.");
        } else if (statusAgendamento.equals("CONFIRMADO")) {
            System.out.println("Atendimento confirmado.");
        } else if (statusAgendamento.equals("REALIZADO")) {
            System.out.println("Atendimento realizado.");
        } else if (statusAgendamento.equals("CANCELADO")) {
            System.out.println("Atendimento cancelado.");
        } else if (statusAgendamento.equals("FALTOU")) {
            System.out.println("Cliente faltou ao atendimento.");
            System.out.println("Regra: falta não gera cobrança nem comissão.");
        } else {
            System.out.println("Status de atendimento inválido.");
        }
    }

    public static boolean podeCancelar(int horasAntesAtendimento) {
        return horasAntesAtendimento >= 24;
    }

    public static void validarCancelamento(int horasAntesAtendimento) {
        System.out.println();
        System.out.println("=== Validação de Cancelamento ===");

        if (podeCancelar(horasAntesAtendimento)) {
            System.out.println("Cancelamento permitido.");
        } else {
            System.out.println("Cancelamento não permitido.");
            System.out.println("Motivo: antecedência inferior a 24 horas.");
        }
    }

    public static void validarPagamento(String statusPagamento) {
        System.out.println();
        System.out.println("=== Validação de Pagamento ===");

        if (statusPagamento.equals("PAGO")) {
            System.out.println("Pagamento confirmado.");
        } else if (statusPagamento.equals("PENDENTE")) {
            System.out.println("Pagamento pendente.");
        } else if (statusPagamento.equals("CANCELADO")) {
            System.out.println("Pagamento cancelado.");
        } else if (statusPagamento.equals("ESTORNADO")) {
            System.out.println("Pagamento estornado.");
        } else {
            System.out.println("Status de pagamento inválido.");
        }
    }

    public static boolean deveGerarComissao(String statusPagamento, String statusAgendamento) {
        return statusPagamento.equals("PAGO") && statusAgendamento.equals("REALIZADO");
    }

    public static double calcularComissao(double valorSessao, double percentualComissao) {
        return valorSessao * percentualComissao / 100;
    }

    public static void processarComissao(
            String statusPagamento,
            String statusAgendamento,
            double valorSessao,
            double percentualComissao
    ) {
        System.out.println();
        System.out.println("=== Cálculo de Comissão ===");

        if (deveGerarComissao(statusPagamento, statusAgendamento)) {
            double valorComissao = calcularComissao(valorSessao, percentualComissao);

            System.out.println("Comissão gerada com sucesso.");
            System.out.println("Percentual de comissão: " + percentualComissao + "%");
            System.out.println("Valor da comissão: R$ " + valorComissao);
        } else {
            System.out.println("Comissão não gerada.");

            if (!statusPagamento.equals("PAGO")) {
                System.out.println("Motivo: pagamento não confirmado.");
            }

            if (!statusAgendamento.equals("REALIZADO")) {
                System.out.println("Motivo: atendimento não realizado.");
            }
        }
    }
}