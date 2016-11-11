package br.unifor.retail.rest.response;

import java.util.ArrayList;
import java.util.List;

import br.unifor.retail.model.Review;

/**
 * Created by vania on 27/10/16.
 */

public class ResponseReview {

    private List<Review> reviews = new ArrayList<>();

    public List<Review> getReviews() {
        return reviews;
    }


    public Long id;
    public String review_descric;
    public Long produto_id;
    public String nome;
    public String created_at;
    public String updated_at;
    public String nota;


    public String cliente_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeview_descric() {
        return review_descric;
    }

    public void setPeview_descric(String peview_descric) {
        this.review_descric = peview_descric;
    }

    public Long getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(Long produto_id) {
        this.produto_id = produto_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }


    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getReview_descric() {
        return review_descric;
    }

    public void setReview_descric(String review_descric) {
        this.review_descric = review_descric;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }
}
