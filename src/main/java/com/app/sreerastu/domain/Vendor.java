package com.app.sreerastu.domain;

import com.app.sreerastu.Enum.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "VENDOR_TBL")
public class Vendor implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int vendorId;
    protected String firstName;
    protected String lastName;
    @Enumerated(EnumType.STRING)
    protected Gender gender;
    @Column(nullable = false,unique = true)
    protected String emailAddress;
    protected String contactNumber;
    protected String alternateNumber;
    protected String contactPerson;
    protected String contactPersonNumber;
    protected String businessStartDate;
    protected String businessYear;
    protected String businessRegistrationDate;
    protected String aadharNumber;
    protected String panNumber;

    @Enumerated(EnumType.STRING)
    private  VendorCategory vendorCategory;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Enumerated(EnumType.STRING)
    protected VendorStatus vendorStatus;
    protected Date registeredDate = new Date(System.currentTimeMillis());
    protected String addressProof;
    protected String subscriptionExpiryDate;
    protected String password;

    protected Boolean isApproved = false;

    @Enumerated(EnumType.STRING)
    protected VendorType vendorType;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority(role.name()));

        return authorities;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.getEmailAddress();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}


