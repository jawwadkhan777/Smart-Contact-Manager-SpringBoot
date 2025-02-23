package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;



@Controller
public class PageController {

    public UserService userService;
    // constructor injection
    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/") 
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");

        // sending data to view
        model.addAttribute("name", "Our Site");
        model.addAttribute("githubRepo", "https://github.com/jawwadkhan777");

        return "home";
    }

    // about page route
    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("About page loading");
        return "about";
    }


    // services page route
    @RequestMapping("/services")
    public String servicesPage(Model model) {
        model.addAttribute("isLogin", false);

        System.out.println("Services page loading");
        return "services";
    }

    // contact page route
    @GetMapping("/contact")
    public String contactPage() {
        return new String("contact");
    }

    // login page route
    @GetMapping("/login")
    public String loginPage() {
        return new String("login");
    }

    // signup page route
    @GetMapping("/signup")
    public String signupPage(Model model) {
        UserForm userForm = new UserForm();
        // userForm.setName("Jawwad");
        // userForm.setPhoneNumber("9090");
        // userForm.setAbout("Hey, I am the king!");
        model.addAttribute("userForm", userForm);
        return new String("signup");
    }


    // processing signup
    @RequestMapping(value = "/do-register", method=RequestMethod.POST)
    public String processSignup(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, HttpSession session) {
        System.out.println("Processing signup");
        
        // fetch the data from signup form
        // UserForm
        System.out.println(userForm);
        // validate the data from signup form
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        // save the data into database
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .phoneNumber(userForm.getPhoneNumber())
        // .about(userForm.getAbout())
        // .profilePic("/resource/static/images/default_profile.png")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());
        user.setEnabled(false);
        user.setProfilePic("/resource/static/images/default_profile.png");

        User savedUser = userService.saveUser(user);
        System.out.println("user saved");
        
        // message like registration successful
        Message message = Message.builder().content("Registration successful!").type(MessageType.green).build();
        session.setAttribute("message", message);

        // redirect to login page 
        return new String("redirect:/signup");

    }
    
    
}
