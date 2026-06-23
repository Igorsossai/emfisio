public class Servico {

    private int id;
    private String nome;
    private int duracaoMinutos;
    private double valor;
    private boolean ativo;

    public Servico(
            int id,
            String nome,
            int duracaoMinutos,
            double valor,
            boolean ativo
    ) {
        this.id = id;
        this.nome = nome;
        this.duracaoMinutos = duracaoMinutos;
        this.valor = valor;
        this.ativo = ativo;
    }

    public Servico(
            String nome,
            int duracaoMinutos,
            double valor,
            boolean ativo
    ) {
        this.nome = nome;
        this.duracaoMinutos = duracaoMinutos;
        this.valor = valor;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public double getValor() {
        return valor;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void exibirDados() {
        System.out.println("Código: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Duração: " + duracaoMinutos + " minutos");
        System.out.println("Valor: R$ " + valor);
        System.out.println("Ativo: " + ativo);
        System.out.println("-----------------------------------");
    }
}