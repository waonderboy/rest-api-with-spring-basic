package com.example.restfulwebservice.domain;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList();

    private static int usersCount = 3;

    static{
        users.add(new User(1, "user1", LocalDateTime.now(), "344213kaf", "940221"));
        users.add(new User(2, "user2", LocalDateTime.now(), "dfg213kaf","940221"));
        users.add(new User(3, "user3", LocalDateTime.now(), "213kaffdg", "940221"));
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

    public boolean deleteById(int id) {
        Optional<User> findUser = users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();

        if (!findUser.isEmpty()) {
            return users.remove(findUser.get());
        }

        return false;
    }

}
