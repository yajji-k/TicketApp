package com.TicketApp.AuthService.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TicketApp.AuthService.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
