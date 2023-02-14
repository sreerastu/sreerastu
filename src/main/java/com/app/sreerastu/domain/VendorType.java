/*
package com.app.sreerastu.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VENDOR_TYPE_TBL")
public class VendorType {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int vendorTypeId;
    //Platinum,Gold,Silver
    protected String vendorType;

    @OneToOne(mappedBy = "vendorType")
    protected Vendor vendor;
}
*/
