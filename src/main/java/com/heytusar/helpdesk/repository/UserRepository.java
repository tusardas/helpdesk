package com.heytusar.helpdesk.repository;
import com.heytusar.helpdesk.model.User;

import org.springframework.data.repository.CrudRepository;
public interface UserRepository  extends CrudRepository<User, Long>,  UserRepositoryCustom {
    User findByEmail(String email);
}
