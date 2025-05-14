package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public void addUser(User user);

    public void updateUser(User user);

    public void deleteUserById (int id);

    public Optional<User> findUser(int id);

    public List<User> findUserByNameOrEmail(String name, String email);

    public List<User> getAllUsers();
}
