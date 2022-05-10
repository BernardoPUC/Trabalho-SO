import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Esteira {
    public ArquivoTextoLeitura arquivoEntrada;
    public ArrayList<Pedido> pedidos;

    public Esteira(String arquivo) throws UnsupportedEncodingException {
        this.arquivoEntrada = new ArquivoTextoLeitura(arquivo);
        this.pedidos = adicionarPedidos(this.arquivoEntrada);
        this.ordernarPedidos();
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public static Pedido preencherPedidos(String dadosPedidos) {
        String[] informacao = dadosPedidos.split(";");

        String cliente = informacao[0];
        int qtdProdutos = Integer.parseInt(informacao[1]);
        int prazoMinutos = Integer.parseInt(informacao[2]);

        return new Pedido(cliente, qtdProdutos, prazoMinutos);
    }

    public static ArrayList<Pedido> adicionarPedidos(ArquivoTextoLeitura arquivoEntrada) {
        String entrada;

        entrada = arquivoEntrada.ler();
        ArrayList<Pedido> pedidos = new ArrayList<>(Integer.parseInt(entrada));

        entrada = arquivoEntrada.ler();
        while (entrada != null) {
            if (!verificaClienteExiste(pedidos, preencherPedidos(entrada))) {
                pedidos.add(preencherPedidos(entrada));
            }
            entrada = arquivoEntrada.ler();
        }
        arquivoEntrada.fecharArquivo();

        return pedidos;
    }

    public static boolean verificaClienteExiste(ArrayList<Pedido> pedidos, Pedido cliente) {
        for (Pedido p : pedidos) {
            if (p.getCliente().equals(cliente.cliente) && cliente.prazoMinutos > 0) { /**
                                                                                       * Junta todos os pedidos com
                                                                                       * prazo de clientes iguais
                                                                                       */
                p.prazoMinutos += cliente.prazoMinutos;
                p.qtdProdutos += cliente.qtdProdutos;
                return true;
            }
        }
        return false;
    }

    public void ordernarPedidos() {
        // Armazena todos os pedidos
        ArrayList<Pedido> pedidosAntigos = new ArrayList<>();
        for (Pedido pedido : this.pedidos) {
            pedidosAntigos.add(pedido);
        }

        // Remove todos os pedidos sem prazo
        this.pedidos.removeIf(item -> item.getPrazoMinutos() == 0);

        /**
         * Ordena os pedidos restantes por prazo(crescente) e quantidade de
         * produtos(crescente)
         */
        Collections.sort(this.pedidos,
                Comparator.comparing(Pedido::getPrazoMinutos).thenComparing(Pedido::getQtdProdutos));

        // Ordena os pedidos removidos(sem prazo) e adiciona ao final do array ordenado
        Collections.sort(pedidosAntigos, Comparator.comparing(Pedido::getQtdProdutos));
        for (Pedido pedido : pedidosAntigos) {
            if (pedido.getPrazoMinutos() == 0) {
                this.pedidos.add(pedido);
            }
        }
    }
}
