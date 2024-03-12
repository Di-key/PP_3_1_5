package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    List<User> readAllUsers();
    void create(User user);

    User update(User user);

    User findUser(Long id);

    void delete(Long id);

    User findByUsername(String name);
}
