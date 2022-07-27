package com.atcs.olx.Entity.Authenticate;

public class LogOut {
    private String email;

    public LogOut(String email) {
        this.email = email;
    }

    public LogOut() {
       
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
