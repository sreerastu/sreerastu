package com.app.sreerastu.controllers;

import com.app.sreerastu.domain.Admin;
import com.app.sreerastu.domain.User;
import com.app.sreerastu.domain.Vendor;
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


        Object obj = vendorService.findUserOrVendorOrAdminByEmailAddress(loginCredentials.getEmailAddress());

        String role = null;
        UserDetails userDetails = null;
        switch (obj.getClass().getSimpleName()) {
            case "User":
                role = ((User) obj).getRole().toString();
                userDetails = this.userService.loadUserByUsername(loginCredentials.getEmailAddress());
                break;
            case "Vendor":
                role = ((Vendor) obj).getRole().toString();
                userDetails = this.vendorService.loadUserByUsername(loginCredentials.getEmailAddress());
                break;
            case "Admin":
                role = ((Admin) obj).getRole().toString();
                userDetails = this.adminService.loadUserByUsername(loginCredentials.getEmailAddress());
                break;
            default:
                throw new UsernameNotFoundException("User not found with email address: " + loginCredentials.getEmailAddress());
        }

        if (userDetails != null) {
            String token = this.jwtUtil.generateToken(userDetails, role);
            log.info("JWT" + token);
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            throw new UsernameNotFoundException("User not found with email address: " + loginCredentials.getEmailAddress());
        }
    }

    @PostMapping("/resetPassword/{emailAddress}")
    public ResponseEntity<?> resetPassword(@PathVariable String emailAddress) throws Exception {

        mailService.sendMail(emailAddress);

        return ResponseEntity.status(HttpStatus.OK).body("Mail Sent Successfully......!");

    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        Object userDetails = vendorService.findUserOrVendorOrAdminByEmailAddress(username);
        return ResponseEntity.ok(userDetails);
    }


    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginApiDto loginApiDto) throws AuthenticationException {
        User authenticate = userService.authenticate(loginApiDto.getEmailAddress(), loginApiDto.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(authenticate);
    }
}
