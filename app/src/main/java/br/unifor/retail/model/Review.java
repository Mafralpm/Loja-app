package br.unifor.retail.model;

import java.io.Serializable;

/**
 * Created by vania on 10/10/16.
 */

public class Review implements Serializable{

    private Long id;
    private String review_descric;
    private String nota;
    public String created_at;
    public String updated_at;
    private Long produto_id;
    private Long cliente_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public Long getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(Long produto_id) {
        this.produto_id = produto_id;
    }
}
