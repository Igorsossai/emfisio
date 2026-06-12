public class Pagamento {

    private double valor;
    private String formaPagamento;
    private String status;

    public Pagamento(double valor, String formaPagamento, String status) {
        setValor(valor);
        setFormaPagamento(formaPagamento);
        setStatus(status);
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        if (valor >= 0) {
            this.valor = valor;
        } else {
            System.out.println("Valor inválido. O pagamento não pode ser negativo.");
        }
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        if (formaPagamento != null && !formaPagamento.isBlank()) {
            this.formaPagamento = formaPagamento.toUpperCase();
        } else {
            System.out.println("Forma de pagamento inválida.");
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status != null && !status.isBlank()) {
            this.status = status.toUpperCase();
        } else {
            System.out.println("Status do pagamento inválido.");
        }
    }

    public boolean estaPago() {
        return status.equals("PAGO");
    }

    public void exibirDados() {
        System.out.println("=== Pagamento ===");
        System.out.println("Valor: R$ " + valor);
        System.out.println("Forma de pagamento: " + formaPagamento);
        System.out.println("Status: " + status);
    }
}