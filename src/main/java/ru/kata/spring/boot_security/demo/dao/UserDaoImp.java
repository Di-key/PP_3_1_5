package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<User> readAllUsers() {
        return manager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void create(User user) {
        manager.persist(user);
    }

    @Override
    public User update(User user) {

        return manager.merge(user);
    }


    @Override
    public User findUser(Long id) {
        return manager.find(User.class, id);
    }

    @Override
    public void delete(Long id) {
        manager.createQuery("delete from User where id =: id").setParameter("id", id).executeUpdate();
    }

    @Override
    public User findByEmail(String email) {
        TypedQuery<User> user = (TypedQuery<User>) manager.createQuery(
                        "select u from User u left join fetch u.roles where u.email=:pname").
                setParameter("pname", email);
        return user.getSingleResult();
    }

}