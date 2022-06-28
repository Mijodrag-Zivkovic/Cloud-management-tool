package com.example.UserManagement.controllers;

import com.example.UserManagement.model.Permission;
import com.example.UserManagement.model.Permissions;
import com.example.UserManagement.model.User;
import com.example.UserManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    //private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
        //this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(){
        if(userService.isAuthorised(SecurityContextHolder.getContext().getAuthentication().getName(),Permissions.CAN_READ_USERS))
        {
            return ResponseEntity.ok(userService.findAll());
        }
        else
        {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        if(userService.isAuthorised(SecurityContextHolder.getContext().getAuthentication().getName(),Permissions.CAN_READ_USERS))
        {
            Optional<User> optionalStudent = userService.findById(id);
            if(optionalStudent.isPresent()) {
                return ResponseEntity.ok(optionalStudent.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        else
        {
            return ResponseEntity.status(403).build();
        }

    }

//    @GetMapping(value="/email/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getUserById(@PathVariable String email){
//        //if(userService.isAuthorised(SecurityContextHolder.getContext().getAuthentication().getName(),Permissions.CAN_READ_USERS))
//        {
//            User user = userService.findUserByEmail(email);
//            if(!user.equals(null)) {
//                return ResponseEntity.ok(user);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        }
////        else
////        {
////            return ResponseEntity.status(403).build();
////        }
//
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user){
        if(userService.isAuthorised(SecurityContextHolder.getContext().getAuthentication().getName(),Permissions.CAN_CREATE_USERS))
        {
            //user.setPassword(passwordEncoder.encode(user.getPassword()));
            return ResponseEntity.ok(userService.save(user));
        }
        else
        {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody User user){
        if(userService.isAuthorised(SecurityContextHolder.getContext().getAuthentication().getName(),Permissions.CAN_UPDATE_USERS))
        {
            //user.setPassword(passwordEncoder.encode(user.getPassword()));
            return ResponseEntity.ok(userService.save(user));
        }
        else
        {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        if(userService.isAuthorised(SecurityContextHolder.getContext().getAuthentication().getName(),Permissions.CAN_DELETE_USERS))
        {
            Optional<User> optionalUser = userService.findById(id);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();

                for (int i = 0; i < user.getPermissions().size(); i++) {
                    user.getPermissions().get(i).getUsers().remove(user);
                }
                userService.deleteById(id);

                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }
        else
        {
            return ResponseEntity.status(403).build();
        }
    }

}
