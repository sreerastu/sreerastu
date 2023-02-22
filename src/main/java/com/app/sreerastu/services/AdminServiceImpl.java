package com.app.sreerastu.services;

import com.app.sreerastu.domain.Admin;
import com.app.sreerastu.exception.AdminNotFoundException;
import com.app.sreerastu.repositories.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {


    //  @Autowired
    private AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Override
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin updateAdmin(int adminId, Admin admin) throws AdminNotFoundException {

        Admin existingAdmin = adminRepository.findById(adminId).orElseThrow(() -> new AdminNotFoundException("Invalid adminId"));
        existingAdmin.setFirstName(admin.getFirstName());
        existingAdmin.setLastName(admin.getLastName());
        existingAdmin.setContactNumber(admin.getContactNumber());
        //existingAdmin.setDob(admin.getDob());
        existingAdmin.setEmailAddress(admin.getEmailAddress());
        existingAdmin.setPassword(admin.getPassword());
        return adminRepository.save(existingAdmin);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdminById(int adminId) throws AdminNotFoundException {
        return adminRepository.findById(adminId).orElseThrow(() -> new AdminNotFoundException("Invalid adminId"));
    }

    @Override
    public String deleteAdminById(int adminId) throws AdminNotFoundException {
        try {
            adminRepository.deleteById(adminId);
            return "Admin Successfully deleted";
        } catch (Exception ex) {
            throw new AdminNotFoundException("Invalid adminId");
        }
    }

    @Override
    public Admin authenticate(String emailAddress, String password) {
        return adminRepository.findByEmailAddressAndPassword(emailAddress, password);

/*        if (Objects.isNull(loginResult)) {
            throw new AuthenticationException("Invalid credentials");
        }
        return "Login Successful";*/
    }

}
