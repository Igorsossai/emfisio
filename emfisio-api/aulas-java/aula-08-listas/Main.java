import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Profissional> profissionais = new ArrayList<>();
        ArrayList<Servico> servicos = new ArrayList<>();
        ArrayList<Agendamento> agendamentos = new ArrayList<>();

        clientes.add(new Cliente("Ana Paula", 42, "(27) 99999-0000", true));
        clientes.add(new Cliente("João Pereira", 38, "(27) 98888-1111", true));
        clientes.add(new Cliente("Maria Souza", 51, "(27) 97777-2222", false));

        profissionais.add(new Profissional("Elisangela Morozini", "Fisioterapia Pélvica", 40.0, true));
        profissionais.add(new Profissional("Mariana Silva", "Pilates", 35.0, true));
        profissionais.add(new Profissional("Rafael Costa", "Osteopatia", 45.0, false));

        servicos.add(new Servico("Sessão de Fisioterapia", 60, 150.00, true));
        servicos.add(new Servico("Pilates", 60, 120.00, true));
        servicos.add(new Servico("Osteopatia", 50, 180.00, false));

        agendamentos.add(new Agendamento(
                clientes.get(0),
                profissionais.get(0),
                servicos.get(0),
                "REALIZADO",
                "PAGO",
                30
        ));

        agendamentos.add(new Agendamento(
                clientes.get(1),
                profissionais.get(1),
                servicos.get(1),
                "FALTOU",
                "PAGO",
                10
        ));

        agendamentos.add(new Agendamento(
                clientes.get(2),
                profissionais.get(2),
                servicos.get(2),
                "AGENDADO",
                "PENDENTE",
                48
        ));

        System.out.println("=====================================");
        System.out.println("        EMFISIO - AULA 8             ");
        System.out.println("        Listas em Java               ");
        System.out.println("=====================================");

        System.out.println();
        System.out.println("=== Clientes Cadastrados ===");
        for (Cliente cliente : clientes) {
            cliente.exibirDados();
        }

        System.out.println();
        System.out.println("=== Profissionais Cadastrados ===");
        for (Profissional profissional : profissionais) {
            profissional.exibirDados();
        }

        System.out.println();
        System.out.println("=== Serviços Cadastrados ===");
        for (Servico servico : servicos) {
            servico.exibirDados();
        }

        System.out.println();
        System.out.println("=== Agendamentos Cadastrados ===");
        for (Agendamento agendamento : agendamentos) {
            agendamento.exibirResumo();
        }

        System.out.println();
        System.out.println("=== Resumo Geral ===");
        System.out.println("Total de clientes: " + clientes.size());
        System.out.println("Total de profissionais: " + profissionais.size());
        System.out.println("Total de serviços: " + servicos.size());
        System.out.println("Total de agendamentos: " + agendamentos.size());

        System.out.println();
        System.out.println("=== Busca de Cliente por Nome ===");

        String nomeBusca = "João Pereira";
        boolean encontrouCliente = false;

        for (Cliente cliente : clientes) {
            if (cliente.getNome().equalsIgnoreCase(nomeBusca)) {
                System.out.println("Cliente encontrado:");
                cliente.exibirDados();
                encontrouCliente = true;
            }
        }

        if (!encontrouCliente) {
            System.out.println("Cliente não encontrado.");
        }

        System.out.println();
        System.out.println("=== Clientes Ativos ===");

        for (Cliente cliente : clientes) {
            if (cliente.isAtivo()) {
                System.out.println(cliente.getNome());
            }
        }

        System.out.println();
        System.out.println("=== Serviços Ativos ===");

        for (Servico servico : servicos) {
            if (servico.isAtivo()) {
                System.out.println(servico.getNome() + " - R$ " + servico.getValor());
            }
        }

        System.out.println();
        System.out.println("=== Profissionais Disponíveis ===");

        for (Profissional profissional : profissionais) {
            if (profissional.isDisponivel()) {
                System.out.println(profissional.getNome() + " - " + profissional.getEspecialidade());
                
            }
        }

        System.out.println();
System.out.println("=== Contadores ===");

int totalClientesAtivos = 0;
int totalServicosAtivos = 0;
int totalProfissionaisDisponiveis = 0;

for (Cliente cliente : clientes) {
    if (cliente.isAtivo()) {
        totalClientesAtivos++;
    }
}

for (Servico servico : servicos) {
    if (servico.isAtivo()) {
        totalServicosAtivos++;
    }
}

for (Profissional profissional : profissionais) {
    if (profissional.isDisponivel()) {
        totalProfissionaisDisponiveis++;
    }
}

System.out.println("Clientes ativos: " + totalClientesAtivos);
System.out.println("Serviços ativos: " + totalServicosAtivos);
System.out.println("Profissionais disponíveis: " + totalProfissionaisDisponiveis);

    }
}
