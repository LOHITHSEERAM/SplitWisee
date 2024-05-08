package com.project.splitwise.controllers;

import com.project.splitwise.dtos.AddUserRequestDTO;
import com.project.splitwise.dtos.CreateUserRequestDTO;
import com.project.splitwise.models.User;
import com.project.splitwise.repositories.UserRepository;
import com.project.splitwise.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {

            User user = userService.createUser(createUserRequestDTO.getUserName(), createUserRequestDTO.getEmail());
            return ResponseEntity.ok(user);
    }

    @PostMapping("/adduser")
    public ResponseEntity<String> addUser(@RequestBody AddUserRequestDTO addUserRequestDTO) {

            userService.addUserInGroup(addUserRequestDTO.getUserId(),addUserRequestDTO.getGroupId());
            return ResponseEntity.ok("User added successfully");
    }
}
