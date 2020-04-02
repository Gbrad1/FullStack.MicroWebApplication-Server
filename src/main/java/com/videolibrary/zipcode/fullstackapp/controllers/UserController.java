package com.videolibrary.zipcode.fullstackapp.controllers;

import com.videolibrary.zipcode.fullstackapp.models.User;
import com.videolibrary.zipcode.fullstackapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/User")
    public ResponseEntity<Iterable<User>> index() {
        return new ResponseEntity<>(service.index(), HttpStatus.OK);
    }

    @GetMapping("/User/{id}")
    public ResponseEntity<User> show(@PathVariable Long id) {
        return new ResponseEntity<>(service.show(id), HttpStatus.OK);
    }

    @PostMapping("/User")
    public ResponseEntity<User> create(@RequestBody User u) {
        return new ResponseEntity<>(service.create(u), HttpStatus.CREATED);
    }

    @PutMapping("/User/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User u) {
        return new ResponseEntity<>(service.update(id, u), HttpStatus.OK);
    }

    @PostMapping("/User/list")
    public ResponseEntity<List<User>> addMultiple(List<User> list) {
        return new ResponseEntity<>(service.create(list), HttpStatus.CREATED);
    }

    @DeleteMapping("/User/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
