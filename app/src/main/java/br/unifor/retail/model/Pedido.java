package br.unifor.retail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vania on 10/10/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pedido  {

    private Long id;
    private Long cliente_id;
    private Double valor_total;
    private Boolean finalizado;
    private Long produto_id;
    private int quantidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public Double getValor_total() {
        return valor_total;
    }

    public void setValor_total(Double valor_total) {
        this.valor_total = valor_total;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }

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
}
