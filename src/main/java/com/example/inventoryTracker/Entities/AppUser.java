package com.example.inventoryTracker.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Username")
    private String username;

    @ToString.Exclude //Exclude the password field from the generated toString() method
    @EqualsAndHashCode.Exclude //Exclude the password field from the generated equals() and hashCode() methods
    @Column(name = "Password")
    private String password;

    @Column(name = "Email")
    private String email;

    @Column(name = "Role")
    private String role;
}
