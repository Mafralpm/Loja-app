package br.unifor.retail.model;

/**
 * Created by vania on 10/10/16.
 */

public class Review {

    private Long idReviews;
    private String descricaoReview;
    private String notaReview;
    private Long cliente_id;
    private Long produto_id;

    public Long getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(Long produto_id) {
        this.produto_id = produto_id;
    }

    public String getNotaReview() {
        return notaReview;
    }

    public void setNotaReview(String notaReview) {
        this.notaReview = notaReview;
    }

    public Long getIdReviews() {
        return idReviews;
    }

    public void setIdReviews(Long idReviews) {
        this.idReviews = idReviews;
    }

    public String getDescricaoReview() {
        return descricaoReview;
    }

    public void setDescricaoReview(String descricaoReview) {
        this.descricaoReview = descricaoReview;
    }

    public Long getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Long cliente_id) {
        this.cliente_id = cliente_id;
    }
}
