public class Main {
    public static void main(String[] args) {

        String nomeCliente = "Ana Paula";
        boolean clienteAtivo = true;

        String nomeProfissional = "Elisangela Morozini";
        boolean profissionalDisponivel = true;

        String nomeServico = "Sessão de Fisioterapia";
        double valorSessao = 150.00;

        String statusAgendamento = "REALIZADO";
        String statusPagamento = "PAGO";

        double percentualComissao = 40.0;
        double valorComissao = 0;

        int horasAntesAtendimento = 26;

        System.out.println("=====================================");
        System.out.println("        EMFISIO - AULA 3             ");
        System.out.println("=====================================");

        System.out.println();
        System.out.println("=== Dados do Agendamento ===");
        System.out.println("Cliente: " + nomeCliente);
        System.out.println("Profissional: " + nomeProfissional);
        System.out.println("Serviço: " + nomeServico);
        System.out.println("Valor da sessão: R$ " + valorSessao);

        System.out.println();
        System.out.println("=== Validação de Agendamento ===");

        if (clienteAtivo && profissionalDisponivel) {
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
        } else {
            System.out.println("Status de atendimento desconhecido.");
        }

        System.out.println();
        System.out.println("=== Validação de Cancelamento ===");

        if (horasAntesAtendimento >= 24) {
            System.out.println("Cancelamento permitido.");
        } else {
            System.out.println("Cancelamento não permitido.");
            System.out.println("Motivo: antecedência inferior a 24 horas.");
        }

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
            System.out.println("Status de pagamento desconhecido.");
        }

        System.out.println();
        System.out.println("=== Cálculo de Comissão ===");

        if (statusPagamento.equals("PAGO") && statusAgendamento.equals("REALIZADO")) {
            valorComissao = valorSessao * percentualComissao / 100;

            System.out.println("Comissão gerada.");
            System.out.println("Percentual: " + percentualComissao + "%");
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