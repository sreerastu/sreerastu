package com.app.sreerastu.services;

import com.app.sreerastu.Enum.VendorStatus;
import com.app.sreerastu.Enum.VendorType;
import com.app.sreerastu.domain.Booking;
import com.app.sreerastu.domain.SubscriptionBooking;
import com.app.sreerastu.domain.User;
import com.app.sreerastu.domain.Vendor;
import com.app.sreerastu.exception.BookingNotFoundException;
import com.app.sreerastu.exception.UserNotFoundException;
import com.app.sreerastu.exception.VendorNotAvailableException;
import com.app.sreerastu.exception.VendorNotFoundException;
import com.app.sreerastu.repositories.BookingRepository;
import com.app.sreerastu.repositories.SubscriptionBookingRepository;
import com.app.sreerastu.repositories.UserRepository;
import com.app.sreerastu.repositories.VendorRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.app.sreerastu.Enum.VendorType.*;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private SubscriptionBookingRepository subscriptionBooking;


    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    private BookingRepository bookingRepository;


    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();

    }

    @Override
    public Booking getBookingById(int bookingId) throws BookingNotFoundException {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id " + bookingId));
    }

    @Override
    public Booking createBooking(int userId, int vendorId, int amount) throws UserNotFoundException, VendorNotFoundException, VendorNotAvailableException, RazorpayException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found with id " + vendorId));

        if (vendor.getVendorStatus() != VendorStatus.ACTIVE) {
            throw new VendorNotAvailableException("Vendor is not available for booking!");
        }

        vendor.setVendorStatus(VendorStatus.HOLD);
        vendorRepository.save(vendor);

        String receiptId = "txn_" + UUID.randomUUID().toString();

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setVendor(vendor);
        booking.setAmount(amount);
        booking.setBookingTime(LocalDateTime.now());
        booking.setReceiptId(receiptId);

        var client = new RazorpayClient("rzp_test_mOsWfEZouKIqSx", "XgOFZ2sYI2nTODoh6Hwq1r05");
        JSONObject obj = new JSONObject();
        obj.put("amount", amount * 100);
        obj.put("currency", "INR");
        obj.put("receipt", receiptId);
        Order order = client.orders.create(obj);

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByUserId(int userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
        List<Booking> bookings = bookingRepository.findByUser(user);
        return bookings;
    }

    @Override
    public List<Booking> getBookingsByVendorId(int vendorId) throws VendorNotFoundException {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found with id " + vendorId));

        return bookingRepository.findByVendor(vendor);
    }


    @Override
    public String cancelBooking(int bookingId) throws BookingNotFoundException {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id " + bookingId));

        Vendor vendor = booking.getVendor();
        vendor.setVendorStatus(VendorStatus.ACTIVE);
        vendorRepository.save(vendor);
        bookingRepository.delete(booking);
        return "Booking Cancelled Successfully!";
    }


    public SubscriptionBooking vendorSubscription(int amount,int vendorId) throws RazorpayException, VendorNotFoundException {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found with id " + vendorId));

        if (amount == 2000) {
            vendor.setVendorType(SILVER);
        } else if (amount == 4000) {
            vendor.setVendorType(GOLD);
        } else if (amount == 5000) {
            vendor.setVendorType(PLATINUM);
        }

        String receiptId = "txn_" + UUID.randomUUID().toString();
        var client = new RazorpayClient("rzp_test_mOsWfEZouKIqSx", "XgOFZ2sYI2nTODoh6Hwq1r05");
        JSONObject obj = new JSONObject();
        obj.put("amount", amount * 100);
        obj.put("currency", "INR");
        obj.put("receipt", receiptId);

        SubscriptionBooking booking = new SubscriptionBooking();
        booking.setAmount(amount);
        booking.setVendor(vendor);
        booking.setBookingTime(LocalDateTime.now());
        booking.setReceiptId(receiptId);



        Order order = client.orders.create(obj);
        return subscriptionBooking.save(booking);
    }


}
