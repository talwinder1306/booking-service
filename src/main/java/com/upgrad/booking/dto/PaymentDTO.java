package com.upgrad.booking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * This class corresponds to the request received for each payment transaction
 */

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO {

    private int transactionId;

    @NotBlank(message = "paymentMode cannot be blank")
    private String paymentMode;

    @NotNull(message = "bookingId cannot be blank")
    private int bookingId;

    private String upiId;

    private String cardNumber;

}
