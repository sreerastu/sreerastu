package com.app.sreerastu.repositories;

import com.app.sreerastu.domain.Booking;
import com.app.sreerastu.domain.User;
import com.app.sreerastu.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUser(User user);
    List<Booking> findByVendor(Vendor vendor);
}