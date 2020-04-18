package com.videolibrary.zipcode.fullstackapp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String firstName;
    private String lastName;

    public User() {}

    public User(Long id, String firstName, String lastName) {
        this.user_id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getUser_Id() {
        return user_id;
    }

    public void setUser_Id(Long id) {
        this.user_id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
