package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {
    List<User> readAllUsers();
    void create(User user);

    User update(User user);

    User findUser(Long id);

    void delete(Long id);

    User findByEmail(String email);
}
