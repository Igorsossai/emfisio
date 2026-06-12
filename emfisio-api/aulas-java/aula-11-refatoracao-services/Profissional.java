public class Profissional {

    private String nome;
    private String especialidade;
    private double percentualComissao;
    private boolean disponivel;
    private boolean ativo;

    public Profissional(String nome, String especialidade, double percentualComissao, boolean disponivel, boolean ativo) {
        setNome(nome);
        setEspecialidade(especialidade);
        setPercentualComissao(percentualComissao);
        setDisponivel(disponivel);
        setAtivo(ativo);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        } else {
            System.out.println("Nome inválido. O nome do profissional não pode ser vazio.");
        }
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        if (especialidade != null && !especialidade.isBlank()) {
            this.especialidade = especialidade;
        } else {
            System.out.println("Especialidade inválida.");
        }
    }

    public double getPercentualComissao() {
        return percentualComissao;
    }

    public void setPercentualComissao(double percentualComissao) {
        if (percentualComissao >= 0 && percentualComissao <= 100) {
            this.percentualComissao = percentualComissao;
        } else {
            System.out.println("Percentual inválido. A comissão deve estar entre 0 e 100.");
        }
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean podeAtender() {
        return ativo && disponivel;
    }

    public void inativar() {
        this.ativo = false;
        this.disponivel = false;
    }

    public void exibirDados() {
        System.out.println("Nome: " + nome);
        System.out.println("Especialidade: " + especialidade);
        System.out.println("Comissão: " + percentualComissao + "%");
        System.out.println("Disponível: " + disponivel);
        System.out.println("Ativo: " + ativo);
        System.out.println("-----------------------------------");
    }
}