package com.app.sreerastu.controllers;

import com.app.sreerastu.dto.JwtResponse;
import com.app.sreerastu.dto.LoginApiDto;
import com.app.sreerastu.exception.AuthenticationException;
import com.app.sreerastu.services.AdminServiceImpl;
import com.app.sreerastu.services.MailService;
import com.app.sreerastu.services.UserServiceImpl;
import com.app.sreerastu.services.VendorServiceImpl;
import com.app.sreerastu.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class AuthenticationController {

    final static Logger log = LoggerFactory.getLogger(AuthenticationController.class);

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

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginApiDto loginCredentials) throws AuthenticationException {

        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginCredentials.getEmailAddress(), loginCredentials.getPassword()));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AuthenticationException("Invalid details provided");
        }
        try {
            String token;
            UserDetails vendorDetails = this.vendorService.loadUserByUsername(loginCredentials.getEmailAddress());
            if (vendorDetails != null) {
                String role = vendorDetails.getAuthorities().iterator().next().getAuthority();
                token = this.jwtUtil.generateToken(vendorDetails, role);
                log.info("JWT" + token);
                return ResponseEntity.ok(new JwtResponse(token));
            }
            UserDetails userDetails = this.userService.loadUserByUsername(loginCredentials.getEmailAddress());
            if (userDetails != null) {
                String role = userDetails.getAuthorities().iterator().next().getAuthority();
                token = this.jwtUtil.generateToken(userDetails, role);
                log.info("JWT" + token);
                return ResponseEntity.ok(new JwtResponse(token));
            }
            UserDetails adminDetails = this.adminService.loadUserByUsername(loginCredentials.getEmailAddress());
            if (adminDetails != null) {
                String role = adminDetails.getAuthorities().iterator().next().getAuthority();
                token = this.jwtUtil.generateToken(adminDetails, role);
                log.info("JWT" + token);
                return ResponseEntity.ok(new JwtResponse(token));
            }
        } catch (Exception ex) {
            throw new UsernameNotFoundException("User not found with email address: " + loginCredentials.getEmailAddress());
        }
        return null;
    }

    @PostMapping("/resetPassword/{emailAddress}")
    public ResponseEntity<?> resetPassword(@PathVariable String emailAddress) throws Exception {

        mailService.sendMail(emailAddress);

        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
