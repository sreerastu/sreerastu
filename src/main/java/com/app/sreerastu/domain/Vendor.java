package com.app.sreerastu.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

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
    protected String gender;
    protected String emailAddress;
    protected String contactNumber;
    protected String alternateNumber;
    protected String contactPerson;
    protected String contactPersonNumber;
    protected String businessName;
    protected String businessStartDate;
    protected String businessYear;
    protected String businessRegistrationDate;
   /* @Lob
    @Column(name = "businessLogo", length = 1000)
    protected byte[] businessLogo;*/
    protected String idProof;
  /*  @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_subscription_Id")
    private SubscriptionType subscriptionType;*/
   // protected String subscriptionTypeId;
/*    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_vendorCategory_Id")
    private VendorCategory vendorCategory;*/
   // protected String vendorCategoryId;
    protected Timestamp registeredDate;
    protected String addressProof;
    protected String subscriptionExpiryDate;
    protected String password;
   /* @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_vendorType_Id")
    private VendorType vendorType;*/
   // protected String vendorTypeId;
    protected Boolean isApproved;
  /*  @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_status_Id")
    private Status status;*/
   // protected String statusId;
   /* @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_login_Id")
    protected String loginId;
*/
}
