package com.heytusar.helpdesk.controller;

import java.util.Map;

import com.heytusar.helpdesk.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
    @Autowired
    private UserService userService;
	
    @RequestMapping(value="/user", method = RequestMethod.POST)
    public ResponseEntity<Map> saveUser(@RequestBody String jsonBody) throws Exception {
        Map models = userService.saveUser(jsonBody);
        return new ResponseEntity<Map>(models, HttpStatus.OK);
    }
	
    @RequestMapping(value="/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Map> getUserById(@PathVariable Long userId) throws Exception {
	Map models = userService.getUserById(userId);
	return new ResponseEntity<Map>(models, HttpStatus.OK);
    }
	
    @RequestMapping(value="/user", method = RequestMethod.GET)
    public ResponseEntity<Map> getAllUser() throws Exception {
        Map models = userService.getAllUser();
        return new ResponseEntity<Map>(models, HttpStatus.OK);
    }
}
