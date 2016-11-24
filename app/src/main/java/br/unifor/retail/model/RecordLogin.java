package br.unifor.retail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vania on 17/11/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordLogin {

    Cliente cliente = new Cliente();
    User user =  new User();


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
