package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.AdminService;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.Set;

@Controller
public class AdminController {

    private AdminService adminService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(AdminService adminService,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        this.adminService = adminService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;

    }

    //Удаления юзера
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") int id) {
        adminService.deleteUserById(id);
        return "redirect:/users";
    }

    // Форма для добавления
    @GetMapping("/addUserForm")
    public String showAddForm(Model model) {
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String name,
                          @RequestParam String lastName,
                          @RequestParam String email,
                          @RequestParam Set<Long> roles) {

        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        Set<Role> userRoles = roleService.findRolesByIds(roles);
        user.setRoles(userRoles);

        adminService.addUser(user);

        System.out.println("Пользователь создан");
        return "redirect:/users";
    }

}
