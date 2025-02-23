package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.repositories.UserRepo;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {


    private UserService userService;
    private UserRepo userRepo;
    public AuthController(UserService service, UserRepo repo) {
        this.userService = service;
        this.userRepo = repo;
    }

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam String token, HttpSession session) {

        // userService.findByEmailToken(token);

        User user = userRepo.findByEmailToken(token).orElse(null);
        if (user!=null) {

            if (user.getEmailToken().equals(token)) {
                user.setEmailVerified(true);
                user.setEnabled(true);

                // userService.saveUser(user);
                userRepo.save(user);
                session.setAttribute("message", Message.builder().content("Email verified, your acocount is enabled!").type(MessageType.green).build());
                return "success_page";
            }

            session.setAttribute("message", Message.builder().content("Something went wrong, your email is not verified!").type(MessageType.red).build());
            return "error_page";
        }

        session.setAttribute("message", Message.builder().content("Something went wrong, your email is not verified!").type(MessageType.red).build());
        return "error_page";
    }
}
