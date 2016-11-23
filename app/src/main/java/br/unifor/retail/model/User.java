package br.unifor.retail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vania on 10/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private Long id;
    private Long user_id;
    private String foto;
    private String nome_cliente;
    private String sexo;
    private String aniversario;
    private String tamanho_blusa;
    private String tamanho_calca;
    private String tamanho_calcado;
    private String email;
    private String password;
    private String password_confirmation;
    private String role;
    private String facebook_token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getAniversario() {
        return aniversario;
    }

    public void setAniversario(String aniversario) {
        this.aniversario = aniversario;
    }

    public String getTamanho_blusa() {
        return tamanho_blusa;
    }

    public void setTamanho_blusa(String tamanho_blusa) {
        this.tamanho_blusa = tamanho_blusa;
    }

    public String getTamanho_calca() {
        return tamanho_calca;
    }

    public void setTamanho_calca(String tamanho_calca) {
        this.tamanho_calca = tamanho_calca;
    }

    public String getTamanho_calcado() {
        return tamanho_calcado;
    }

    public void setTamanho_calcado(String tamanho_calcado) {
        this.tamanho_calcado = tamanho_calcado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFacebook_token() {
        return facebook_token;
    }

    public void setFacebook_token(String facebook_token) {
        this.facebook_token = facebook_token;
    }
}
