package com.videolibrary.zipcode.fullstackapp.services;

import com.videolibrary.zipcode.fullstackapp.models.User;
import com.videolibrary.zipcode.fullstackapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User u) {
        return userRepository.save(u);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean delete(Long id) {
        if (findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User update(Long id, User newUser) {
        User user = userRepository.getOne(id);
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        create(user);
        return user;
    }

    public User updateFirstName(Long id, String firstName) {
        User user = userRepository.getOne(id);
        user.setFirstName(firstName);
        create(user);
        return user;
    }

    public User updateLastName(Long id, String lastName) {
        User user = userRepository.getOne(id);
        user.setLastName(lastName);
        create(user);
        return user;
    }

}
