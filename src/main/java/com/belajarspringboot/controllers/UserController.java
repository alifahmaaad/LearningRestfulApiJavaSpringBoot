package com.belajarspringboot.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.belajarspringboot.dto.LoginDto;
import com.belajarspringboot.dto.ResponseData;
import com.belajarspringboot.dto.UserDto;
import com.belajarspringboot.models.entities.Role;
import com.belajarspringboot.models.entities.User;
import com.belajarspringboot.security.CustomUserDetailsService;
import com.belajarspringboot.services.RoleService;
import com.belajarspringboot.services.UserService;
import com.belajarspringboot.utils.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
     private AuthenticationManager authenticationManager;
     @Autowired
     private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ResponseData<String>> login(@RequestBody LoginDto logindto){
    ResponseData<String> dataResponse=new ResponseData<>();
        dataResponse.setStatus(false);
        dataResponse.setPayload(null);
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logindto.getEmail(), logindto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(logindto.getEmail());
            String token = jwtUtil.generateToken(userDetails);
            dataResponse.setStatus(true);
            dataResponse.setPayload(token);
            dataResponse.getMessages().add(" berhasil digenerate");
            return ResponseEntity.ok(dataResponse);
        } catch (AuthenticationException e) {
            dataResponse.getMessages().add(" gagal digenerate");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(dataResponse);
        }

    }
    @PostMapping("/register")
    public ResponseEntity<ResponseData<User>> register(@Valid @RequestBody UserDto user, Errors errs){
        ResponseData<User> dataResponse=new ResponseData<>();
        User registerUser = new User();
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
            registerUser.setEmail(user.getEmail());
            registerUser.setNama(user.getNama());
            registerUser.setPassword(user.getPassword());
            Set<Role> newRoles = new HashSet<>();
            for (String roleName : user.getRoles()) {
                Role existingRole = roleService.findByName(roleName);
                if (existingRole != null) {
                  newRoles.add(existingRole);
                }
            }
            registerUser.setRoles(newRoles);
            User savedUser=userService.register(registerUser);
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
    public ResponseEntity<Iterable<User>> getAllUser(){
        Iterable<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<User>> getUserById(@PathVariable Long id){
        ResponseData<User> dataResponse = new ResponseData<>();
        dataResponse.setPayload(null);
        dataResponse.setStatus(false);
        try {
            User dataUser = userService.findById(id);
            dataResponse.setPayload(dataUser);
            dataResponse.setStatus(true);
            dataResponse.getMessages().add("berhasil");
            return ResponseEntity.ok(dataResponse);
        } catch (Exception e) {
            dataResponse.getMessages().add(e.getMessage());
        }
        dataResponse.getMessages().add("gagal");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataResponse);
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseData<User>> updateUser(@RequestBody UserDto user){
        User getUserById= userService.findById(user.getId());
        ResponseData<User> dataResponse = new ResponseData<>();
        dataResponse.setStatus(false);
        dataResponse.setPayload(null);
        if(getUserById!=null){
            try {
                if(user.getRoles()!=null){
                    Set<Role> newRoles = new HashSet<>();
                    for (String roleName : user.getRoles()) {
                        Role existingRole = roleService.findByName(roleName);
                        if (existingRole != null) {
                            newRoles.add(existingRole);
                        }
                    }
                    getUserById.setRoles(newRoles);
                }
                getUserById.setNama(user.getNama());
                getUserById.setPassword(user.getPassword());
                getUserById.setEmail(user.getEmail());
                User UpdatedUser = userService.updateUser(getUserById);
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
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        String message = "Gagal";
        User getUserById=userService.findById(id);
        if(getUserById!=null){
            try {
                userService.deleteUser(id);
                message="Berhasil";
                return ResponseEntity.ok(message);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
