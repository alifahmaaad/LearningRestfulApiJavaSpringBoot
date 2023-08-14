package com.belajarspringboot.models.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name ="USER")
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "nama is required")
    private String nama;
    @NotEmpty(message = "password is required")
    private String password;
    @NotEmpty(message = "email is required")
    @Email(message = "email not valid")
    @Column(name = "email",unique = true)
    private String email;
}
