public class Cliente {

    private int id;
    private String nome;
    private int idade;
    private String telefone;
    private boolean ativo;

    public Cliente(int id, String nome, int idade, String telefone, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.telefone = telefone;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void exibirDados() {
        System.out.println("Código: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Telefone: " + telefone);
        System.out.println("Ativo: " + ativo);
        System.out.println("-----------------------------------");
    }
}