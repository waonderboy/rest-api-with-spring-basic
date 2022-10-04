package com.example.restfulwebservice.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity retrieveUser(@PathVariable Integer id) {
        User findUser = userService.findById(id);

        if (findUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // HATEOAS
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).retrieveAllUsers());
        EntityModel<User> resource = EntityModel.of(findUser).add(linkTo.withRel("all-users"));

        return ResponseEntity.ok(resource);
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
