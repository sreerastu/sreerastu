package com.app.sreerastu.controllers;

import com.app.sreerastu.domain.Booking;
import com.app.sreerastu.exception.BookingNotFoundException;
import com.app.sreerastu.exception.UserNotFoundException;
import com.app.sreerastu.exception.VendorNotAvailableException;
import com.app.sreerastu.exception.VendorNotFoundException;
import com.app.sreerastu.services.BookingServiceImpl;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingController {



    private BookingServiceImpl bookingService;

    @Autowired
    public BookingController(BookingServiceImpl bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/booking/{bookingId}")
    public Booking getBookingById(@PathVariable int bookingId) throws BookingNotFoundException {
        return bookingService.getBookingById(bookingId);
    }

   /* @PostMapping("/user/booking/{userId}/{vendorId}")
    public Booking createBooking(@PathVariable int userId, @PathVariable int vendorId) throws UserNotFoundException, VendorNotFoundException, VendorNotAvailableException {
        return bookingService.createBooking(userId, vendorId);
    }*/

    @GetMapping("/user/{userId}/bookings")
    public List<Booking> getBookingsForUser(@PathVariable int userId) throws UserNotFoundException {
        return bookingService.getBookingsByUserId(userId);
    }

    @GetMapping("/vendor/{vendorId}/bookings")
    public List<Booking> getBookingsForVendor(@PathVariable int vendorId) throws VendorNotFoundException {
        return bookingService.getBookingsByVendorId(vendorId);
    }

    @DeleteMapping("/booking/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable int bookingId) throws BookingNotFoundException {
        String cancel = bookingService.cancelBooking(bookingId);
        return ResponseEntity.status(HttpStatus.OK).body(cancel);

    }

    @PostMapping("/user/booking-and-order/{userId}/{vendorId}/{amount}")
    public ResponseEntity<?> createBookingAndOrder(@PathVariable int userId, @PathVariable int vendorId, @PathVariable int amount) throws UserNotFoundException, VendorNotFoundException, VendorNotAvailableException, RazorpayException {
        Booking booking = bookingService.createBooking(userId, vendorId, amount);

        if (booking != null) {
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Booking  failed");
        }
    }

    @PostMapping("user/create_order/{amount}")
    public String createOrder(@PathVariable int amount) throws RazorpayException {
        var client = new RazorpayClient("rzp_test_mOsWfEZouKIqSx", "XgOFZ2sYI2nTODoh6Hwq1r05");

        JSONObject obj = new JSONObject();
        obj.put("amount", amount * 100);
        obj.put("currency", "INR");
        obj.put("receipt", "txn_12746467");

        Order order = client.orders.create(obj);
        return order.toString();
    }



}
