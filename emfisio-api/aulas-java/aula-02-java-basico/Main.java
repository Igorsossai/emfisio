public class Main {
    public static void main(String[] args) {

        String nomeCliente = "Ana Paula";
        int idadeCliente = 42;
        String telefoneCliente = "(27) 99999-0000";
        boolean clienteAtivo = true;

        String nomeProfissional = "Elisangela Morozini";
        String especialidade = "Fisioterapia Pélvica";
        double percentualComissao = 40.0;

        String nomeServico = "Sessão de Fisioterapia";
        int duracaoMinutos = 60;
        double valorSessao = 150.00;

        int quantidadeSessoesPacote = 10;

        double valorComissao = valorSessao * percentualComissao / 100;
        double valorTotalPacote = quantidadeSessoesPacote * valorSessao;

        System.out.println("=====================================");
        System.out.println("        EMFISIO - AULA 2             ");
        System.out.println("=====================================");

        System.out.println();
        System.out.println("=== Cliente ===");
        System.out.println("Nome: " + nomeCliente);
        System.out.println("Idade: " + idadeCliente);
        System.out.println("Telefone: " + telefoneCliente);
        System.out.println("Ativo: " + clienteAtivo);

        System.out.println();
        System.out.println("=== Profissional ===");
        System.out.println("Nome: " + nomeProfissional);
        System.out.println("Especialidade: " + especialidade);
        System.out.println("Comissão: " + percentualComissao + "%");

        System.out.println();
        System.out.println("=== Serviço ===");
        System.out.println("Serviço: " + nomeServico);
        System.out.println("Duração: " + duracaoMinutos + " minutos");
        System.out.println("Valor da sessão: R$ " + valorSessao);

        System.out.println();
        System.out.println("=== Cálculos ===");
        System.out.println("Valor da comissão: R$ " + valorComissao);
        System.out.println("Quantidade de sessões no pacote: " + quantidadeSessoesPacote);
        System.out.println("Valor total do pacote: R$ " + valorTotalPacote);
    }
}