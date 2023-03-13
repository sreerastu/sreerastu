package com.app.sreerastu.services;

import com.app.sreerastu.Enum.VendorCategory;
import com.app.sreerastu.Enum.VendorStatus;
import com.app.sreerastu.domain.User;
import com.app.sreerastu.domain.Vendor;
import com.app.sreerastu.exception.AuthenticationException;
import com.app.sreerastu.exception.DuplicateVendorException;
import com.app.sreerastu.exception.InvalidVendorIdException;
import com.app.sreerastu.exception.VendorNotFoundException;
import com.app.sreerastu.repositories.AdminRepository;
import com.app.sreerastu.repositories.UserRepository;
import com.app.sreerastu.repositories.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class VendorServiceImpl implements VendorService, UserDetailsService {

    final static Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;
    /*@Autowired
    private MailService mailService;*/

    @Autowired
    private BookingServiceImpl bookingService;


    public Vendor createVendor(Vendor vendor) throws DuplicateVendorException {
        try {
            return vendorRepository.save(vendor);
        } catch (Exception ex) {
            throw new DuplicateVendorException("vendor aleardy exists");
        }
    }

    @Override
    public Vendor updateVendor(int vendorId, Vendor vendor) throws InvalidVendorIdException {
        Vendor existingVendor = vendorRepository.findById(vendor.getVendorId()).orElseThrow(() -> new InvalidVendorIdException("please enter a valid vendorId"));
        existingVendor.setFirstName(vendor.getFirstName());
        existingVendor.setLastName(vendor.getLastName());
        existingVendor.setEmailAddress(vendor.getEmailAddress());
        existingVendor.setAddressProof(vendor.getAddressProof());
        existingVendor.setAlternateNumber(vendor.getAlternateNumber());
        existingVendor.setIsApproved(vendor.getIsApproved());
        existingVendor.setBusinessRegistrationDate(vendor.getBusinessRegistrationDate());
        existingVendor.setBusinessStartDate(vendor.getBusinessStartDate());
        existingVendor.setContactNumber(vendor.getContactNumber());
        existingVendor.setBusinessYear(vendor.getBusinessYear());
        existingVendor.setContactPerson(vendor.getContactPerson());
        existingVendor.setVendorCategory(vendor.getVendorCategory());
        existingVendor.setGender(vendor.getGender());
        existingVendor.setEmailAddress(vendor.getEmailAddress());
        existingVendor.setContactPersonNumber(vendor.getContactPersonNumber());
        existingVendor.setPassword(vendor.getPassword());
        return vendorRepository.save(existingVendor);
    }

    public Vendor updatePassword(int vendorId, String newPassword) throws VendorNotFoundException {

        Vendor byId = vendorRepository.findById(vendorId).orElseThrow(() -> new VendorNotFoundException("Invalid vendorId"));

        byId.setPassword(newPassword);
        Vendor save = vendorRepository.save(byId);
        return save;


    }

    public Vendor updateVendorByFields(int vendorId, Map<String, Object> fields) throws InvalidVendorIdException {
        Vendor existingVendor = vendorRepository.findById(vendorId).orElseThrow(() -> new InvalidVendorIdException("please enter a valid vendorId"));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Vendor.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, existingVendor, value);
        });
        return vendorRepository.save(existingVendor);
    }

    @Override
    public List<Vendor> getAllVendors() {

        List<Vendor> vendors = vendorRepository.findAll();
       /* if (vendor.getIsApproved() == true) {
            return vendors;
        } else
            throw new RuntimeException("There are No Approved Vendors ");*/
        List<Vendor> collection = vendors.stream().filter(n -> n.getIsApproved() == true).collect(Collectors.toList());

        return collection;

        // return vendors;
    }

    @Override
    public Vendor getVendorById(int vendorId) throws VendorNotFoundException {
        return vendorRepository.findById(vendorId).orElseThrow(() -> new VendorNotFoundException("invalid vendorId"));
    }

    @Override
    public String deleteVendor(int vendorId) throws VendorNotFoundException {
        try {
            vendorRepository.deleteById(vendorId);

        } catch (Exception ex) {
            throw new VendorNotFoundException("invalid vendorId passed");
        }
        return "Vendor Successfully deleted" + vendorId;

    }

    @Override
    public Vendor authenticate(String emailAddress, String password) throws AuthenticationException {
        return vendorRepository.findByEmailAddressAndPassword(emailAddress, password);
    }

    @Override
    public List<Vendor> getVendorsByCategoryType(VendorCategory vendorCategory) throws Exception {

        List<Vendor> byVendorCategory = vendorRepository.findByVendorCategory(vendorCategory);

        return byVendorCategory;
    }

    @Override
    public Vendor getVendorByEmailAddress(String emailAddress) {
        return vendorRepository.findByEmailAddress(emailAddress);
    }

    public Vendor updateVendorStatus(int vendorId) throws VendorNotFoundException {
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new VendorNotFoundException("Vendor not found"));

        if (vendor.getVendorStatus() == VendorStatus.HOLD) {
            vendor.setVendorStatus(VendorStatus.ACTIVE);
            vendorRepository.save(vendor);
        }
        return vendor;
    }

    public Vendor updateIsApproved(int vendorId) throws VendorNotFoundException {
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new VendorNotFoundException("Vendor not found"));
        vendor.setIsApproved(true);
        Vendor vendorX = vendorRepository.save(vendor);
        return vendorX;
    }

    public List<Vendor> getAllUnApprovedVendors() {
        List<Vendor> vendors = vendorRepository.findAll();

        List<Vendor> collection = vendors.stream().filter(n -> n.getIsApproved() == false).collect(Collectors.toList());

        return collection;
    }


    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        Vendor vendor = vendorRepository.findByEmailAddress(emailAddress);
        if (vendor == null) {
            throw new UsernameNotFoundException("Vendor not found with email address: " + emailAddress);
        }
        return new org.springframework.security.core.userdetails.User(vendor.getEmailAddress(), vendor.getPassword(), vendor.getAuthorities());
    }

    public Object findUserOrVendorOrAdminByEmailAddress(String emailAddress) {
        User user = userRepository.findByEmailAddress(emailAddress);
        if (user != null) {
            return user;
        }
        Vendor vendor = vendorRepository.findByEmailAddress(emailAddress);
        if (vendor != null) {
            return vendor;
        }
        return adminRepository.findByEmailAddress(emailAddress);
    }

}

