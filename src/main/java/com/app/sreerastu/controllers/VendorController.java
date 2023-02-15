package com.app.sreerastu.controllers;

import com.app.sreerastu.Enum.VendorCategory;
import com.app.sreerastu.domain.Vendor;
import com.app.sreerastu.exception.DuplicateVendorException;
import com.app.sreerastu.exception.InvalidVendorIdException;
import com.app.sreerastu.exception.VendorNotFoundException;
import com.app.sreerastu.services.VendorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class VendorController {

    public VendorController(VendorServiceImpl vendorService) {
        this.vendorService = vendorService;
    }

    //  @Autowired
    private VendorServiceImpl vendorService;

    @PostMapping("/vendor")
    public ResponseEntity<?> createVendor(@RequestBody Vendor vendor) throws DuplicateVendorException {

        Vendor createdVendor = vendorService.createVendor(vendor);
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
    public ResponseEntity<?> getVendorByVendorCategory(@RequestParam("vendorCategory") VendorCategory vendorCategory) throws VendorNotFoundException {

      List<Vendor> vendors = vendorService.getVendorsByCategoryType(vendorCategory);
        return ResponseEntity.status(HttpStatus.OK).body(vendors);
    }
}
