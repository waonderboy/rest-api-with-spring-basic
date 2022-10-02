package com.example.restfulwebservice.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDaoService userService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @PostMapping("/users")
    public User registerUser(@RequestBody User user) {
        return userService.save(user);
    }

}
