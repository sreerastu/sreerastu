package com.app.sreerastu.services;

import com.app.sreerastu.Enum.VendorCategory;
import com.app.sreerastu.Enum.VendorStatus;
import com.app.sreerastu.domain.Booking;
import com.app.sreerastu.domain.User;
import com.app.sreerastu.domain.Vendor;
import com.app.sreerastu.exception.*;
import com.app.sreerastu.repositories.UserRepository;
import com.app.sreerastu.repositories.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class VendorServiceImpl implements VendorService {

    final static Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

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





        /* Vendor vendor = new Vendor();
        String emailAddress = vendor.getEmailAddress();

        mailService.sendMailBySearch(emailAddress);*/

       /* Admin admin = new Admin();
        String emailAddress = admin.getEmailAddress();
        mailService.sendMailBySearch(emailAddress);*/

        return byVendorCategory;
    }

    @Override
    public Vendor getVendorByEmailAddress(String emailAddress) {
        return vendorRepository.findByEmailAddress(emailAddress);
    }


    public Booking bookVendor(int userId, int vendorId) throws VendorNotFoundException, UserNotFoundException, VendorNotAvailableException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new VendorNotFoundException("Vendor not found"));

        if (vendor.getVendorStatus() == VendorStatus.ACTIVE) {
            Booking booking = new Booking();
            booking.setUser(user);
            booking.setVendor(vendor);
          //  bookingService.createBooking(booking);

            vendor.setVendorStatus(VendorStatus.HOLD);
            vendorRepository.save(vendor);
            return booking;
        } else {
            throw new VendorNotAvailableException("Vendor not available for booking");
        }
    }

    public Vendor updateVendorStatus(int vendorId) throws VendorNotFoundException {
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new VendorNotFoundException("Vendor not found"));

        if (vendor.getVendorStatus() == VendorStatus.HOLD) {
            vendor.setVendorStatus(VendorStatus.ACTIVE);
            vendorRepository.save(vendor);
        }
        return vendor;
    }


    public List<Booking> getBookingsByVendorId(int vendorId) throws VendorNotFoundException {
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new VendorNotFoundException("Vendor not found"));
        return vendor.getBookings();
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
}

