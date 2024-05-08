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
@Table(name = "user_entity")
public class User extends BaseModel {

    String username;

    @Column(unique = true)
    String email;

    @ManyToMany(cascade = CascadeType.ALL)
    Set<Group> groups;

    public User() {
        groups = new HashSet<>();
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

}
