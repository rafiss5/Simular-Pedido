package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {

    private final String tipo;
    private final String tamanho;
    private final String pagamento;
    private final List<String> itens;

    private final String tipoPao;
    private final String tipoMassa;
    private final boolean bordaRecheada;
    private final String entrega;
    private final String enderecoEntrega;
    private final String cupom;
    private final String observacao;
    private final Double trocoPara;

    private Pedido(PedidoBuilder builder) {
        this.tipo = builder.tipo;
        this.tamanho = builder.tamanho;
        this.pagamento = builder.pagamento;
        this.itens = Collections.unmodifiableList(new ArrayList<>(builder.itens));
        this.tipoPao = builder.tipoPao;
        this.tipoMassa = builder.tipoMassa;
        this.bordaRecheada = builder.bordaRecheada;
        this.entrega = builder.entrega;
        this.enderecoEntrega = builder.enderecoEntrega;
        this.cupom = builder.cupom;
        this.observacao = builder.observacao;
        this.trocoPara = builder.trocoPara;
    }

    public String getTipo() { return tipo; }
    public String getTamanho() { return tamanho; }
    public String getPagamento() { return pagamento; }
    public List<String> getItens() { return itens; }
    public String getTipoPao() { return tipoPao; }
    public String getTipoMassa() { return tipoMassa; }
    public boolean isBordaRecheada() { return bordaRecheada; }
    public String getEntrega() { return entrega; }
    public String getEnderecoEntrega() { return enderecoEntrega; }
    public String getCupom() { return cupom; }
    public String getObservacao() { return observacao; }
    public Double getTrocoPara() { return trocoPara; }

    public void printResumo() {
        System.out.println("\n=== RESUMO DO PEDIDO ===");
        System.out.println("Tipo: " + getTipo());
        System.out.println("Tamanho: " + getTamanho());
        System.out.println("Itens: " + String.join(", ", getItens()));

        if (getTipo().equals("Hamburguer")) {
            System.out.println("Pão: " + getTipoPao());
        } else if (getTipo().equals("Pizza")) {
            System.out.println("Massa: " + getTipoMassa());
            System.out.println("Borda recheada: " + (isBordaRecheada() ? "Sim" : "Não"));
        }

        System.out.println("Pagamento: " + getPagamento());
        if (getTrocoPara() != null) {
            System.out.println("Troco para: R$ " + String.format("%.2f", getTrocoPara()));
        }

        System.out.println("Entrega: " + getEntrega());
        if (getEnderecoEntrega() != null && !getEnderecoEntrega().isEmpty()) {
            System.out.println("Endereço: " + getEnderecoEntrega());
        }

        if (getCupom() != null && !getCupom().isEmpty()) {
            System.out.println("Cupom: " + getCupom());
        }

        if (getObservacao() != null && !getObservacao().isEmpty()) {
            System.out.println("Observação: " + getObservacao());
        }
        System.out.println("=========================\n");
    }

    public static class PedidoBuilder {

        private String tipo;
        private String tamanho;
        private String pagamento;
        private List<String> itens = new ArrayList<>();

        private String tipoPao;
        private String tipoMassa;
        private boolean bordaRecheada = false;
        private String entrega;
        private String enderecoEntrega;
        private String cupom;
        private String observacao;
        private Double trocoPara;

        public PedidoBuilder tipoHamburguer() {
            this.tipo = "HAMBURGUER";
            return this;
        }

        public PedidoBuilder tipoPizza() {
            this.tipo = "PIZZA";
            return this;
        }

        public PedidoBuilder tamanhoP() {
            this.tamanho = "P";
            return this;
        }

        public PedidoBuilder tamanhoM() {
            this.tamanho = "M";
            return this;
        }

        public PedidoBuilder tamanhoG(String g) {
            this.tamanho = "G";
            return this;
        }

        public PedidoBuilder pagarComPix() {
            this.pagamento = "PIX";
            return this;
        }

        public PedidoBuilder pagarComCartao(String cartão) {
            this.pagamento = "CARTAO";
            return this;
        }

        public PedidoBuilder pagarComDinheiro() {
            this.pagamento = "DINHEIRO";
            return this;
        }

        public PedidoBuilder comTrocoPara(double valor) {
            this.trocoPara = valor;
            return this;
        }

        public PedidoBuilder entregaDelivery(String endereco) {
            this.entrega = "DELIVERY";
            this.enderecoEntrega = endereco;
            return this;
        }

        public PedidoBuilder entregaRetirada(String retirada) {
            this.entrega = "RETIRADA";
            this.enderecoEntrega = null;
            return this;
        }

        public PedidoBuilder tipoPao(String pao) {
            this.tipoPao = pao;
            return this;
        }

        public PedidoBuilder tipoMassa(String massa) {
            this.tipoMassa = massa;
            return this;
        }

        public PedidoBuilder comBordaRecheada() {
            this.bordaRecheada = true;
            return this;
        }

        public PedidoBuilder semBordaRecheada() {
            this.bordaRecheada = false;
            return this;
        }

        public PedidoBuilder addItem(String item) {
            this.itens.add(item);
            return this;
        }

        public PedidoBuilder comCupom(String cupom) {
            this.cupom = cupom;
            return this;
        }

        public PedidoBuilder comObservacao(String obs) {
            this.observacao = obs;
            return this;
        }

        public Pedido build() {
            validarPedido();
            return new Pedido(this);
        }

        private void validarPedido() {
            // Regras gerais
            if (tipo == null || (!tipo.equals("HAMBURGUER") && !tipo.equals("PIZZA"))) {
                throw new IllegalStateException("Tipo deve ser HAMBURGUER ou PIZZA");
            }

            if (tamanho == null || (!tamanho.equals("P") && !tamanho.equals("M") && !tamanho.equals("G"))) {
                throw new IllegalStateException("Tamanho deve ser P, M ou G");
            }

            if (pagamento == null || (!pagamento.equals("PIX") && !pagamento.equals("CARTAO") && !pagamento.equals("DINHEIRO"))) {
                throw new IllegalStateException("Pagamento deve ser PIX, CARTAO ou DINHEIRO");
            }

            if (itens.isEmpty()) {
                throw new IllegalStateException("Deve haver pelo menos um item no pedido");
            }

            if (tipo.equals("HAMBURGUER")) {
                if (tipoPao == null || tipoPao.isEmpty()) {
                    throw new IllegalStateException("Hambúrguer deve ter tipo de pão definido");
                }
                if (!tipoPao.equals("BRIOCHE") && !tipoPao.equals("TRADICIONAL") && !tipoPao.equals("INTEGRAL")) {
                    throw new IllegalStateException("Tipo de pão deve ser BRIOCHE, TRADICIONAL ou INTEGRAL");
                }
                if (tipoMassa != null) {
                    throw new IllegalStateException("Hambúrguer não pode ter tipo de massa");
                }
                if (bordaRecheada) {
                    throw new IllegalStateException("Hambúrguer não pode ter borda recheada");
                }
            }

            if (tipo.equals("PIZZA")) {
                if (tipoMassa == null || tipoMassa.isEmpty()) {
                    throw new IllegalStateException("Pizza deve ter tipo de massa definido");
                }
                if (!tipoMassa.equals("FINA") && !tipoMassa.equals("TRADICIONAL") && !tipoMassa.equals("PAN")) {
                    throw new IllegalStateException("Tipo de massa deve ser FINA, TRADICIONAL ou PAN");
                }
                if (tipoPao != null) {
                    throw new IllegalStateException("Pizza não pode ter tipo de pão");
                }
            }

            if (entrega == null || (!entrega.equals("DELIVERY") && !entrega.equals("RETIRADA"))) {
                throw new IllegalStateException("Entrega deve ser DELIVERY ou RETIRADA");
            }

            if (entrega.equals("DELIVERY")) {
                if (enderecoEntrega == null || enderecoEntrega.trim().isEmpty()) {
                    throw new IllegalStateException("Endereço de entrega é obrigatório para DELIVERY");
                }
            } else {
                if (enderecoEntrega != null && !enderecoEntrega.isEmpty()) {
                    throw new IllegalStateException("Endereço não deve ser informado para RETIRADA");
                }
            }

            if (!pagamento.equals("DINHEIRO") && trocoPara != null) {
                throw new IllegalStateException("Troco só pode ser informado para pagamento em DINHEIRO");
            }

            if (pagamento.equals("DINHEIRO") && trocoPara != null && trocoPara <= 0) {
                throw new IllegalStateException("Troco deve ser maior que zero");
            }
        }
    }
}