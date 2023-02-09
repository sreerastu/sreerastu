package com.app.sreerastu.repositories;

import com.app.sreerastu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

   // User findByEmailIdAndPassword( LoginApiResponse loginApiResponse);
}

