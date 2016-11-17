package br.unifor.retail.model;



public class Usuario {

    private String nome;
    private String email;
    private String foto;
    private String senha;
    private String confirmaçao_senha;

    public Usuario() {
    }

    public Usuario(String nome, String email, String foto) {
        this.nome = nome;
        this.email = email;
        this.foto = foto;
    }

    public Usuario(String nome, String email, String senha, String confirmaçao_senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.confirmaçao_senha = confirmaçao_senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmaçao_senha() {
        return confirmaçao_senha;
    }

    public void setConfirmaçao_senha(String confirmaçao_senha) {
        this.confirmaçao_senha = confirmaçao_senha;
    }

}
