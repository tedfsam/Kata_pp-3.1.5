package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    public List<User> getAllUsers();

    void saveUser(User user);

    User getUserById(Long id);

    User getUserByUsername(String username);

    void deleteUserById(Long id);

    void updateUser(Long id, User updatedUser);
}