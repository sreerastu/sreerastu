package com.app.sreerastu.controllers;

import com.app.sreerastu.domain.SubscriptionBooking;
import com.app.sreerastu.exception.BookingNotFoundException;
import com.app.sreerastu.exception.VendorNotFoundException;
import com.app.sreerastu.services.SubscriptionServiceImpl;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionServiceImpl subscriptionService;

    @PostMapping("/create_order/{amount}/{vendorId}")
    public ResponseEntity<?> vendorSubscription(@PathVariable int amount, @PathVariable int vendorId) throws RazorpayException, VendorNotFoundException {
        SubscriptionBooking booking = subscriptionService.vendorSubscription(amount, vendorId);

        if (booking != null) {
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Subscription failed");
        }
    }

    @GetMapping("/subscriptionBookings")
    public List<SubscriptionBooking> getAllBookings() {
        return subscriptionService.getAllSubscriptionBookings();
    }

    @GetMapping("/subscriptionBooking/{subscriptionBookingId}")
    public SubscriptionBooking getBookingById(@PathVariable int subscriptionBookingId) throws BookingNotFoundException {
        return subscriptionService.getSubscriptionBookingById(subscriptionBookingId);
    }

    @GetMapping("/vendor/{vendorId}/subscriptionBookings")
    public List<SubscriptionBooking> getSubscriptionBookingsForVendor(@PathVariable int vendorId) throws VendorNotFoundException {
        return subscriptionService.getSubscriptionBookingsByVendorId(vendorId);
    }
}
