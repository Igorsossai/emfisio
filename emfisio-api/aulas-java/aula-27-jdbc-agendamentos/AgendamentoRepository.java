import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AgendamentoRepository {

    public boolean cadastrar(Agendamento agendamento) {
        String sql = """
                INSERT INTO agendamentos (
                    cliente_id,
                    profissional_id,
                    servico_id,
                    status_agendamento,
                    status_pagamento,
                    horas_antes_atendimento
                )
                VALUES (?, ?, ?, ?, ?, ?)
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
                comando.setInt(1, agendamento.getClienteId());
                comando.setInt(2, agendamento.getProfissionalId());
                comando.setInt(3, agendamento.getServicoId());
                comando.setString(4, agendamento.getStatusAgendamento());
                comando.setString(5, agendamento.getStatusPagamento());
                comando.setInt(6, agendamento.getHorasAntesAtendimento());

                int linhasAfetadas = comando.executeUpdate();

                return linhasAfetadas > 0;
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao cadastrar agendamento.");
            System.out.println("Mensagem: " + erro.getMessage());
            return false;
        }
    }

    public boolean atualizarStatus(int id, String novoStatus) {
        String sql = """
                UPDATE agendamentos
                SET status_agendamento = ?
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
                comando.setString(1, novoStatus);
                comando.setInt(2, id);

                int linhasAfetadas = comando.executeUpdate();

                return linhasAfetadas > 0;
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao atualizar status do agendamento.");
            System.out.println("Mensagem: " + erro.getMessage());
            return false;
        }
    }

    public boolean cancelar(int id) {
        String sql = """
                UPDATE agendamentos
                SET status_agendamento = 'CANCELADO'
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
            System.out.println("Erro ao cancelar agendamento.");
            System.out.println("Mensagem: " + erro.getMessage());
            return false;
        }
    }

    public Agendamento buscarPorId(int idBusca) {
        String sql = """
                SELECT
                    a.id AS agendamento_id,
                    c.id AS cliente_id,
                    p.id AS profissional_id,
                    s.id AS servico_id,
                    c.nome AS cliente,
                    p.nome AS profissional,
                    s.nome AS servico,
                    s.valor AS valor_servico,
                    a.status_agendamento,
                    a.status_pagamento,
                    a.horas_antes_atendimento
                FROM agendamentos a
                INNER JOIN clientes c ON c.id = a.cliente_id
                INNER JOIN profissionais p ON p.id = a.profissional_id
                INNER JOIN servicos s ON s.id = a.servico_id
                WHERE a.id = ?
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
                        return criarAgendamentoDoResultado(resultado);
                    }
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao buscar agendamento por ID.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return null;
    }

    public ArrayList<Agendamento> listarTodos() {
        ArrayList<Agendamento> agendamentos = new ArrayList<>();

        String sql = """
                SELECT
                    a.id AS agendamento_id,
                    c.id AS cliente_id,
                    p.id AS profissional_id,
                    s.id AS servico_id,
                    c.nome AS cliente,
                    p.nome AS profissional,
                    s.nome AS servico,
                    s.valor AS valor_servico,
                    a.status_agendamento,
                    a.status_pagamento,
                    a.horas_antes_atendimento
                FROM agendamentos a
                INNER JOIN clientes c ON c.id = a.cliente_id
                INNER JOIN profissionais p ON p.id = a.profissional_id
                INNER JOIN servicos s ON s.id = a.servico_id
                ORDER BY a.id DESC
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return agendamentos;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql);
                    ResultSet resultado = comando.executeQuery()
            ) {
                while (resultado.next()) {
                    Agendamento agendamento = criarAgendamentoDoResultado(resultado);
                    agendamentos.add(agendamento);
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao listar agendamentos.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return agendamentos;
    }

    public ArrayList<Agendamento> listarPorStatus(String status) {
        ArrayList<Agendamento> agendamentos = new ArrayList<>();

        String sql = """
                SELECT
                    a.id AS agendamento_id,
                    c.id AS cliente_id,
                    p.id AS profissional_id,
                    s.id AS servico_id,
                    c.nome AS cliente,
                    p.nome AS profissional,
                    s.nome AS servico,
                    s.valor AS valor_servico,
                    a.status_agendamento,
                    a.status_pagamento,
                    a.horas_antes_atendimento
                FROM agendamentos a
                INNER JOIN clientes c ON c.id = a.cliente_id
                INNER JOIN profissionais p ON p.id = a.profissional_id
                INNER JOIN servicos s ON s.id = a.servico_id
                WHERE a.status_agendamento = ?
                ORDER BY a.id DESC
                """;

        try {
            Connection conexao = ConnectionFactory.conectar();

            if (conexao == null) {
                return agendamentos;
            }

            try (
                    conexao;
                    PreparedStatement comando = conexao.prepareStatement(sql)
            ) {
                comando.setString(1, status);

                try (ResultSet resultado = comando.executeQuery()) {
                    while (resultado.next()) {
                        Agendamento agendamento = criarAgendamentoDoResultado(resultado);
                        agendamentos.add(agendamento);
                    }
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao listar agendamentos por status.");
            System.out.println("Mensagem: " + erro.getMessage());
        }

        return agendamentos;
    }

    private Agendamento criarAgendamentoDoResultado(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("agendamento_id");
        int clienteId = resultado.getInt("cliente_id");
        int profissionalId = resultado.getInt("profissional_id");
        int servicoId = resultado.getInt("servico_id");
        String clienteNome = resultado.getString("cliente");
        String profissionalNome = resultado.getString("profissional");
        String servicoNome = resultado.getString("servico");
        double valorServico = resultado.getDouble("valor_servico");
        String statusAgendamento = resultado.getString("status_agendamento");
        String statusPagamento = resultado.getString("status_pagamento");
        int horasAntesAtendimento = resultado.getInt("horas_antes_atendimento");

        return new Agendamento(
                id,
                clienteId,
                profissionalId,
                servicoId,
                clienteNome,
                profissionalNome,
                servicoNome,
                valorServico,
                statusAgendamento,
                statusPagamento,
                horasAntesAtendimento
        );
    }
}