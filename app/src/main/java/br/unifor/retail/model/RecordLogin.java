package br.unifor.retail.model;

/**
 * Created by vania on 17/11/16.
 */

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
