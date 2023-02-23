package com.app.sreerastu.services;

import com.app.sreerastu.domain.Admin;
import com.app.sreerastu.dto.LoginApiDto;
import com.app.sreerastu.exception.AdminNotFoundException;
import com.app.sreerastu.exception.AuthenticationException;

import java.util.List;

public interface AdminService {

    Admin createAdmin(Admin admin);

    Admin updateAdmin(int adminId, Admin admin) throws AdminNotFoundException;

    List<Admin> getAllAdmins();

    Admin getAdminById(int adminId) throws AdminNotFoundException;

    String deleteAdminById(int adminId) throws AdminNotFoundException;

    Admin authenticate(String emailAddress, String password) throws AuthenticationException;

}
