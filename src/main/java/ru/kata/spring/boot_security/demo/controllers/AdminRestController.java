package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }


    // 🔍 Получить всех пользователей
    @GetMapping("/users")
    public List<UserDTO> findAllUsers() {
        return userService.findAllUsers().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    // 🔍 Получить пользователя по ID
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable int id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(new UserDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✏️ Обновить пользователя
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser)
                .map(user -> ResponseEntity.ok(new UserDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ❌ Удалить пользователя
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        // Если роли не указаны, инициализируем пустой Set
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        User savedUser = userService.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
