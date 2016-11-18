package br.unifor.retail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vania on 10/10/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product { //nome como eu quiser

    public Long id;
    public String prod_descric;
    public Float preco;
    public String nome;
    public Integer unidade_em_estoque;
    public String tamanho;
    public String cor;
    public String created_at;
    public String updated_at;
    public String url;
    public String foto;
    public String foto_file_name;


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProd_descric() {
        return prod_descric;
    }

    public void setProd_descric(String prod_descric) {
        this.prod_descric = prod_descric;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getUnidade_em_estoque() {
        return unidade_em_estoque;
    }

    public void setUnidade_em_estoque(Integer unidade_em_estoque) {
        this.unidade_em_estoque = unidade_em_estoque;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFoto_file_name() {
        return foto_file_name;
    }

    public void setFoto_file_name(String foto_file_name) {
        this.foto_file_name = foto_file_name;
    }
}
