import java.sql.Connection;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("        EMFISIO - AULA 21            ");
        System.out.println("      Java + SQL Server com JDBC     ");
        System.out.println("=====================================");

        Connection conexao = ConnectionFactory.conectar();

        if (conexao != null) {
            System.out.println("Conexão realizada com sucesso!");

            try {
                conexao.close();
            } catch (Exception erro) {
                System.out.println("Erro ao fechar conexão.");
            }
        } else {
            System.out.println("Não foi possível conectar ao banco.");
            return;
        }

        ClienteRepository clienteRepository = new ClienteRepository();

        System.out.println();
        System.out.println("=== Clientes vindos do SQL Server ===");

        ArrayList<Cliente> clientes = clienteRepository.listarTodos();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
        } else {
            for (Cliente cliente : clientes) {
                cliente.exibirDados();
            }
        }

        System.out.println();
        System.out.println("=== Clientes ativos vindos do SQL Server ===");

        ArrayList<Cliente> clientesAtivos = clienteRepository.listarAtivos();

        if (clientesAtivos.isEmpty()) {
            System.out.println("Nenhum cliente ativo encontrado.");
        } else {
            for (Cliente cliente : clientesAtivos) {
                cliente.exibirDados();
            }
        }

        System.out.println();
        System.out.println("=== Busca de cliente por nome ===");

        ArrayList<Cliente> clientesBusca = clienteRepository.buscarPorNome("Ana");

        if (clientesBusca.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
        } else {
            for (Cliente cliente : clientesBusca) {
                cliente.exibirDados();
            }
        }
    }
}