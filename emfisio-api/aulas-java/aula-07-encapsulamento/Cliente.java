public class Cliente {

    private String nome;
    private int idade;
    private String telefone;
    private boolean ativo;

    public Cliente(String nome, int idade, String telefone, boolean ativo) {
        setNome(nome);
        setIdade(idade);
        setTelefone(telefone);
        setAtivo(ativo);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        } else {
            System.out.println("Nome inválido. O nome do cliente não pode ser vazio.");
        }
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade > 0) {
            this.idade = idade;
        } else {
            System.out.println("Idade inválida. A idade deve ser maior que zero.");
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone != null && !telefone.isBlank()) {
            this.telefone = telefone;
        } else {
            System.out.println("Telefone inválido. O telefone não pode ser vazio.");
        }
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean podeAgendar() {
        return ativo;
    }

    public void exibirDados() {
        System.out.println("=== Cliente ===");
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Telefone: " + telefone);
        System.out.println("Ativo: " + ativo);
    }
}