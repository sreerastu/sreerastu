package com.app.sreerastu.services;

import com.app.sreerastu.domain.Booking;

import java.util.List;

public interface BookingService {
   /* List<Booking> getBookingsByUser(int userId);
    List<Booking> getBookingsByVendor(int vendorId);*/
    Booking createBooking(Booking booking);
}