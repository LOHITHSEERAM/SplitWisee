package com.project.splitwise.services;

import com.project.splitwise.models.Group;
import com.project.splitwise.models.User;
import com.project.splitwise.repositories.GroupRepository;
import com.project.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    GroupRepository groupRepository;

    @Autowired
    UserService(UserRepository userRepository,GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public User createUser(String username, String email) {

        try {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        return userRepository.save(user);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("User already exists");
        }
    }

    public void addUserInGroup(int userID,int groupId) {

        User user;
        Optional<User> optionalUser= userRepository.findById(userID);
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        else
            throw new IllegalArgumentException("User not found");
        Group group;

        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if(optionalGroup.isPresent()) {
            group = optionalGroup.get();
        }
        else
            throw new IllegalArgumentException("Group not found");

        if(group.getUsers().contains(user)) {
            throw new IllegalArgumentException("User is already in group");
        }
        try {
            group.getUsers().add(user);
            groupRepository.save(group);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("User already exists");
        }

    }
}
