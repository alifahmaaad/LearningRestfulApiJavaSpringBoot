package com.belajarspringboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.belajarspringboot.dto.ResponseData;
import com.belajarspringboot.models.entities.User;
import com.belajarspringboot.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseData<User>> tambah(@Valid @RequestBody User user, Errors errs){
        ResponseData<User> dataResponse=new ResponseData<>();
        dataResponse.setStatus(false);
         dataResponse.setPayload(null);
        if(userService.findbyEmail(user.getEmail()) != null){
            dataResponse.getMessages().add("email already taken");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataResponse);
        }
        if(errs.hasErrors()){
         for (ObjectError err : errs.getAllErrors()) {
             dataResponse.getMessages().add(err.getDefaultMessage());
         }
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataResponse);
        }
        try {
            User savedUser=userService.Tambah(user);
            dataResponse.setStatus((true));
            dataResponse.setPayload(savedUser);
            dataResponse.getMessages().add("berhasil");
            return ResponseEntity.ok(dataResponse);
        } catch (Exception e) {
            dataResponse.getMessages().add("DB Server Error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataResponse);
        }
       
    }
}
