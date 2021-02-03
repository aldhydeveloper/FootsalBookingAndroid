package com.ns.queenfutsal.Model;

public class Users {
    private String id, fullName, password, email, noHp;

    public Users() {
    }

    public Users(String id, String fullName, String password, String email, String noHp) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.noHp = noHp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
}
