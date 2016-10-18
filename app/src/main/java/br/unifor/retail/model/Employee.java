package br.unifor.retail.model;


/**
 * Created by vania on 10/10/16.
 */

public class Employee extends User {

    private Long idEmployee;
    private String nomeEmployee;
    private String enderecoEmployee;
    private String telefoneEmployee;
    private String cpfEmployee;

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getNomeEmployee() {
        return nomeEmployee;
    }

    public void setNomeEmployee(String nomeEmployee) {
        this.nomeEmployee = nomeEmployee;
    }

    public String getEnderecoEmployee() {
        return enderecoEmployee;
    }

    public void setEnderecoEmployee(String enderecoEmployee) {
        this.enderecoEmployee = enderecoEmployee;
    }

    public String getTelefoneEmployee() {
        return telefoneEmployee;
    }

    public void setTelefoneEmployee(String telefoneEmployee) {
        this.telefoneEmployee = telefoneEmployee;
    }

    public String getCpfEmployee() {
        return cpfEmployee;
    }

    public void setCpfEmployee(String cpfEmployee) {
        this.cpfEmployee = cpfEmployee;
    }
}
