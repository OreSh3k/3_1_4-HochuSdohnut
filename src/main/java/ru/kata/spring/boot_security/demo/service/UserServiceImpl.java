package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    @Override
    public void updateUser(User user) {
        if (!user.getPassword().equals(userRepository.findById(user.getId()).get().getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }


    @Override
    public List<User> findUserByNameOrEmail(String name, String email) {
        if (!name.isEmpty() && !email.isEmpty()) {
            return userRepository.findByNameAndEmail(name, email);
        } else if (!name.isEmpty()) {
            return userRepository.findByName(name);
        } else if (!email.isEmpty()) {
            return userRepository.findByEmail(email);
        } else {
            return new ArrayList<User>();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<User> findUser(int id) {
        return userRepository.findById(id);
    }
}


