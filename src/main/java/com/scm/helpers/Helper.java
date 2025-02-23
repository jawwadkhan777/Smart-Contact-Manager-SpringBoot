package com.scm.helpers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    @Value("${server.email-verification}")
    private String verificationUrl;


    public static String getEmailOfLoggedInUser(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken) {
            var oauth2AutheticationToken = (OAuth2AuthenticationToken)authentication;
            var clientId = oauth2AutheticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User)authentication.getPrincipal();
            String userName = "";

            if (clientId.equalsIgnoreCase("google")) {
                System.out.println("Getting email from google");
                userName = oauth2User.getAttribute("email").toString();
                
            } else if (clientId.equalsIgnoreCase("github")) {
                System.out.println("Getting email from github");
                userName = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString()+"@gmail.com";
            }
            return userName;
        } else {
            System.out.println("Getting email from local database");
            return authentication.getName();
        }
    }


    public String getEmailVerificaitonLink(String emailToken) {

        return "http://localhost:8081/auth/verify-email?token="+emailToken;

//        return this.verificationUrl+"/auth/verify-email?token="+emailToken;
    }
}
