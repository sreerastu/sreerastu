package com.app.sreerastu.services;

import com.app.sreerastu.domain.Booking;
import com.app.sreerastu.exception.BookingNotFoundException;
import com.app.sreerastu.exception.UserNotFoundException;
import com.app.sreerastu.exception.VendorNotAvailableException;
import com.app.sreerastu.exception.VendorNotFoundException;
import com.razorpay.RazorpayException;

import java.util.List;

public interface BookingService {


    List<Booking> getAllBookings();

    Booking getBookingById(int bookingId) throws BookingNotFoundException;

    Booking createBooking(int userId, int vendorId, int amount) throws UserNotFoundException, VendorNotFoundException, VendorNotAvailableException, RazorpayException;

    String cancelBooking(int bookingId) throws BookingNotFoundException;

    List<Booking> getBookingsByUserId(int userId) throws UserNotFoundException;

    List<Booking> getBookingsByVendorId(int vendorId) throws VendorNotFoundException;

}