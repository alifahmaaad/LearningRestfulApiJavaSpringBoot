package com.belajarspringboot.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.belajarspringboot.dto.DataUserWithoutPass;
import com.belajarspringboot.dto.ResponseData;
import com.belajarspringboot.models.entities.User;
import com.belajarspringboot.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseData<User>> register(@Valid @RequestBody User user, Errors errs){
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
            User savedUser=userService.register(user);
            dataResponse.setStatus((true));
            dataResponse.setPayload(savedUser);
            dataResponse.getMessages().add("berhasil");
            return ResponseEntity.ok(dataResponse);
        } catch (Exception e) {
            dataResponse.getMessages().add("DB Server Error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataResponse);
        }
       
    }
    @GetMapping
    public ResponseEntity<List<DataUserWithoutPass>> getAllUser(){
        DataUserWithoutPass datauser = new DataUserWithoutPass();
        List<DataUserWithoutPass> datausers = new ArrayList<DataUserWithoutPass>();
        Iterable<User> users = userService.getAllUser();
        for (User user : users) {
            datauser.setId(user.getId());
            datauser.setNama(user.getNama());
            datauser.setEmail(user.getEmail());
            datausers.add(datauser);
        }
        return ResponseEntity.ok(datausers);
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseData<User>> updateUser(@RequestBody User user){
        User getUserById= userService.findById(user.getId());
        ResponseData<User> dataResponse = new ResponseData<>();
        dataResponse.setStatus(false);
        dataResponse.setPayload(null);
        if(getUserById!=null){
            try {
                User UpdatedUser = userService.updateUser(user);
                dataResponse.setPayload(userService.updateUser(UpdatedUser));
                dataResponse.setStatus(true);
                dataResponse.getMessages().add("berhasil");
                return ResponseEntity.ok(dataResponse);
            } catch (Exception e) {
                dataResponse.getMessages().add(e.getMessage());
            }
        }
        dataResponse.getMessages().add("gagal");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataResponse);
    }
}
