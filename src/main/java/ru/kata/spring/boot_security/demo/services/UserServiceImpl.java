package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public List<User> readAllUsers() {
        List<User> list = new LinkedList<>();
        userRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public User findUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public User update(User user) {
        User existUser = userRepository.findById(user.getId()).orElse(null);
        if (existUser != null) {
            existUser.setUsername(user.getUsername());
            existUser.setEmail(user.getEmail());
            existUser.setRoles(user.getRoles());
            if (!user.getPassword().isEmpty()) {
                existUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            }
            userRepository.save(existUser);
        }
        return existUser;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (userRepository.findById(id).isPresent())
            userRepository.deleteById(id);
    }
    @Transactional
    @Override
    public void create(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }
}