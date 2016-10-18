package br.unifor.retail.model;

/**
 * Created by vania on 10/10/16.
 */

public class Cart {

    private Long idCart;
    private Float valorTotalCart;
    private Integer formaDePagamento;

    public Long getIdCart() {
        return idCart;
    }

    public void setIdCart(Long idCart) {
        this.idCart = idCart;
    }

    public Float getValorTotalCart() {
        return valorTotalCart;
    }

    public void setValorTotalCart(Float valorTotalCart) {
        this.valorTotalCart = valorTotalCart;
    }

    public Integer getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(Integer formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }
}
