package br.unifor.retail.model;

/**
 * Created by vania on 10/10/16.
 */

public class Review {

    private Long idReviews;
    private String descricaoReview;

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
}
