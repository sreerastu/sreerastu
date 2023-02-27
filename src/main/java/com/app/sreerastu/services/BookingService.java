package com.app.sreerastu.services;

import com.app.sreerastu.domain.Booking;
import com.app.sreerastu.domain.User;
import com.app.sreerastu.exception.*;

import java.util.List;

public interface BookingService {


    List<Booking> getAllBookings();

    Booking getBookingById(int bookingId) throws BookingNotFoundException;

    Booking createBooking(int userId, int vendorId) throws UserNotFoundException, VendorNotFoundException, VendorNotAvailableException;

    String cancelBooking(int bookingId) throws BookingNotFoundException;

    List<Booking> getBookingsByUserId(int userId) throws UserNotFoundException;

    List<Booking> getBookingsByVendorId(int vendorId) throws VendorNotFoundException;

}