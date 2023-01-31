package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private  final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String findAllUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("users", userService.allUser()); //TODO
        model.addAttribute("user", authentication.getPrincipal());
        return "admin/admin";
    }

    @GetMapping("/add")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "add";
    }

    @PostMapping(value = "/edit")
    public String editUser(@RequestParam Long id, @RequestParam String firstName,
                           @RequestParam String lastName, @RequestParam String age,
                           @RequestParam String email, @RequestParam String password,
                           @RequestParam(required = false) List<String> roleList) {
        User user = userService.findUserById(id);
        user.setName(firstName);
        user.setLastName(lastName);
        user.setAge(Byte.parseByte(age));
        user.setEmail(email);
        user.setPassword(password);
        if (roleList != null) {
            user.setRoles(new HashSet<>());
            for (String s : roleList) {
                user.getRoles().add(roleService.findRoleByName(s));
            }
        }
        userService.editUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/add")
    public String addUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam byte age,
                          @RequestParam String email, @RequestParam String password, @RequestParam List<String> roleList) {
        UserDTO user = new UserDTO(firstName, lastName, age, email, password, roleList);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
    @GetMapping(value = "/user-admin")
    public String user(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUserName(authentication.getName());
        model.addAttribute("user", user);
        return "admin/user-admin";
    }
}