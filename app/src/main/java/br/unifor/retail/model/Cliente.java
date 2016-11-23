package br.unifor.retail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vania on 10/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cliente {

    private Long id;
    private String foto;
    private String nome_cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }


}
