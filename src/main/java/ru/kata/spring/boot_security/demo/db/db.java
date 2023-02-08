//package ru.kata.spring.boot_security.demo.db;//package ru.kata.spring.boot_security.demo.db;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import ru.kata.spring.boot_security.demo.dto.UserDTO;
//import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.service.RoleService;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//@Component
//public class db {
//    private final UserService userService;
//    private final RoleService roleService;
//
//    @Autowired
//      public db(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    @PostConstruct
//    public void initDB() {
//        Role roleAdmin = new Role(1L, "ROLE_ADMIN");
//        Role roleUser = new Role(2L, "ROLE_USER");
//        List<String> adminList = new ArrayList<>();
//        List<String> userList = new ArrayList<>();
//
//        adminList.add("ROLE_ADMIN");
//        adminList.add("ROLE_USER");
//
//        UserDTO user = new UserDTO("a1", "al2",21,"a1@e1.ru","123", adminList);
////        user.setId(1L);
//
//        userService.saveUser(user);
//    }
//}
