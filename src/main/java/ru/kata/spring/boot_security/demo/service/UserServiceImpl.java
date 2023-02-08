package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    @Override
    public UserDTO findById(Long userId) {
        return getDtoFromUser(userRepository.findById(userId).get());
    }

    public List<UserDTO> allUsers() {
        List<UserDTO> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            users.add(getDtoFromUser(user));
        }
        return users;
    }

    @Override
    public List<User> allUser() {
        return userRepository.findAll();
    }


    public UserDTO saveUser(UserDTO userDTO) {
        User user = toUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User newUser = userRepository.save(user);
        return getDtoFromUser(newUser);
    }

    public void editUser(User user) {

        userRepository.save(user);
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public User getByUserName(String name) {
        return userRepository.findByName(name);
    }

    private User toUser(UserDTO userDTO) {

        User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRoles(getSetOfRoles(userDTO.getRoles()));
        return user;
    }

    private Set<Role> getSetOfRoles(List<String> nameRoles) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : nameRoles) {
            roles.add(roleService.findRoleByName(roleName));
        }
        return roles;
    }

    private UserDTO getDtoFromUser(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getLastName(), user.getAge(),
                user.getEmail(), user.getPassword(), getSetOfString(user.getRoles()));
    }

    public List<String> getSetOfString(Set<Role> roles) {
        List<String> nameRoles = new ArrayList<>();
        for (Role role : roles) {
            nameRoles.add(role.getRole());
        }
        return nameRoles;
    }
    public UserDTO getUserByName(String name) {
        return getDtoFromUser(userRepository.findByName(name));
    }

    public Set<String> getNameRoles() {
        Set<String> nameRoles = new HashSet<>();
        for (Role role : roleService.getRoles()) {
            nameRoles.add(role.getRole());
        }
        return nameRoles;
    }
}