package com.app.sreerastu.domain;

import com.app.sreerastu.Enum.Gender;
import com.app.sreerastu.Enum.VendorCategory;
import com.app.sreerastu.Enum.VendorStatus;
import com.app.sreerastu.Enum.VendorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "VENDOR_TBL")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int vendorId;
    protected String firstName;
    protected String lastName;
    @Enumerated(EnumType.STRING)
    protected Gender gender;
    protected String emailAddress;
    protected String contactNumber;
    protected String alternateNumber;
    protected String contactPerson;
    protected String contactPersonNumber;
    protected String businessStartDate;
    protected String businessYear;
    protected String businessRegistrationDate;
   /* @Lob
    @Column(name = "businessLogo", length = 1000)
    protected byte[] businessLogo;*/
    protected String aadharNumber;
    protected String panNumber;

    @Enumerated(EnumType.STRING)
    private  VendorCategory vendorCategory;

/*    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_vendorCategory_Id")
    private VendorCategory vendorCategory;*/
   // protected String vendorCategoryId;
    @Enumerated(EnumType.STRING)
    protected VendorStatus vendorStatus;
    protected Date registeredDate = new Date(System.currentTimeMillis());
    protected String addressProof;
    protected String subscriptionExpiryDate;
    protected String password;
   /* @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vendorTypeId" , referencedColumnName = "vendorTypeId")
    protected VendorType vendorType;*/
    protected Boolean isApproved = false;
  /*  @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_statusTypeId")
    protected Status status;*/
   /* @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "loginTypeId")
    protected LoginType loginType;
*/
    @Enumerated(EnumType.STRING)
    protected VendorType vendorType;
}


