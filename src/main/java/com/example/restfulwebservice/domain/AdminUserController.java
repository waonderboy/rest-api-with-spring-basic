package com.example.restfulwebservice.domain;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {
    private final UserDaoService userService;

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {

        List<User> users = userService.findAll();

        return filteringUserInfo("UserInfo",
                new MappingJacksonValue(users),
                new String[]{"id", "name", "joinDate", "ssn"});
    }

    // GET /admin/users/1 -> admin/v1/users/1
    //@GetMapping("/v1/users/{id}")
    //@GetMapping(value = "/users/{id}/", params = "version=1")
    //@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable Integer id) {
        User findUser = userService.findById(id);

        if (findUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return filteringUserInfo("UserInfo",
                new MappingJacksonValue(findUser),
                new String[]{"id", "name", "joinDate", "ssn"});
    }

    // admin/v2/users/1
    //@GetMapping("/v2/users/{id}")
    //@GetMapping(value = "/users/{id}/", params = "version=2")
    //@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable Integer id) {
        User findUser = userService.findById(id);

        if (findUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User-> UserV2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(findUser, userV2);
        userV2.setGrade("Vip");

        return filteringUserInfo("UserInfoV2",
                new MappingJacksonValue(userV2),
                new String[]{"id", "name", "joinDate", "ssn", "grade"});
    }



    private MappingJacksonValue filteringUserInfo(String filterName,
                                                  MappingJacksonValue users,
                                                  String... propertyArray) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept(propertyArray);

        FilterProvider filters = new SimpleFilterProvider().addFilter(filterName, filter);

        MappingJacksonValue mapping = users;
        mapping.setFilters(filters);
        return mapping;
    }
}
