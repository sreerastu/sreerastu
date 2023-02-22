package com.app.sreerastu.services;

import com.app.sreerastu.domain.Booking;
import com.app.sreerastu.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookingServiceImpl implements BookingService {


    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    private BookingRepository bookingRepository;

/*
    @Override
    public List<Booking> getBookingsByUser(int userId) {
        return null;//bookingRepository.findAllById(userId);
    }

    @Override
    public List<Booking> getBookingsByVendor(int vendorId) {
        return null;//bookingRepository.findAllById(vendorId);
    }
*/

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking) ;
    }
}
