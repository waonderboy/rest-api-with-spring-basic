package com.example.restfulwebservice.domain;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {
    private final UserDaoService userService;

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {

        List<User> users = userService.findAll();

        return filteringUserInfo(new MappingJacksonValue(users));
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable Integer id) {
        User findUser = userService.findById(id);

        if (findUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return filteringUserInfo(new MappingJacksonValue(findUser));
    }

    private MappingJacksonValue filteringUserInfo(MappingJacksonValue users) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = users;
        mapping.setFilters(filters);
        return mapping;
    }
}
