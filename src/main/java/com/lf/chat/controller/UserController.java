package com.lf.chat.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lf.chat.model.User;
import com.lf.chat.model.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public String getUser(Principal user) {
	if (user != null && user instanceof OAuth2Authentication) {
	    OAuth2Authentication authentication = (OAuth2Authentication) user;
	    Map<String, String> details = (Map<String, String>) authentication.getUserAuthentication().getDetails();
	    String email = details.get("email");
	    String name = details.get("name");
	    User userFromEmail = userRepo.findByEmail(email);
	    if (userFromEmail != null) {
		return userFromEmail.getName();
	    } else {
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setName(name);
		userRepo.save(newUser);
		return name;
	    }
	}
	return null;
    }
}
