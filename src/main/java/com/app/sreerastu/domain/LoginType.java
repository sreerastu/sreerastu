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
@Table(name = "LOGIN_TBL")
public class LoginType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int loginTypeId;
    protected String loginType;

    @OneToOne(mappedBy = "LOGIN_TBL")
    private Vendor vendor;
}
*/
