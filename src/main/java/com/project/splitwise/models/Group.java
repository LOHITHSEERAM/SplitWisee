package com.project.splitwise.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "splitwise_group")
public class Group extends BaseModel {

    String name;

    @ManyToMany(cascade = CascadeType.ALL)
    Set<User> users;

    @ManyToOne
    User admin;

    public Group(){
        users = new HashSet<>();
    }

    public void addUser(User user){
        users.add(user);
    }

}
