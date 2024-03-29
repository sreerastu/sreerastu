package com.app.sreerastu.controllers;

import com.app.sreerastu.domain.User;
import com.app.sreerastu.exception.DuplicateUserException;
import com.app.sreerastu.exception.InvalidUserIdException;
import com.app.sreerastu.services.UserServiceImpl;
import com.app.sreerastu.services.VendorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    private UserServiceImpl userService;


    @Autowired
    private VendorServiceImpl vendorService;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user) throws DuplicateUserException {

        User createdUser = userService.createUser(user);
        //BcryptPasswordEncoder

      //  user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        return ResponseEntity.status(HttpStatus.OK).body(createdUser);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId,
                                        @RequestBody User userX) throws InvalidUserIdException {
        User user = userService.updateUser(userId, userX);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> vendors = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(vendors);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable int userId) throws InvalidUserIdException {
        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId) throws InvalidUserIdException {
        userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
