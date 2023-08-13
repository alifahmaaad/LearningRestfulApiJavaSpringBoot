package com.belajarspringboot.models.repos;

import org.springframework.data.repository.CrudRepository;

import com.belajarspringboot.models.entities.User;

public interface UserRepo extends CrudRepository<User,Long>{
    
}
