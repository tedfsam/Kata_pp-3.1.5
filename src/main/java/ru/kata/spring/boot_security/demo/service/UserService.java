package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    User findUserById(Long userId);

    UserDTO findById(Long userId);

    List<UserDTO> allUsers();

    List<User> allUser();

    UserDTO saveUser(UserDTO user);

    void editUser(User user);

    boolean deleteUser(Long userId);

    User getByUserName(String name);

    UserDTO getUserByName(String name);

    Set<String> getNameRoles();
}