import java.util.ArrayList;

public class ServicoRepository {

    private ArrayList<Servico> servicos;

    public ServicoRepository() {
        this.servicos = new ArrayList<>();
    }

    public void salvar(Servico servico) {
        servicos.add(servico);
    }

    public ArrayList<Servico> listarTodos() {
        return servicos;
    }

    public Servico buscarPorCodigo(int codigo) {
        if (!codigoValido(codigo)) {
            return null;
        }

        return servicos.get(codigo);
    }

    public boolean codigoValido(int codigo) {
        return codigo >= 0 && codigo < servicos.size();
    }

    public int contarTodos() {
        return servicos.size();
    }

    public boolean estaVazio() {
        return servicos.isEmpty();
    }
}