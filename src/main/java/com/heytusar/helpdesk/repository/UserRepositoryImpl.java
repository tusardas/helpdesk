package com.heytusar.helpdesk.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.heytusar.helpdesk.model.User;

import org.json.JSONObject;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public User saveUser(JSONObject json) {
        User user = new User();
        user.setFirstName(json.getString("firstName"));
        user.setLastName(json.getString("lastName"));
        user.setEmail(json.getString("email"));
        entityManager.persist(user);
        return user;
    }
    
}
