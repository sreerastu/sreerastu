package com.app.sreerastu.repositories;

import com.app.sreerastu.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

  //   Optional<Vendor> findByName(String fileName);

    String findOneByEmailAddressAndPassword(String emailAddress , String password);

}
