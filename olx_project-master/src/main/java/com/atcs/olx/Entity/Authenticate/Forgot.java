package com.atcs.olx.Entity.Authenticate;

public class Forgot {
    public String email;
    public String password;
    public Forgot() {
    }
    public Forgot(String email, String password) {
        this.email = email;
        this.password = password;
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
}
