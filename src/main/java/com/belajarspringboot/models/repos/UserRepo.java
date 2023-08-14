package com.belajarspringboot.models.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.belajarspringboot.models.entities.User;

public interface UserRepo extends CrudRepository<User,Long>{
    @Query(value = "SELECT * FROM USER WHERE EMAIL = ?1", nativeQuery = true)
    public User findByEmail(String email);
}
