public class Profissional {

    String nome;
    String especialidade;
    double percentualComissao;
    boolean disponivel;

    public Profissional(String nome, String especialidade, double percentualComissao, boolean disponivel) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.percentualComissao = percentualComissao;
        this.disponivel = disponivel;
    }

    public void exibirDados() {
        System.out.println("=== Profissional ===");
        System.out.println("Nome: " + nome);
        System.out.println("Especialidade: " + especialidade);
        System.out.println("Comissão: " + percentualComissao + "%");
        System.out.println("Disponível: " + disponivel);
    }

    public boolean podeAtender() {
        return disponivel;
    }

}