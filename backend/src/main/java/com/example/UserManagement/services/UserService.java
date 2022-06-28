package com.example.UserManagement.services;

import com.example.UserManagement.model.Machine;
import com.example.UserManagement.model.Permissions;
import com.example.UserManagement.model.User;
import com.example.UserManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findById(Long userID) {
        return userRepository.findById(userID);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

//    public User findUserByEmail(String email)
//    {
//        return userRepository.findUserByEmail(email);
//    }

    public void deleteById(Long studentID) {
        userRepository.deleteById(studentID);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User myUser = this.userRepository.findUserByEmail(email);
        if(myUser == null) {
            throw new UsernameNotFoundException("User name "+email+" not found");
        }

        return new org.springframework.security.core.userdetails.User(myUser.getEmail(), myUser.getPassword(), new ArrayList<>());
    }

    public boolean isAuthorised(String email, Permissions permissions){
        User user = userRepository.findUserByEmail(email);
        return user.getPermissions().stream().anyMatch(permission -> permission.getPermission().equals(permissions));
    }



}
