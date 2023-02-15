package com.app.sreerastu.repositories;

import com.app.sreerastu.Enum.VendorCategory;
import com.app.sreerastu.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

  //   Optional<Vendor> findByName(String fileName);

    Vendor findByEmailAddressAndPassword(String emailAddress , String password);
    List<Vendor> findByVendorCategory(VendorCategory vendorCategory);

    Vendor findByEmailAddress(String emailAddress);


}
