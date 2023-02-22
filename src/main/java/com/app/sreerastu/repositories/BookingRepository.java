package com.app.sreerastu.repositories;

import com.app.sreerastu.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
  /*  List<Booking> findByUserId(int userId);
    List<Booking> findByVendorId(int vendorId);*/
}