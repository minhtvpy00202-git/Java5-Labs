package com.poly.lab6.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "Categories")
public class Category implements Serializable {

    @Id
    @NotBlank
    @Size(min = 2, max = 20)
    String id;

    @NotBlank
    @Size(min = 2, max = 50)
    String name;

    @OneToMany(mappedBy = "category")
    List<Product> products;
}
