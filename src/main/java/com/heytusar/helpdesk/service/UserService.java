package com.heytusar.helpdesk.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.heytusar.helpdesk.model.User;
import com.heytusar.helpdesk.repository.UserRepository;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
    private UserRepository userRepository;
	
    @Autowired
    public UserService(UserRepository userRepository) {
	    this.userRepository = userRepository;
    }
	
    public Map<String, Object> saveUser(String jsonBody) {
        JSONObject json = new JSONObject(jsonBody);
        User user = userRepository.saveUser(json);
        Map<String, Object> models = new HashMap<String, Object>();
        models.put("user", user);
        return models;
    }

    public Map getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new ResponseStatusException(
                HttpStatus.NO_CONTENT, "No person found"
            );
        }
        Map<String, Object> models = new HashMap<String, Object>();
        models.put("user", user);
	    return models;
    }

    public Map getAllUser() {
        Iterable<User> list = userRepository.findAll();
        Map<String, Object> models = new HashMap<String, Object>();
        models.put("users", list);
        return models;
    }

    public Map authCheck(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        log.info("Email 123 ---------------> " + email);
        Map<String, Object> models = new HashMap<String, Object>();
        User user = userRepository.findByEmail(email);
        String status = "failed";
        if(user == null) {
            models.put("message", "Invalid email/password");
        }
        else {
            status = "success";
            models.put("email", user.getEmail());

            Cookie cookie = new Cookie("helpdesk-email", user.getEmail());
            cookie.setMaxAge(100000);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            models.put("cookie", cookie);
        }
        models.put("status", status);
	    return models;
    }

    public User getLoggedInUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        log.info("cookies ------------> " + cookies.length);
        String email = null;
        User user = null;
        for(Cookie cookie : cookies) {
            log.info("cookie ------------> " + cookie.getName() + " => " + cookie.getValue());
            if(cookie.getName().equals("helpdesk-email")) {
                email = cookie.getValue();
                break;
            }
        }
        log.info("email -------> " + email);
        if(email != null) {
            user = userRepository.findByEmail(email);
        }
        return user;
    }
}
