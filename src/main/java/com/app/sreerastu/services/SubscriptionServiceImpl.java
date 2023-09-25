package com.app.sreerastu.services;

import com.app.sreerastu.domain.SubscriptionBooking;
import com.app.sreerastu.domain.Vendor;
import com.app.sreerastu.exception.BookingNotFoundException;
import com.app.sreerastu.exception.VendorNotFoundException;
import com.app.sreerastu.repositories.SubscriptionBookingRepository;
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
public class SubscriptionServiceImpl {

    @Autowired
    private SubscriptionBookingRepository subscriptionBooking;

    @Autowired
    private SubscriptionBookingRepository subscriptionBookingRepository;

    @Autowired
    private VendorRepository vendorRepository;


    public SubscriptionBooking vendorSubscription(int amount, int vendorId) throws RazorpayException, VendorNotFoundException {

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

    public List<SubscriptionBooking> getAllSubscriptionBookings() {
        return subscriptionBookingRepository.findAll();
    }

    public SubscriptionBooking getSubscriptionBookingById(int subscriptionId) throws BookingNotFoundException {
        return subscriptionBookingRepository.findById(subscriptionId)
                .orElseThrow(() -> new BookingNotFoundException("SubscriptionBooking not found with id :" + subscriptionId));
    }


    public List<SubscriptionBooking> getSubscriptionBookingsByVendorId(int vendorId) throws VendorNotFoundException {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found with id: " + vendorId));
        List<SubscriptionBooking> bookings = subscriptionBookingRepository.findByVendor(vendor);
        return bookings;
    }

}
