package com.videolibrary.zipcode.fullstackapp.repositories;

import com.videolibrary.zipcode.fullstackapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long id);

/*
    @Query(value = "SELECT ")
    User updateUser(User user);
*/



}
