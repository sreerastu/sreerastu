package com.app.sreerastu.controllers;

import com.app.sreerastu.dto.ApiResponse;
import com.app.sreerastu.dto.LoginApiResponse;
import com.app.sreerastu.exception.AuthenticationException;
import com.app.sreerastu.services.VendorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class AuthenticationController {

    private static final String STATUS = "status";
    private static final String LOGIN_SUCCESS = "Login Successful";


    public AuthenticationController(VendorServiceImpl vendorService) {
        this.vendorService = vendorService;
    }

    private VendorServiceImpl vendorService;


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginApiResponse loginCredentials) throws AuthenticationException {

        String loginResponse= vendorService.login(loginCredentials);


        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);

    }
}
