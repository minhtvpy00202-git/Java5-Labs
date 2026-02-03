package com.poly.lab8.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Accounts")
public class Account {

    @Id
    private String username;
    private String password;
    private String fullname;
    private String email;
    private Boolean activated;
    private Boolean admin;

    // getter setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public Boolean getAdmin() { return admin; }
    public void setAdmin(Boolean admin) { this.admin = admin; }
}
