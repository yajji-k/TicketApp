package com.TicketApp.AuthService.entities;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.*;

@Entity
@Table(name = "auth_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private String roles;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<String> getRoleSet() {
        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
    }
}
