package com.upgrad.booking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * This class corresponds to the request received for each booking
 */
@Getter
@Setter
@NoArgsConstructor
public class BookingInfoDTO {

    private int id;

    @NotNull(message = "fromDate should be present")
    private Date fromDate;

    @NotNull(message = "toDate should be present")
    private Date toDate;

    @NotBlank(message = "aadharNumber cannot be blank")
    private String aadharNumber;

    @NotNull(message = "numOfRooms cannot be blank")
    private int numOfRooms;

    private String roomNumbers;

    private int roomPrice;

    private int transactionId;

    private Date bookedOn;
}
