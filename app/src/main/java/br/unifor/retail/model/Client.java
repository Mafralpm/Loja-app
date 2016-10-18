package br.unifor.retail.model;

/**
 * Created by vania on 10/10/16.
 */

public class Client extends User {

    private Long idClient;
    private String nomeClient;
    private String telefoneClient;
    private String cpfClient;

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getNomeClient() {
        return nomeClient;
    }

    public void setNomeClient(String nomeClient) {
        this.nomeClient = nomeClient;
    }

    public String getTelefoneClient() {
        return telefoneClient;
    }

    public void setTelefoneClient(String telefoneClient) {
        this.telefoneClient = telefoneClient;
    }

    public String getCpfClient() {
        return cpfClient;
    }

    public void setCpfClient(String cpfClient) {
        this.cpfClient = cpfClient;
    }
}
