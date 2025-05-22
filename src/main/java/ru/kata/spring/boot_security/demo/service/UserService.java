package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    List<User> findAllUsers();
    Optional<User> findById(int id);
    Optional<User> updateUser(int id, UserDTO updatedUserDTO);
    boolean deleteById(int id);
    public User save(User user);
}
