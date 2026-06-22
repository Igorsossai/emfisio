import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServicoRepository {

    public boolean cadastrar(Servico servico) {
        String sql = """
                INSERT INTO servicos (
                    nome,
                    duracao_minutos,
                    valor,
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
                comando.setString(1, servico.getNome());
                comando.setInt(2, servico.getDuracaoMinutos());
                comando.setDouble(3, servico.getValor());
                comando.setBoolean(4, servico.isAtivo());

                int linhasAfetadas = comando.executeUpdate();

                return linhasAfetadas > 0;
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao cadastrar serviço.");
            System.out.println("Mensagem: " + erro.getMessage());
            return false;
        }
    }

    public boolean editar(
            int id,
            String nome,
            int duracaoMinutos,
            double valor
    ) {
        String sql = """
                UPDATE servicos
                SET
                    nome = ?,
                    duracao_minutos = ?,
                    valor = ?
                WHERE id = ?
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
                comando.setString(1, nome);
                comando.setInt(2, duracaoMinutos);
                comando.setDouble(3, valor);
                comando.setInt(4, id);

                int linhasAfetadas = comando.executeUpdate();

                return linhasAfetadas > 0;
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao editar serviço.");
            System.out.println("Mensagem: " + erro.getMessage());
            return false;
        }
    }

    public boolean inativar(int id) {
        String sql = """
                UPDATE servicos
                SET ativo = 0
                WHERE id = ?
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
                comando.setInt(1, id);

                int linhasAfetadas = comando.executeUpdate();

                return linhasAfetadas > 0;
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao inativar serviço.");
            System.out.println("Mensagem: " + erro.getMessage());
            return false;
        }
    }

    public boolean reativar(int id) {
        String sql = """
                UPDATE servicos
                SET ativo = 1
                WHERE id = ?
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
                comando.setInt(1, id);

                int linhasAfetadas = comando.executeUpdate();

                return linhasAfetadas > 0;
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao reativar serviço.");
            System.out.println("Mensagem: " + erro.getMessage());
            return false;
        }
    }

    public Servico buscarPorId(int idBusca) {
        String sql = """
                SELECT
                    id,
                    nome,
                    duracao_minutos,
                    valor,
                    ativo
                FROM servicos
                WHERE id = ?
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return null;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql)
            ) {
                comando.setInt(1, idBusca);

                try (ResultSet resultado = comando.executeQuery()) {
                    if (resultado.next()) {
                        return criarServicoDoResultado(resultado);
                    }
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao buscar serviço por ID.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return null;
    }

    public ArrayList<Servico> listarTodos() {
        ArrayList<Servico> servicos = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    nome,
                    duracao_minutos,
                    valor,
                    ativo
                FROM servicos
                ORDER BY nome
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return servicos;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql);
                    ResultSet resultado = comando.executeQuery()
            ) {
                while (resultado.next()) {
                    Servico servico = criarServicoDoResultado(resultado);
                    servicos.add(servico);
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao listar serviços.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return servicos;
    }

    public ArrayList<Servico> listarAtivos() {
        ArrayList<Servico> servicos = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    nome,
                    duracao_minutos,
                    valor,
                    ativo
                FROM servicos
                WHERE ativo = 1
                ORDER BY nome
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return servicos;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql);
                    ResultSet resultado = comando.executeQuery()
            ) {
                while (resultado.next()) {
                    Servico servico = criarServicoDoResultado(resultado);
                    servicos.add(servico);
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao listar serviços ativos.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return servicos;
    }

    public ArrayList<Servico> buscarPorNome(String nomeBusca) {
        ArrayList<Servico> servicos = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    nome,
                    duracao_minutos,
                    valor,
                    ativo
                FROM servicos
                WHERE nome LIKE ?
                ORDER BY nome
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return servicos;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql)
            ) {
                comando.setString(1, "%" + nomeBusca + "%");

                try (ResultSet resultado = comando.executeQuery()) {
                    while (resultado.next()) {
                        Servico servico = criarServicoDoResultado(resultado);
                        servicos.add(servico);
                    }
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao buscar serviço por nome.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return servicos;
    }

    public int contarAtivos() {
        String sql = """
                SELECT COUNT(*) AS total
                FROM servicos
                WHERE ativo = 1
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return 0;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql);
                    ResultSet resultado = comando.executeQuery()
            ) {
                if (resultado.next()) {
                    return resultado.getInt("total");
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao contar serviços ativos.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return 0;
    }

    public boolean existePorNome(String nome) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM servicos
                WHERE nome = ?
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
                comando.setString(1, nome);

                try (ResultSet resultado = comando.executeQuery()) {
                    if (resultado.next()) {
                        return resultado.getInt("total") > 0;
                    }
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao verificar serviço duplicado.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return false;
    }

    private Servico criarServicoDoResultado(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("id");
        String nome = resultado.getString("nome");
        int duracaoMinutos = resultado.getInt("duracao_minutos");
        double valor = resultado.getDouble("valor");
        boolean ativo = resultado.getBoolean("ativo");

        return new Servico(
                id,
                nome,
                duracaoMinutos,
                valor,
                ativo
        );
    }
}