package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String userPage2(Principal principal, Model model) {

        model.addAttribute("newUser", new User());
        Set<Role> roles = new HashSet<>(roleService.getAllRoles());
        model.addAttribute("allRoles", roles);
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("usera", user);

        model.addAttribute("users", userService.readAllUsers());
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        Set<Role> roles = new HashSet<>(roleService.getAllRoles());
        model.addAttribute("allroles", roles);
        return "/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.create(user);
        return "redirect:/admin";
    }

    @PatchMapping(value = "/updateInfo/{id}")
    public String updateUser(@ModelAttribute("user") User updatedUser) {
        userService.update(updatedUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}






