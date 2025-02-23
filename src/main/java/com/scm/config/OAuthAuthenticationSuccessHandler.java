package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    UserRepo userRepo;
    public OAuthAuthenticationSuccessHandler(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                logger.info("oAuthAuthenticationSuccessHandler");

                // identify the provider
                var oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
                String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
                logger.info(authorizedClientRegistrationId);

                var oauthUser = (DefaultOAuth2User)authentication.getPrincipal();

                oauthUser.getAttributes().forEach((key, value) -> {
                    logger.info(key + " : " + value);
                });

                User user = new User();
                user.setUserId(UUID.randomUUID().toString());
                user.setRoleList(List.of(AppConstants.ROLE_USER));
                user.setEmailVerified(true);
                user.setEnabled(true);
                user.setPassword("local");

                if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
                    // google login attribute
                    user.setEmail(oauthUser.getAttribute("email").toString());
                    user.setProfilePic(oauthUser.getAttribute("picture").toString());
                    user.setName(oauthUser.getAttribute("name").toString());
                    user.setProviderUserId(oauthUser.getName());
                    user.setProvider(Providers.GOOGLE);
                    user.setAbout("This account is created using google");

                } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
                    // github login attributes
                    String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString()+"@gmail.com";
                    String picture = oauthUser.getAttribute("avatar_url").toString();
                    String name = oauthUser.getAttribute("login").toString();
                    String providerUserId = oauthUser.getName();
                    user.setEmail(email);
                    user.setProfilePic(picture);
                    user.setName(name);
                    user.setProviderUserId(providerUserId);
                    user.setProvider(Providers.GITHUB);
                    user.setAbout("This account is created using github");
                    
                } else {
                    logger.info("OAuthAuthenticationSuccessHandler: unknown provider");
                }
                
                // save the user
                User dataBaseUser = userRepo.findByEmail(user.getEmail()).orElse(null);
                if (dataBaseUser==null) {
                    userRepo.save(user);
                    logger.info("user saved: " + user.getEmail());
                }



                /* 
                DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();
                // logger.info(user.getName());
                // user.getAttributes().forEach((key, value) -> {
                //     logger.info("{} => {}",key, value);
                // });
                // logger.info(user.getAuthorities().toString());

                String email = user.getAttribute("email").toString();
                String name = user.getAttribute("name").toString();
                String picture = user.getAttribute("picture").toString();

                // save the user in database
                User tempUser = new User();
                tempUser.setEmail(email);
                tempUser.setName(name);
                tempUser.setProfilePic(picture);
                tempUser.setPassword("password");
                tempUser.setUserId(UUID.randomUUID().toString());
                tempUser.setProvider(Providers.GOOGLE);
                tempUser.setEnabled(true);
                tempUser.setEmailVerified(true);
                tempUser.setProviderUserId(user.getName());
                tempUser.setRoleList(List.of(AppConstants.ROLE_USER));
                tempUser.setAbout("This account is created using google.");

                User dataBaseUser = userRepo.findByEmail(email).orElse(null);
                if (dataBaseUser==null) {
                    userRepo.save(tempUser);
                    logger.info("User Saved: ", email);
                }
                    */

                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

        
    }


}
