package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public void updateUser(User user);

    Optional<User> findByUsername(String username);

    public List<User> findUserByNameOrEmail(String name, String email);

    public List<User> getAllUsers();

    public Optional<User> findUser(int id);
}
