package com.example.restfulwebservice.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDaoService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> retrieveAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> retrieveUser(@PathVariable Integer id) {
        User findUser = userService.findById(id);

        if (findUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        return ResponseEntity.ok(findUser);
    }

    @PostMapping("/users")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        Boolean deleteState = userService.deleteById(id);

        if (deleteState.equals(false)) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));

        }

    }
}
