package com.app.sreerastu.controllers;

import com.app.sreerastu.domain.Admin;
import com.app.sreerastu.domain.User;
import com.app.sreerastu.domain.Vendor;
import com.app.sreerastu.dto.LoginApiDto;
import com.app.sreerastu.exception.AuthenticationException;
import com.app.sreerastu.services.AdminServiceImpl;
import com.app.sreerastu.services.MailService;
import com.app.sreerastu.services.UserServiceImpl;
import com.app.sreerastu.services.VendorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class AuthenticationController {

    public AuthenticationController(VendorServiceImpl vendorService) {
        this.vendorService = vendorService;
    }

    private VendorServiceImpl vendorService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private MailService mailService;


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginApiDto loginCredentials) throws AuthenticationException {

        // Check if the login credentials belong to a vendor
        Vendor vendor = vendorService.authenticate(loginCredentials.getEmailAddress()  , loginCredentials.getPassword());
        if (vendor != null) {
            return ResponseEntity.ok("Vendor logged in successfully!");
        }

        // Check if the login credentials belong to a user
        User user = userService.authenticate(loginCredentials.getEmailAddress(), loginCredentials.getPassword());
        if (user != null) {
            return ResponseEntity.ok("User logged in successfully!");
        }

        // Check if the login credentials belong to admin
        Admin admin = adminService.authenticate(loginCredentials.getEmailAddress(), loginCredentials.getPassword());

        if (admin != null) {
            return ResponseEntity.ok("Admin logged in successfully!");
        }

        // If none of the login credentials are valid, return an error response
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

    @PostMapping("/resetPassword/{emailAddress}")
    public ResponseEntity<?> resetPassword(@PathVariable String emailAddress) throws Exception {

        mailService.sendMail(emailAddress);

        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
