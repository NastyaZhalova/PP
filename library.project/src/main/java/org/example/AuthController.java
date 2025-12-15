package org.example;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthController {
    private final UsersXmlService users;

    public AuthController(UsersXmlService users) {
        this.users = users;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String role,
                           Model model) {
        try {
            if (!role.equals("LIBRARIAN") && !role.equals("READER")) {
                model.addAttribute("error", "Роль должна быть LIBRARIAN или READER");
                return "register";
            }
            users.add(username, password, role);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }


    @GetMapping("/post-login")
    public String postLogin(Authentication auth) {
        List<String> roles = auth.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList());

        if (roles.contains("ROLE_LIBRARIAN")) {
            return "redirect:/librarian/menu";
        }
        return "redirect:/reader/menu";
    }
}

