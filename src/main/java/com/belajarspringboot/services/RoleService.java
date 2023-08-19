package com.belajarspringboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.belajarspringboot.models.entities.Role;
import com.belajarspringboot.models.repos.RoleRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;
    public Role create(Role role){
        return roleRepo.save(role);
    }
    public Role findByName(String role){
        return roleRepo.findByName(role);
    }
}
