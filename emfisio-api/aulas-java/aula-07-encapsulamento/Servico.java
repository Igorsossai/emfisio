public class Servico {

    private String nome;
    private int duracaoMinutos;
    private double valor;
    private boolean ativo;

    public Servico(String nome, int duracaoMinutos, double valor, boolean ativo) {
        setNome(nome);
        setDuracaoMinutos(duracaoMinutos);
        setValor(valor);
        setAtivo(ativo);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        } else {
            System.out.println("Nome inválido. O nome do serviço não pode ser vazio.");
        }
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        if (duracaoMinutos > 0) {
            this.duracaoMinutos = duracaoMinutos;
        } else {
            System.out.println("Duração inválida. A duração deve ser maior que zero.");
        }
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        if (valor >= 0) {
            this.valor = valor;
        } else {
            System.out.println("Valor inválido. O valor do serviço não pode ser negativo.");
        }
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean podeSerAgendado() {
        return ativo;
    }

    public void exibirDados() {
        System.out.println("=== Serviço ===");
        System.out.println("Nome: " + nome);
        System.out.println("Duração: " + duracaoMinutos + " minutos");
        System.out.println("Valor: R$ " + valor);
        System.out.println("Ativo: " + ativo);
    }
}