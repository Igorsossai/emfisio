import java.util.ArrayList;

public class ProfissionalRepository {

    private ArrayList<Profissional> profissionais;

    public ProfissionalRepository() {
        this.profissionais = new ArrayList<>();
    }

    public void salvar(Profissional profissional) {
        profissionais.add(profissional);
    }

    public ArrayList<Profissional> listarTodos() {
        return profissionais;
    }

    public Profissional buscarPorCodigo(int codigo) {
        if (!codigoValido(codigo)) {
            return null;
        }

        return profissionais.get(codigo);
    }

    public boolean codigoValido(int codigo) {
        return codigo >= 0 && codigo < profissionais.size();
    }

    public int contarTodos() {
        return profissionais.size();
    }

    public boolean estaVazio() {
        return profissionais.isEmpty();
    }
}