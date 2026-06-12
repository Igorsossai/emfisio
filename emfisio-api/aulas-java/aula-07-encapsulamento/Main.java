public class Main {
    public static void main(String[] args) {

        Cliente cliente = new Cliente(
                "Ana Paula",
                42,
                "(27) 99999-0000",
                true
        );

        Profissional profissional = new Profissional(
                "Elisangela Morozini",
                "Fisioterapia Pélvica",
                40.0,
                true
        );

        Servico servico = new Servico(
                "Sessão de Fisioterapia",
                60,
                150.00,
                true
        );

        Agendamento agendamento = new Agendamento(
                cliente,
                profissional,
                servico,
                "realizado",
                "pago",
                30
        );

        System.out.println("=====================================");
        System.out.println("        EMFISIO - AULA 7             ");
        System.out.println(" Encapsulamento, Getters e Setters   ");
        System.out.println("=====================================");

        System.out.println();
        cliente.exibirDados();

        System.out.println();
        profissional.exibirDados();

        System.out.println();
        servico.exibirDados();

        System.out.println();
        agendamento.exibirResumo();

        agendamento.processarAgendamento();
        agendamento.processarCancelamento();
        agendamento.processarComissao();

        System.out.println();
        System.out.println("=== Teste de Proteção dos Dados ===");

        cliente.setIdade(-10);
        profissional.setPercentualComissao(150);
        servico.setValor(-200);

        System.out.println();
        System.out.println("=== Dados Após Tentativas Inválidas ===");
        cliente.exibirDados();

        System.out.println();
        profissional.exibirDados();

        System.out.println();
        servico.exibirDados();
  }
}