import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProfissionalRepository {

    public boolean cadastrar(Profissional profissional) {
        String sql = """
                INSERT INTO profissionais (
                    nome,
                    especialidade,
                    percentual_comissao,
                    disponivel,
                    ativo
                )
                VALUES (?, ?, ?, ?, ?)
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
                comando.setString(1, profissional.getNome());
                comando.setString(2, profissional.getEspecialidade());
                comando.setDouble(3, profissional.getPercentualComissao());
                comando.setBoolean(4, profissional.isDisponivel());
                comando.setBoolean(5, profissional.isAtivo());

                int linhasAfetadas = comando.executeUpdate();

                return linhasAfetadas > 0;
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao cadastrar profissional.");
            System.out.println("Mensagem: " + erro.getMessage());
            return false;
        }
    }

    public boolean editar(
            int id,
            String nome,
            String especialidade,
            double percentualComissao,
            boolean disponivel
    ) {
        String sql = """
                UPDATE profissionais
                SET
                    nome = ?,
                    especialidade = ?,
                    percentual_comissao = ?,
                    disponivel = ?
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
                comando.setString(2, especialidade);
                comando.setDouble(3, percentualComissao);
                comando.setBoolean(4, disponivel);
                comando.setInt(5, id);

                int linhasAfetadas = comando.executeUpdate();

                return linhasAfetadas > 0;
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao editar profissional.");
            System.out.println("Mensagem: " + erro.getMessage());
            return false;
        }
    }

    public boolean inativar(int id) {
        String sql = """
                UPDATE profissionais
                SET
                    ativo = 0,
                    disponivel = 0
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
            System.out.println("Erro ao inativar profissional.");
            System.out.println("Mensagem: " + erro.getMessage());
            return false;
        }
    }

    public boolean reativar(int id) {
        String sql = """
                UPDATE profissionais
                SET
                    ativo = 1,
                    disponivel = 1
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
            System.out.println("Erro ao reativar profissional.");
            System.out.println("Mensagem: " + erro.getMessage());
            return false;
        }
    }

    public boolean alterarDisponibilidade(int id, boolean disponivel) {
        String sql = """
                UPDATE profissionais
                SET disponivel = ?
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
                comando.setBoolean(1, disponivel);
                comando.setInt(2, id);

                int linhasAfetadas = comando.executeUpdate();

                return linhasAfetadas > 0;
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao alterar disponibilidade do profissional.");
            System.out.println("Mensagem: " + erro.getMessage());
            return false;
        }
    }

    public Profissional buscarPorId(int idBusca) {
        String sql = """
                SELECT
                    id,
                    nome,
                    especialidade,
                    percentual_comissao,
                    disponivel,
                    ativo
                FROM profissionais
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
                        int id = resultado.getInt("id");
                        String nome = resultado.getString("nome");
                        String especialidade = resultado.getString("especialidade");
                        double percentualComissao = resultado.getDouble("percentual_comissao");
                        boolean disponivel = resultado.getBoolean("disponivel");
                        boolean ativo = resultado.getBoolean("ativo");

                        return new Profissional(
                                id,
                                nome,
                                especialidade,
                                percentualComissao,
                                disponivel,
                                ativo
                        );
                    }
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao buscar profissional por ID.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return null;
    }

    public ArrayList<Profissional> listarTodos() {
        ArrayList<Profissional> profissionais = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    nome,
                    especialidade,
                    percentual_comissao,
                    disponivel,
                    ativo
                FROM profissionais
                ORDER BY nome
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return profissionais;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql);
                    ResultSet resultado = comando.executeQuery()
            ) {
                while (resultado.next()) {
                    Profissional profissional = criarProfissionalDoResultado(resultado);
                    profissionais.add(profissional);
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao listar profissionais.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return profissionais;
    }

    public ArrayList<Profissional> listarAtivos() {
        ArrayList<Profissional> profissionais = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    nome,
                    especialidade,
                    percentual_comissao,
                    disponivel,
                    ativo
                FROM profissionais
                WHERE ativo = 1
                ORDER BY nome
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return profissionais;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql);
                    ResultSet resultado = comando.executeQuery()
            ) {
                while (resultado.next()) {
                    Profissional profissional = criarProfissionalDoResultado(resultado);
                    profissionais.add(profissional);
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao listar profissionais ativos.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return profissionais;
    }

    public ArrayList<Profissional> listarAtivosDisponiveis() {
        ArrayList<Profissional> profissionais = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    nome,
                    especialidade,
                    percentual_comissao,
                    disponivel,
                    ativo
                FROM profissionais
                WHERE ativo = 1
                AND disponivel = 1
                ORDER BY nome
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return profissionais;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql);
                    ResultSet resultado = comando.executeQuery()
            ) {
                while (resultado.next()) {
                    Profissional profissional = criarProfissionalDoResultado(resultado);
                    profissionais.add(profissional);
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao listar profissionais ativos e disponíveis.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return profissionais;
    }

    public ArrayList<Profissional> buscarPorNome(String nomeBusca) {
        ArrayList<Profissional> profissionais = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    nome,
                    especialidade,
                    percentual_comissao,
                    disponivel,
                    ativo
                FROM profissionais
                WHERE nome LIKE ?
                ORDER BY nome
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return profissionais;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql)
            ) {
                comando.setString(1, "%" + nomeBusca + "%");

                try (ResultSet resultado = comando.executeQuery()) {
                    while (resultado.next()) {
                        Profissional profissional = criarProfissionalDoResultado(resultado);
                        profissionais.add(profissional);
                    }
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao buscar profissional por nome.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return profissionais;
    }

    private Profissional criarProfissionalDoResultado(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("id");
        String nome = resultado.getString("nome");
        String especialidade = resultado.getString("especialidade");
        double percentualComissao = resultado.getDouble("percentual_comissao");
        boolean disponivel = resultado.getBoolean("disponivel");
        boolean ativo = resultado.getBoolean("ativo");

        return new Profissional(
                id,
                nome,
                especialidade,
                percentualComissao,
                disponivel,
                ativo
        );
    }
}