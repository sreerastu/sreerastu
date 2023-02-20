package com.app.sreerastu.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "USER_TBL")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int userId;
    protected String firstName;
    protected String lastName;
    protected String emailAddress;
    protected String contactNumber;
    protected String password;
    /*  @OneToOne(cascade = CascadeType.ALL)
      @JoinColumn(name = "fk_login_Id")
      protected String loginId;*/
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();


}
