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
@Table(name = "STATUS_TBL")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int statusTypeId;
    // Active,InActive,Hold
    protected String statusType;
    @OneToOne(mappedBy = "status", cascade = CascadeType.ALL)
    protected Vendor vendor;

}
*/
