package br.unifor.retail.rest.response;

/**
 * Created by vania on 27/10/16.
 */

public class ResponseReview {


    public Long id;
    public String peview_descric;
    public Long produto_id;
    public String nome;
    public String created_at;
    public String updated_at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeview_descric() {
        return peview_descric;
    }

    public void setPeview_descric(String peview_descric) {
        this.peview_descric = peview_descric;
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
}
