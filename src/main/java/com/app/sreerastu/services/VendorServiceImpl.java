package com.app.sreerastu.services;

import com.app.sreerastu.domain.Vendor;
import com.app.sreerastu.dto.LoginApiDto;
import com.app.sreerastu.exception.AuthenticationException;
import com.app.sreerastu.exception.DuplicateVendorException;
import com.app.sreerastu.exception.InvalidVendorIdException;
import com.app.sreerastu.exception.VendorNotFoundException;
import com.app.sreerastu.repositories.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class VendorServiceImpl implements VendorService {

    final static Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    //  @Autowired
    private VendorRepository vendorRepository;


    private static final String STATUS = "status";
    private static final String LOGIN_SUCCESS = "Login Successful";

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
    public Vendor updatePassword(String emailAddress) throws InvalidVendorIdException {
        Vendor vendor = new Vendor();
        Vendor existingVendor = vendorRepository.findByEmailAddress(emailAddress);
        existingVendor.setPassword(vendor.getPassword());
        return vendorRepository.save(existingVendor);
    }

    @Override
    public List<Vendor> getAllVendors() {
      /*  Vendor vendor = new Vendor();
        if(vendor.getIsApproved()== true) {*/

            List<Vendor> vendors = vendorRepository.findAll();
            return vendors;
      /*  }else
            throw new RuntimeException();*/
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
    public String login(LoginApiDto loginApiResponse) throws AuthenticationException {

        //Verifing vendor exists or not
        Vendor loginResult = vendorRepository.findByEmailAddressAndPassword(loginApiResponse.getEmailAddress(), loginApiResponse.getPassword());

        //Response
        if (Objects.isNull(loginResult)) {
            throw new AuthenticationException("Invalid credentials");
        }
        return "Login Successful";
    }

    @Override
    public List<Vendor> getVendorsByCategoryType(String vendorCategory) {

        List<Vendor> byVendorCategory = vendorRepository.findByVendorCategory(vendorCategory);

        List<Vendor> vendors = byVendorCategory.stream().filter(n -> n.getVendorCategory().equals("VIDEOGRAPHY")).collect(Collectors.toList());
        return vendors;
    }
}
