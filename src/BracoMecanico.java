public class BracoMecanico {
    public Esteira esteira;
    public double tempoTrabalho = 0;
    public static final int limiteProducao = 20;
    public static final double tempoFixo = 5.5;

    public BracoMecanico(Esteira esteira) {
        this.esteira = esteira;
    }

    public void empacotar() {
        System.out.println("Começando empacotamento - 08:00\n");
        boolean prazoExecedido = false;
        int prazoExcedidoQuantidade = 0;
        int pacotesMeioDia = 0;

        for (Pedido pedido : this.esteira.pedidos) {
            double tempo = 0;
            int qtdProdutosOld = pedido.qtdProdutos;

            if (this.tempoTrabalho <= 14400)
                pacotesMeioDia++; // Incremente todos os pedidos até 12:00(primeiras 4 horas)

            while (pedido.qtdProdutos > 0) {
                if (pedido.prazoMinutos == 0
                        || !(this.tempoTrabalho > pedido.prazoMinutos * 60 && pedido.prazoMinutos > 0)) {
                    if (pedido.qtdProdutos > limiteProducao) {
                        tempo += tempoFixo;
                        this.tempoTrabalho += tempoFixo;/**
                                                         * Considera que cada empacotamento de até 20 produtos demora
                                                         * 5.5s
                                                         */
                        pedido.qtdProdutos -= limiteProducao;
                    } else {
                        tempo += tempoFixo;
                        this.tempoTrabalho += tempoFixo; /**
                                                          * Considera que cada empacotamento de até 20 produtos demora
                                                          * 5.5s
                                                          */
                        pedido.qtdProdutos = 0;
                    }
                    prazoExecedido = false;
                } else {
                    prazoExecedido = true;
                    prazoExcedidoQuantidade++;
                    break;
                }
            }

            String retorno = "Cliente: " + pedido.cliente +
                    " | Qtd Produtos: " + qtdProdutosOld +
                    " | Prazo: " + pedido.prazoMinutos * 60
                    + "s | Tempo médio gasto: " + tempo
                    + "s" + " | Tempo de Retorno: " + this.tempoTrabalho + "s"
                    + (prazoExecedido ? " | O prazo do pedido foi excedido!" : "");

            System.out.println(retorno);
        }

        int horas = (int) (this.tempoTrabalho / 3600);
        int minutos = (int) ((this.tempoTrabalho / 3600 - horas) * 60);
        System.out.println("\nTerminando empacotamento - " + horas + " horas e " + minutos + " minutos (" + (8 + horas)
                + ":" + minutos + ")\n");

        System.out.printf("Média de tempo por pedido: %.2f minutos\n",
                (this.tempoTrabalho / this.esteira.pedidos.size()) / 60);

        System.out.printf("Pacotes feitos até meio dia: %d (%.2f%% do total de pedidos)\n", pacotesMeioDia,
                (double) pacotesMeioDia / this.esteira.pedidos.size() * 100);

        System.out.println("Quantidade de pedidos com prazo excedido: " + prazoExcedidoQuantidade);
        System.out.println("Comprar uma segunda esteira reduziria pela metade o tempo total de empacotamento.");
    }
}
