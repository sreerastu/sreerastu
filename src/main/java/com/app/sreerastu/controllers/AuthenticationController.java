package com.app.sreerastu.controllers;

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

import java.util.Arrays;
import java.util.List;

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

        String vendorLoginResponse = vendorService.login(loginCredentials);

     //   String userLoginResponse = userService.login(loginCredentials);
      //  String adminLoginResponse = adminService.login(loginCredentials);

        return ResponseEntity.status(HttpStatus.OK).body(vendorLoginResponse);
     //   return Arrays.asList(vendorLoginResponse, userLoginResponse, adminLoginResponse);

    }

    @PostMapping("/resetPassword/{emailAddress}")
    public ResponseEntity<?> resetPassword(@PathVariable String emailAddress) throws Exception {

        mailService.sendMail(emailAddress);

        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
