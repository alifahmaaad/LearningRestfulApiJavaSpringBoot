package com.belajarspringboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.belajarspringboot.dto.ResponseData;
import com.belajarspringboot.models.entities.Role;
import com.belajarspringboot.services.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping("/create")
    public ResponseEntity<ResponseData<Role>> create(@RequestBody Role role){
        ResponseData<Role> dataResponse= new ResponseData<>();
        dataResponse.setPayload(null);
        dataResponse.setStatus(false);
        try {
            Role dataRole = roleService.create(role);
            dataResponse.getMessages().add("berhasil");
            dataResponse.setPayload(dataRole);
            return ResponseEntity.ok(dataResponse);
        } catch (Exception e) {
            dataResponse.getMessages().add(e.getMessage());
            return ResponseEntity.badRequest().body(dataResponse);
        }

    }
}
