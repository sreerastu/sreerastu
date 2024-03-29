package com.app.sreerastu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOKING_TBL")
public class Booking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendorId", nullable = false)
    private Vendor vendor;

    @Column(nullable = false)
    private LocalDateTime bookingTime = LocalDateTime.now();

    @Column(nullable = false)
    private int amount;

    @Column(name = "receipt_id")
    private String receiptId;
}

