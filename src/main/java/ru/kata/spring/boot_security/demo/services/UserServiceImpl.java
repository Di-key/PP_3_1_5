package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public List<User> readAllUsers() {
        return userDao.readAllUsers();
    }

    @Override
    @Transactional
    public void create(User user) {
        userDao.create(user);
    }

    @Override
    public User findUser(Long id) {
        return userDao.findUser(id);
    }

    @Override
    @Transactional
    public User update(User user) {
        return userDao.update(user);
    }



    @Override
    @Transactional
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}