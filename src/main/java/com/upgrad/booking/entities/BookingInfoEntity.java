package com.upgrad.booking.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * This class corresponds to booking table in the database having booking info
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity(name = "booking")
public class BookingInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="bookingId")
    private int id;

    @Column(nullable = true)
    private Date fromDate;

    @Column(nullable = true)
    private Date toDate;

    @Column(nullable = true)
    private String aadharNumber;

    private int numOfRooms;

    private String roomNumbers;

    @Column(nullable = false)
    private int roomPrice;

    @Column(columnDefinition = "integer default 0")
    private int transactionId;

    @Column(nullable = true)
    private Date bookedOn;

}
