package com.poly.lab6.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {

    @Id
    String username;

    String password;
    String fullname;
    String email;
    String photo;
    boolean activated;
    boolean admin;

    @OneToMany(mappedBy = "account")
    List<Order> orders;
}
