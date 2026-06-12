public class Cliente {

    String nome;
    int idade;
    String telefone;
    boolean ativo;

    public Cliente(String nome, int idade, String telefone, boolean ativo) {
        this.nome = nome;
        this.idade = idade;
        this.telefone = telefone;
        this.ativo = ativo;
    }

    public void exibirDados() {
        System.out.println("=== Cliente ===");
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Telefone: " + telefone);
        System.out.println("Ativo: " + ativo);
    }

    public boolean podeAgendar() {
        return ativo;
    }

}