package com.scm.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.repositories.UserRepo;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {

    // Constructor injection
    private UserRepo userRepo;
    public SecurityCustomUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // load the user
        return userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

}
