package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;

public interface AdminService {

    public void addUser(User user);


    public void deleteUserById(int id);

}
