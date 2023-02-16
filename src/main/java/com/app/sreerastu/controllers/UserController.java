package com.app.sreerastu.controllers;

import com.app.sreerastu.Enum.VendorStatus;
import com.app.sreerastu.domain.User;
import com.app.sreerastu.domain.Vendor;
import com.app.sreerastu.exception.DuplicateUserException;
import com.app.sreerastu.exception.InvalidUserIdException;
import com.app.sreerastu.exception.InvalidVendorIdException;
import com.app.sreerastu.exception.VendorNotFoundException;
import com.app.sreerastu.services.UserServiceImpl;
import com.app.sreerastu.services.VendorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    // @Autowired
    private UserServiceImpl userService;

  /*  public UserController(VendorServiceImpl vendorService) {
        this.vendorService = vendorService;
    }

    private VendorServiceImpl vendorService;*/


    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user) throws DuplicateUserException {

            User createdUser = userService.createUser(user);
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int id) throws InvalidUserIdException {
        User user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable int userId) throws InvalidUserIdException {
        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /*@PostMapping("/user/booking/{vendorId}/{vendorStatus}")
    public ResponseEntity<?> bookVendorById(@PathVariable int vendorId,
                                            @PathVariable VendorStatus vendorStatus) throws  InvalidVendorIdException {
        Vendor vendorById = vendorService.updateVendorStatus(vendorId,vendorStatus);


       // vendorById.setVendorStatus(VendorStatus.valueOf("HOLD"));
        return ResponseEntity.status(HttpStatus.OK).body(vendorById);
    }*/
}
