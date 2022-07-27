package com.atcs.olx.Entity.Authenticate;

public class ActiveUsers {
    private String name;
    private String email;
    public ActiveUsers() {
    }
    public ActiveUsers(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
