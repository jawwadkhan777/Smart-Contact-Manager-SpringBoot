package com.scm.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    // constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private Logger logger = LoggerFactory.getLogger(UserController.class);


    // user dashboard page
    @RequestMapping(value="/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }
    

    // user profile page
    @RequestMapping(value="/profile")
    public String userProfile(Model model, Authentication authentication) {

        return "user/profile";
    }


    // user add contacts page


    // user view contacts page


    // user edit contacts pade


    // user delete contacts page

}
