public class Servico {

    String nome;
    int duracaoMinutos;
    double valor;
    boolean ativo;

    public Servico(String nome, int duracaoMinutos, double valor, boolean ativo) {
        this.nome = nome;
        this.duracaoMinutos = duracaoMinutos;
        this.valor = valor;
        this.ativo = ativo;
    }

    public void exibirDados() {
        System.out.println("=== Serviço ===");
        System.out.println("Nome: " + nome);
        System.out.println("Duração: " + duracaoMinutos + " minutos");
        System.out.println("Valor: R$ " + valor);
        System.out.println("Ativo: " + ativo);
    }

    public boolean podeSerAgendado() {
        return ativo;
    }

}