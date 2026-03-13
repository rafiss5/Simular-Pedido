package lanchonete;

import model.Pedido;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== TESTANDO O BUILDER DE PEDIDOS ===\n");


        try {
            Pedido pedido1 = new Pedido.PedidoBuilder()
                    .tipoHamburguer()
                    .tamanhoM()
                    .tipoPao("BRIOCHE")
                    .addItem("X-Bacon")
                    .pagarComPix()
                    .entregaDelivery("Rua Ana Maria de Sousa, 48")
                    .comObservacao("sem cebola")
                    .build();

            System.out.println("Caso 1 - Hambúrguer válido:");
            pedido1.printResumo();
        } catch (IllegalStateException e) {
            System.out.println("Erro no Caso 1: " + e.getMessage());
        }


        try {
            Pedido pedido2 = new Pedido.PedidoBuilder()
                    .tipoPizza()
                    .tamanhoG("G")
                    .tipoMassa("FINA")
                    .addItem("Pizza Calabresa")
                    .comBordaRecheada()
                .pagarComCartao("CARTAO")
                    .entregaRetirada("Retirada")
                    .comCupom("10OFF")
                    .build();

            System.out.println("Caso 2 - Pizza válida:");
            pedido2.printResumo();
        } catch (IllegalStateException e) {
            System.out.println("Erro no Caso 2: " + e.getMessage());
        }


        try {
            Pedido pedido3 = new Pedido.PedidoBuilder()
                    .tipoHamburguer()
                    .tamanhoP()
                    .tipoPao("TRADICIONAL")
                    .addItem("X-Salada")
                    .pagarComDinheiro()
                    .comTrocoPara(100.0)
                    .entregaRetirada("Retirada")
                    .build();

            System.out.println("Caso 3 - Dinheiro com troco:");
            pedido3.printResumo();
        } catch (IllegalStateException e) {
            System.out.println("Erro no Caso 3: " + e.getMessage());
        }


        try {
            System.out.println("Caso 4 - Tentando criar pizza sem massa:");
            Pedido pedidoInvalido = new Pedido.PedidoBuilder()
                    .tipoPizza()
                    .tamanhoG("G")

                    .addItem("Pizza Margherita")
                    .pagarComCartao("Cartão")
                    .entregaRetirada("Retirada")
                    .build();


            pedidoInvalido.printResumo();
        } catch (IllegalStateException e) {
            System.out.println("Erro no caso 4: " + e.getMessage());
        }

    }
}