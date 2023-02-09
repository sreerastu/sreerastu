package com.app.sreerastu.repositories;

import com.app.sreerastu.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

 //   Admin findByEmailIdAndPassword(String emailAddress ,String password);
}
