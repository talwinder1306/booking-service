package com.upgrad.booking.services;

import com.upgrad.booking.dto.PaymentDTO;
import com.upgrad.booking.entities.BookingInfoEntity;
import com.upgrad.booking.exception.InvalidInputException;

public interface BookingInfoService {

    BookingInfoEntity acceptBookingDetails(BookingInfoEntity bookingInfoEntity);

    BookingInfoEntity updateBookingDetailsWithPayment(int bookingId, PaymentDTO paymentDTO) throws InvalidInputException;
}
