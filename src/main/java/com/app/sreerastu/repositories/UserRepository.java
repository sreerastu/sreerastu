package com.app.sreerastu.repositories;

import com.app.sreerastu.domain.User;
import com.app.sreerastu.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

   User findByEmailAddressAndPassword(String emailAddress , String password);

   User findByEmailAddress(String emailAddress);

}

