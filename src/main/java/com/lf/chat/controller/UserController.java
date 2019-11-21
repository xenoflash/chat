package com.lf.chat.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lf.chat.model.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lf.chat.model.User;
import com.lf.chat.model.repositories.UserRepository;

@RestController

public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
	private SessionRegistry sessionRegistry;

    Map<String,String> userSessionIdMap;

    public UserController() {
        this.userSessionIdMap = new HashMap<>();
    }


    @RequestMapping("/user")
    public String getUser(Principal user) {
	if (user != null && user instanceof OAuth2Authentication) {
	    OAuth2Authentication authentication = (OAuth2Authentication) user;
	    Map<String, String> details = (Map<String, String>) authentication.getUserAuthentication().getDetails();

	    String email = details.get(Constants.EMAIL);
	    String name = details.get(Constants.NAME);
	    User userFromEmail = userRepo.findByEmail(email);

	    if (userFromEmail != null) {
	        userSessionIdMap.put(details.get(Constants.ID),userFromEmail.getName());
		return userFromEmail.getName();
	    } else {
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setName(name);
		userSessionIdMap.put(details.get(Constants.ID),newUser.getName());
		userRepo.save(newUser);
		return name;
	    }
	}
	return null;
    }

//
//	public List<String> getUsersFromSessionRegistry() {
//    	List<String> users=sessionRegistry.getAllPrincipals().stream()
//				.filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
//				.map(sessionId->userSessionIdMap.get(sessionId.toString()))
//				.collect(Collectors.toList());
//		return users;
//	}

    @RequestMapping("/usersFromSessionRegistry")
	public List<String> getAllActiveUsers() {
		String currentUsername = getCurrentUserName();

		List<String> users=sessionRegistry.getAllPrincipals().stream()
				.filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty() && !((userSessionIdMap.get(u.toString())).equals(currentUsername)))
				.map(sessionId->userSessionIdMap.get(sessionId.toString()))
				.collect(Collectors.toList());
		return users;
	}

	protected boolean isAuthenticated() {
		return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
	}

	/**
	 * @return username of the current user
	 */
	protected String getCurrentUserName() {
		if (isAuthenticated()) {
			String sessionId=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userName=userSessionIdMap.get(sessionId);
			return userName;
		}
		return null;
	}


}
