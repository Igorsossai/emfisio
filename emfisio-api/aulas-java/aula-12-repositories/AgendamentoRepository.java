import java.util.ArrayList;

public class AgendamentoRepository {

    private ArrayList<Agendamento> agendamentos;

    public AgendamentoRepository() {
        this.agendamentos = new ArrayList<>();
    }

    public void salvar(Agendamento agendamento) {
        agendamentos.add(agendamento);
    }

    public ArrayList<Agendamento> listarTodos() {
        return agendamentos;
    }

    public Agendamento buscarPorCodigo(int codigo) {
        if (!codigoValido(codigo)) {
            return null;
        }

        return agendamentos.get(codigo);
    }

    public boolean codigoValido(int codigo) {
        return codigo >= 0 && codigo < agendamentos.size();
    }

    public int contarTodos() {
        return agendamentos.size();
    }

    public boolean estaVazio() {
        return agendamentos.isEmpty();
    }
}