package com.example.UserManagement.repositories;

import com.example.UserManagement.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    User findUserByEmail(String email);
}
