package com.app.sreerastu.controllers;

import com.app.sreerastu.Enum.VendorCategory;
import com.app.sreerastu.domain.Admin;
import com.app.sreerastu.domain.Vendor;
import com.app.sreerastu.exception.DuplicateVendorException;
import com.app.sreerastu.exception.InvalidVendorIdException;
import com.app.sreerastu.exception.VendorNotFoundException;
import com.app.sreerastu.services.AdminServiceImpl;
import com.app.sreerastu.services.UserServiceImpl;
import com.app.sreerastu.services.VendorServiceImpl;
import com.app.sreerastu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class VendorController {

    @Value("${spring.mail.username}")
    private String sender;
    private VendorServiceImpl vendorService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private JwtUtil jwtUtil;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public VendorController(VendorServiceImpl vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/vendor")
    public ResponseEntity<?> createVendor(@RequestBody Vendor vendor) throws DuplicateVendorException {

        Vendor createdVendor = vendorService.createVendor(vendor);
        //BcryptPasswordEncoder
      //  vendor.setPassword(this.bCryptPasswordEncoder.encode(vendor.getPassword()));
        return ResponseEntity.status(HttpStatus.OK).body(createdVendor);
        // body("Your Application is Under Processing,and Approved with in 24hrs");
    }

    @PutMapping("/vendor/{vendorId}")
    public ResponseEntity<?> updateVendor(@PathVariable int vendorId,
                                          @RequestBody Vendor vendorX) throws InvalidVendorIdException {
        Vendor vendor = vendorService.updateVendor(vendorId, vendorX);
        return ResponseEntity.status(HttpStatus.OK).body(vendor);
    }

    @GetMapping("/vendors")
    public ResponseEntity<?> getAllVendors() {

        List<Vendor> vendors = vendorService.getAllVendors();
        return ResponseEntity.status(HttpStatus.OK).body(vendors);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<?> getVendorById(@PathVariable int vendorId) throws VendorNotFoundException {

        Vendor vendor = vendorService.getVendorById(vendorId);
        return ResponseEntity.status(HttpStatus.OK).body(vendor);
    }

    @DeleteMapping("/vendor/{vendorId}")
    public ResponseEntity<?> deleteVendorById(@PathVariable int vendorId) throws VendorNotFoundException {
        vendorService.deleteVendor(vendorId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/vendors/categories")
    public ResponseEntity<?> getVendorByVendorCategory(@RequestParam("vendorCategory") VendorCategory vendorCategory,
                                                       @RequestHeader("Authorization") String tokenHeader) throws Exception {

        List<Vendor> vendors = vendorService.getVendorsByCategoryType(vendorCategory);
        String token = tokenHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        Object userDetails = vendorService.findUserOrVendorOrAdminByEmailAddress(username);
        Admin adminById = adminService.getAdminById(3);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(adminById.getEmailAddress());
        message.setSubject("New vendor category selected");
        message.setText("A user with Id:" + userDetails + "has selected the vendor categories!");
        javaMailSender.send(message);
        return ResponseEntity.status(HttpStatus.OK).body(vendors);

    }

    @PatchMapping("/vendor/{vendorId}")
    public ResponseEntity<?> updateAnyFields(@PathVariable int vendorId,
                                             @RequestParam Map<String, Object> fields) throws InvalidVendorIdException {
        Vendor saved = vendorService.updateVendorByFields(vendorId, fields);
        return ResponseEntity.status(HttpStatus.OK).body(saved);

    }

    @PostMapping("/vendor/updateStatus/{vendorId}")
    public ResponseEntity<?> updateVendorStatus(@PathVariable int vendorId) throws VendorNotFoundException {
        Vendor vendor = vendorService.updateVendorStatus(vendorId);
        return ResponseEntity.status(HttpStatus.OK).body(vendor);

    }

  /*  @PostMapping("/vendors/{id}/image")
    public ResponseEntity<?> uploadImage(@PathVariable int vendorId, @RequestParam("file") MultipartFile file) throws VendorNotFoundException {
        Vendor vendor = vendorService.getVendorById(vendorId);

        try {
            vendor.setLogo(file.getBytes());
            vendorRepository.save(vendor);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image");
        }
    }

    @GetMapping("/vendors/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int vendorId) throws VendorNotFoundException {
        Vendor vendor = vendorRepository.findById(vendorId).get();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(vendor.getLogo().length);
        return new ResponseEntity<>(vendor.getLogo(), headers, HttpStatus.OK);
    }
*/


}
