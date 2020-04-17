package com.videolibrary.zipcode.fullstackapp.controllers;

import com.videolibrary.zipcode.fullstackapp.models.User;
import com.videolibrary.zipcode.fullstackapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@CrossOrigin
@RequestMapping("/user/")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<Iterable<User>> index() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<User> create(@RequestBody User u) {
        User user = service.create(u);
        try {
            return ResponseEntity.created(new URI("create/" + user.getUser_Id())).body(user);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User u) {
        User user = service.update(id, u);
        try {
            return ResponseEntity.created(new URI("update/" + user.getUser_Id())).body(u);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("updateFirstName/{id}")
    public ResponseEntity<User> updateFirstName(@PathVariable Long id, @RequestParam String firstName) {
        return new ResponseEntity<>(service.updateFirstName(id, firstName), HttpStatus.OK);
    }

    @PutMapping("updateLastName/{id}")
    public ResponseEntity<User> updateLastName(@PathVariable Long id, @RequestParam String lastName) {
        return new ResponseEntity<>(service.updateLastName(id, lastName), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
