package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.EmailService;
import com.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    private  Helper helper;
    // constructor injection
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, EmailService emailService, Helper helper) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.helper = helper;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        // generate user id
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);

        // Password encoding
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set the user role
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        logger.info(user.getProvider().toString());

        
        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User savedUser =  userRepo.save(user);
        String emailLink = helper.getEmailVerificaitonLink(emailToken);

        emailService.sendEmail(savedUser.getEmail(), "Verify Account: Smart Contact Manager", emailLink);

        return savedUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User tempUser =  userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not found"));

        // update tempUser from user
        tempUser.setName(user.getName());
        tempUser.setEmail(user.getEmail());
        tempUser.setPassword(user.getPassword());
        tempUser.setPhoneNumber(user.getPhoneNumber());
        tempUser.setAbout(user.getAbout());
        tempUser.setProfilePic(user.getProfilePic());
        tempUser.setEnabled(user.isEnabled());
        tempUser.setEmailVerified(user.isEmailVerified());
        tempUser.setPhoneVerified(user.isPhoneVerified());
        tempUser.setProvider(user.getProvider());
        tempUser.setProviderUserId(user.getProviderUserId());

        // save the updated user in database
        User save = userRepo.save(tempUser);
        return Optional.ofNullable(save);

    }

    @Override
    public void deleteUser(String id) {
        User tempUser =  userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        userRepo.delete(tempUser);
    }

    @Override
    public boolean isUserExist(String userId) {
        User tempUser =  userRepo.findById(userId).orElse(null);
        return tempUser!=null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User tempUser = userRepo.findByEmail(email).orElse(null);
        return tempUser!=null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public void findByEmailToken(String token) {
        // return userRepo.findByEmailToken(token).orElse(null);
    }

}
