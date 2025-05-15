package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Optional;


@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    //Форма для редактирования
    @GetMapping("/editUserForm")
    public String showEditUserForm(@RequestParam("id") int id, Model model) {
        Optional<User> user = userService.findUser(id);
        model.addAttribute("user", user);
        return "editUser"; // Это HTML-страница для редактирования
    }

    //Ред. юзера
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }

    //Список всех юзеров
    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }


    @GetMapping("/search")
    public String searchUser(@RequestParam(required = false) String name,
                             @RequestParam(required = false) String email,
                             Model model) {
        List<User> users = userService.findUserByNameOrEmail(name, email);
        model.addAttribute("users", users);
        return "users";
    }


}
