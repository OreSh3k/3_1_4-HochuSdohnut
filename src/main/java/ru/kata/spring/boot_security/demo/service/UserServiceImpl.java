package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;

        public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        public Optional<User> findByUsername(String username) {
            return userRepository.findByUsername(username);
        }

        @Override
        public List<User> findAllUsers() {
            return userRepository.findAll();
        }

        @Override
        public Optional<User> findById(int id) {
            return userRepository.findById(id);
        }

    @Override
    public Optional<User> updateUser(int id, UserDTO userDTO) {
        return userRepository.findById(id).map(existingUser -> {
            // 🔁 Обновление полей
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setName(userDTO.getName());
            existingUser.setLastName(userDTO.getLastName());
            existingUser.setEmail(userDTO.getEmail());

            // 🔄 Преобразование ролей по именам
            Set<Role> roles = userDTO.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new RuntimeException("Роль не найдена: " + roleName)))
                    .collect(Collectors.toSet());

            existingUser.setRoles(roles);

            return userRepository.save(existingUser);
        });
    }
        @Override
        public boolean deleteById(int id) {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return true;
            }
            return false;
        }

    public User save(User user) {
        // Инициализация пароля
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Получаем роли или создаем новый Set, если null
        Set<Role> roles = user.getRoles() != null ? user.getRoles() : new HashSet<>();

        // Если роли пустые, устанавливаем роль по умолчанию
        if (roles.isEmpty()) {
            Role defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Default role ROLE_USER not found"));
            roles.add(defaultRole);
        }

        // Маппинг ролей из базы данных
        Set<Role> mappedRoles = roles.stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role.getName())))
                .collect(Collectors.toSet());

        user.setRoles(mappedRoles);
        return userRepository.save(user);
    }
    }

