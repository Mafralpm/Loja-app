package br.unifor.retail.model;

/**
 * Created by vania on 21/11/16.
 */

public class PedidoHasProduto {

    private Long produto_id;
    private int quantidade;
    private Long pedido_id;

    public Long getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(Long produto_id) {
        this.produto_id = produto_id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Long getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(Long pedido_id) {
        this.pedido_id = pedido_id;
    }
}
