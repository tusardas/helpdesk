package com.heytusar.helpdesk.repository;

import com.heytusar.helpdesk.model.User;

import org.json.JSONObject;

public interface UserRepositoryCustom {
    User saveUser(JSONObject json);
}
