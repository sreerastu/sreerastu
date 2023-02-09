package com.app.sreerastu.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "ADMIN_TBL")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int adminId;
    protected String firstName;
    protected String lastName;
    protected String contactNumber;
    protected String password;
   // protected Date dob;
    protected String emailAddress;
  /*  @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_login_Id")
    protected String loginId;*/
}
