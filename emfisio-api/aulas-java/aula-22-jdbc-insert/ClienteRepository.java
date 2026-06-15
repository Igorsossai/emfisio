import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteRepository {

    public boolean cadastrar(Cliente cliente) {
        String sql = """
                INSERT INTO clientes (
                    nome,
                    idade,
                    telefone,
                    ativo
                )
                VALUES (?, ?, ?, ?)
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return false;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql)
            ) {
                comando.setString(1, cliente.getNome());
                comando.setInt(2, cliente.getIdade());
                comando.setString(3, cliente.getTelefone());
                comando.setBoolean(4, cliente.isAtivo());

                int linhasAfetadas = comando.executeUpdate();

                return linhasAfetadas > 0;
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao cadastrar cliente.");
            System.out.println("Mensagem: " + erro.getMessage());
            return false;
        }
    }

    public ArrayList<Cliente> listarTodos() {
        ArrayList<Cliente> clientes = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    nome,
                    idade,
                    telefone,
                    ativo
                FROM clientes
                ORDER BY nome
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return clientes;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql);
                    ResultSet resultado = comando.executeQuery()
            ) {
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String nome = resultado.getString("nome");
                    int idade = resultado.getInt("idade");
                    String telefone = resultado.getString("telefone");
                    boolean ativo = resultado.getBoolean("ativo");

                    Cliente cliente = new Cliente(id, nome, idade, telefone, ativo);
                    clientes.add(cliente);
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao listar clientes.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return clientes;
    }

    public ArrayList<Cliente> listarAtivos() {
        ArrayList<Cliente> clientes = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    nome,
                    idade,
                    telefone,
                    ativo
                FROM clientes
                WHERE ativo = 1
                ORDER BY nome
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return clientes;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql);
                    ResultSet resultado = comando.executeQuery()
            ) {
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String nome = resultado.getString("nome");
                    int idade = resultado.getInt("idade");
                    String telefone = resultado.getString("telefone");
                    boolean ativo = resultado.getBoolean("ativo");

                    Cliente cliente = new Cliente(id, nome, idade, telefone, ativo);
                    clientes.add(cliente);
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao listar clientes ativos.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return clientes;
    }

    public ArrayList<Cliente> buscarPorNome(String nomeBusca) {
        ArrayList<Cliente> clientes = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    nome,
                    idade,
                    telefone,
                    ativo
                FROM clientes
                WHERE nome LIKE ?
                ORDER BY nome
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return clientes;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql)
            ) {
                comando.setString(1, "%" + nomeBusca + "%");

                try (ResultSet resultado = comando.executeQuery()) {
                    while (resultado.next()) {
                        int id = resultado.getInt("id");
                        String nome = resultado.getString("nome");
                        int idade = resultado.getInt("idade");
                        String telefone = resultado.getString("telefone");
                        boolean ativo = resultado.getBoolean("ativo");

                        Cliente cliente = new Cliente(id, nome, idade, telefone, ativo);
                        clientes.add(cliente);
                    }
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao buscar cliente por nome.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return clientes;
    }
}