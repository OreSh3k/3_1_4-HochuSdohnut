package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.AdminService;

import java.util.Set;

@Controller
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;

    }


    //Удаления юзера
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") int id) {
        adminService.deleteUserById(id);
        return "redirect:/users";
    }

    // Форма для добавления
    @GetMapping("/addUserForm")
    public String showAddUserForm() {
        return "addUser";  // Имя HTML страницы (addUserForm.html)
    }

    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User()); // Добавляем пустой объект User
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        adminService.addUser(user);
        return "redirect:/users";
    }

}
