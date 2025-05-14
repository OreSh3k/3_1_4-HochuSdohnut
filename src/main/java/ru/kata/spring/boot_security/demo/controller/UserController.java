package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    //Список всех юзеров
    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }


    // Форма для добавления
    @GetMapping("/addUserForm")
    public String showAddUserForm() {
        return "addUser";  // Имя HTML страницы (addUserForm.html)
    }

    //Добавление юзера
    @PostMapping("/add")
    public String add(
            @RequestParam("name") String name,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            Model model) {

        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);

        userService.addUser(user);

        return "redirect:/users";
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

    //Удаления юзера
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }


    @GetMapping("/testAdd")
    @ResponseBody
    public String testAdd() {
        User user = new User();
        user.setName("Test");
        user.setLastName("User");
        user.setEmail("test@example.com");
        userService.addUser(user);
        return "OK";
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
