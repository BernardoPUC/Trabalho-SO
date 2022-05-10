public class Pedido {
    public String cliente;
    public int qtdProdutos;
    public double prazoMinutos;
    // Calcular prazo empacotamento qtdProdutos * 5.5s

    public Pedido(String cliente, int qtdProdutos, double prazoMinutos) {
        this.cliente = cliente;
        this.qtdProdutos = qtdProdutos;
        this.prazoMinutos = prazoMinutos;
    }

    public String getCliente() {
        return cliente;
    }

    public double getPrazoMinutos() {
        return prazoMinutos;
    }

    public int getQtdProdutos() {
        return qtdProdutos;
    }

    @Override
    public String toString() {
        return this.cliente + " " + this.qtdProdutos + " " + this.prazoMinutos;
    }
}
