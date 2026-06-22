public class Profissional {

    private int id;
    private String nome;
    private String especialidade;
    private double percentualComissao;
    private boolean disponivel;
    private boolean ativo;

    public Profissional(
            int id,
            String nome,
            String especialidade,
            double percentualComissao,
            boolean disponivel,
            boolean ativo
    ) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
        this.percentualComissao = percentualComissao;
        this.disponivel = disponivel;
        this.ativo = ativo;
    }

    public Profissional(
            String nome,
            String especialidade,
            double percentualComissao,
            boolean disponivel,
            boolean ativo
    ) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.percentualComissao = percentualComissao;
        this.disponivel = disponivel;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public double getPercentualComissao() {
        return percentualComissao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void exibirDados() {
        System.out.println("Código: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Especialidade: " + especialidade);
        System.out.println("Comissão: " + percentualComissao + "%");
        System.out.println("Disponível: " + disponivel);
        System.out.println("Ativo: " + ativo);
        System.out.println("-----------------------------------");
    }
}