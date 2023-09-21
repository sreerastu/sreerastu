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
@Table(name = "SUBSCRIPTION_TBL")
public class SubscriptionBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subscriptionId;

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
