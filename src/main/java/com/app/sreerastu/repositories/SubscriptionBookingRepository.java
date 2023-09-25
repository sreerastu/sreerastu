package com.app.sreerastu.repositories;

import com.app.sreerastu.domain.SubscriptionBooking;
import com.app.sreerastu.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionBookingRepository extends JpaRepository<SubscriptionBooking, Integer> {

    List<SubscriptionBooking> findByVendor(Vendor vendor);

}
