package com.belajarspringboot.models.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.belajarspringboot.models.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{
     Role findByName(String name);
    
}
