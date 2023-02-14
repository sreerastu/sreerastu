package com.app.sreerastu.controllers;

import com.app.sreerastu.domain.Admin;
import com.app.sreerastu.domain.Vendor;
import com.app.sreerastu.exception.AdminNotFoundException;
import com.app.sreerastu.exception.VendorNotFoundException;
import com.app.sreerastu.services.AdminServiceImpl;
import com.app.sreerastu.services.VendorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class AdminController {

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    // @Autowired
    private AdminServiceImpl adminService;

  /*  public AdminController(VendorServiceImpl vendorService) {
        this.vendorService = vendorService;
    }

    private VendorServiceImpl vendorService;
*/
    @PostMapping("/admin")
    public ResponseEntity<?> createAdmin(@RequestBody Admin admin) {

        Admin createdAdmin = adminService.createAdmin(admin);
        return ResponseEntity.status(HttpStatus.OK).body(createdAdmin);
    }

    @PutMapping("/admin/{adminId}")
    public ResponseEntity<?> updateAdmin(@PathVariable int adminId,
                                         @RequestBody Admin adminX) throws AdminNotFoundException {
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
  /*  @GetMapping("/admin/getUnapprovedVendors")
    public ResponseEntity<?> getUnapprovedVendors() throws VendorNotFoundException {
        Vendor vendor = new Vendor();
        if (vendor.getIsApproved()==false) {
            List<Vendor> unApprovedVendors = vendorService.getAllVendors();
          //  unApprovedVendors.stream().map(n->n)
            return ResponseEntity.status(HttpStatus.OK).body(unApprovedVendors);
        }else
            throw new VendorNotFoundException("Vendors not found");
    }*/

}
