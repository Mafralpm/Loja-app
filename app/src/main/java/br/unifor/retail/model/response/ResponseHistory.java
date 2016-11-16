package br.unifor.retail.model.response;

/**
 * Created by vania on 27/10/16.
 */

public class ResponseHistory {

    public Long idHistory;
    public Integer cliente;
    public Integer pedido_id;
    public String creted_at;
    public String updated_at;
    public String url;


    public Long getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(Long idHistory) {
        this.idHistory = idHistory;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(Integer pedido_id) {
        this.pedido_id = pedido_id;
    }

    public String getCreted_at() {
        return creted_at;
    }

    public void setCreted_at(String creted_at) {
        this.creted_at = creted_at;
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
}
