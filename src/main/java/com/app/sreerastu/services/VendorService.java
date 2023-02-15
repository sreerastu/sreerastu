package com.app.sreerastu.services;

import com.app.sreerastu.Enum.VendorCategory;
import com.app.sreerastu.domain.Vendor;
import com.app.sreerastu.dto.LoginApiDto;
import com.app.sreerastu.exception.AuthenticationException;
import com.app.sreerastu.exception.DuplicateVendorException;
import com.app.sreerastu.exception.InvalidVendorIdException;
import com.app.sreerastu.exception.VendorNotFoundException;

import java.util.List;

public interface VendorService {

    Vendor createVendor(Vendor vendor) throws DuplicateVendorException;
    Vendor updateVendor(int vendorId,Vendor vendor) throws InvalidVendorIdException;
    List<Vendor> getAllVendors();
    Vendor getVendorById(int vendorId) throws VendorNotFoundException;
    String deleteVendor(int vendorId) throws VendorNotFoundException;

    String login(LoginApiDto loginApiResponse) throws AuthenticationException;

    List<Vendor> getVendorsByCategoryType(VendorCategory vendorCategory);


}
