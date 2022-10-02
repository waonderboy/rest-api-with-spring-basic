package com.example.restfulwebservice.domain;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList();

    private static int usersCount = 3;

    static{
        users.add(new User(1, "user1", LocalDateTime.now()));
        users.add(new User(2, "user2", LocalDateTime.now()));
        users.add(new User(3, "user3", LocalDateTime.now()));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user){
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User findById(int id) {
        for (User user:users){
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

}
