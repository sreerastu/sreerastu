package com.app.sreerastu.controllers;

import com.app.sreerastu.domain.Admin;
import com.app.sreerastu.domain.Vendor;
import com.app.sreerastu.exception.AdminNotFoundException;
import com.app.sreerastu.exception.VendorNotFoundException;
import com.app.sreerastu.services.AdminServiceImpl;
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
public class AdminController {

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    private AdminServiceImpl adminService;

    @Autowired
    private VendorServiceImpl vendorService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/admin")
    public ResponseEntity<?> createAdmin(@RequestBody Admin admin) {

        Admin createdAdmin = adminService.createAdmin(admin);
        //BcryptPasswordEncoder
        admin.setPassword(this.bCryptPasswordEncoder.encode(admin.getPassword()));
        return ResponseEntity.status(HttpStatus.OK).body(createdAdmin);
    }

    @PutMapping("/admin/{adminId}")
    public ResponseEntity<?> updateAdmin(@PathVariable int adminId, @RequestBody Admin adminX) throws AdminNotFoundException {
        Admin admin = adminService.updateAdmin(adminId, adminX);
        return ResponseEntity.status(HttpStatus.OK).body(admin);
    }

    @GetMapping("/admins")
    public ResponseEntity<?> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.status(HttpStatus.OK).body(admins);
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<?> getAdminById(@PathVariable int id) throws AdminNotFoundException {
        Admin admin = adminService.getAdminById(id);
        return ResponseEntity.status(HttpStatus.OK).body(admin);
    }

    @DeleteMapping("/admin/{adminId}")
    public ResponseEntity<?> deleteAdminById(@PathVariable int adminId) throws AdminNotFoundException {
        adminService.deleteAdminById(adminId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/admin/unApprovedVendors")
    public ResponseEntity<?> getAllUnApprovedVendors() throws VendorNotFoundException {
        List<Vendor> vendors = vendorService.getAllUnApprovedVendors();
        return ResponseEntity.status(HttpStatus.OK).body(vendors);
    }

    @PostMapping("/admin/approve/{vendorId}")
    public ResponseEntity<?> updateApprovedVendors(@PathVariable int vendorId) throws VendorNotFoundException {

        Vendor vendor = vendorService.updateIsApproved(vendorId);
        return ResponseEntity.status(HttpStatus.OK).body(vendor);
    }


}
