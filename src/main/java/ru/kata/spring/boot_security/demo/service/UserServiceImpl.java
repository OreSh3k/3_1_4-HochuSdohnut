package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Transactional()
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Optional<User> findUser(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findUserByNameOrEmail(String name, String email) {
        if(!name.isEmpty() && !email.isEmpty()) {
            return userRepository.findByNameAndEmail(name, email);
        }else if(!name.isEmpty()) {
            return userRepository.findByName(name);
        }else if(!email.isEmpty()) {
            return userRepository.findByEmail(email);
        }else{
            return new ArrayList<User>();
        }
    }


    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

