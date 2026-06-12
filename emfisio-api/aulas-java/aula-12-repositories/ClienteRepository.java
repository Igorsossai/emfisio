import java.util.ArrayList;

public class ClienteRepository {

    private ArrayList<Cliente> clientes;

    public ClienteRepository() {
        this.clientes = new ArrayList<>();
    }

    public void salvar(Cliente cliente) {
        clientes.add(cliente);
    }

    public ArrayList<Cliente> listarTodos() {
        return clientes;
    }

    public Cliente buscarPorCodigo(int codigo) {
        if (!codigoValido(codigo)) {
            return null;
        }

        return clientes.get(codigo);
    }

    public boolean codigoValido(int codigo) {
        return codigo >= 0 && codigo < clientes.size();
    }

    public int contarTodos() {
        return clientes.size();
    }

    public boolean estaVazio() {
        return clientes.isEmpty();
    }
}