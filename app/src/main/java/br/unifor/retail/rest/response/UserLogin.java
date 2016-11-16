package br.unifor.retail.rest.response;

/**
 * Created by labm4 on 16/11/16.
 */

public class UserLogin {

    private String email;

    private String password;

    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserLogin() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
