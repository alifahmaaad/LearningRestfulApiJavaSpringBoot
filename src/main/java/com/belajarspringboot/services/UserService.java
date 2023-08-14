package com.belajarspringboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.belajarspringboot.models.entities.User;
import com.belajarspringboot.models.repos.UserRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo userRepo;
    public User Tambah(User user){
        return userRepo.save(user);
    }
    public User findbyEmail(String email){
        return userRepo.findByEmail(email);
    }
}
