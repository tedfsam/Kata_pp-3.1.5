package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest/admin")
public class RestAdminController {

    private final UserService userService;

    public RestAdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public UserDTO newUser(@RequestBody UserDTO user) {
        return userService.saveUser(user);
    }

    @PutMapping("/users")
    public UserDTO updateUser(@RequestBody UserDTO user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User was deleted";
    }

    @GetMapping("/users")
    public List<UserDTO> allUsers() {
        return userService.allUsers();
    }

    @GetMapping("/users/{id}")
    public UserDTO userById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        UserDTO currentUser = userService.getUserByName(principal.getName());
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<String>> getAllRoles() {
        return new ResponseEntity<>(userService.getNameRoles(), HttpStatus.OK);
    }

}
