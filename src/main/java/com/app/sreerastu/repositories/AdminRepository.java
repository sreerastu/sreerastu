package com.app.sreerastu.repositories;

import com.app.sreerastu.domain.Admin;
import com.app.sreerastu.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

    Admin findByEmailAddressAndPassword(String emailAddress , String password);

    Admin findByEmailAddress(String emailAddress);

}
