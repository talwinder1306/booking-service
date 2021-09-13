package com.upgrad.booking.controller;

import com.upgrad.booking.dto.BookingInfoDTO;
import com.upgrad.booking.dto.PaymentDTO;
import com.upgrad.booking.entities.BookingInfoEntity;
import com.upgrad.booking.services.BookingInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/hotel")
public class BookingInfoController {

    @Autowired
    BookingInfoService bookingInfoService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping(value="/booking", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createBooking(@Valid @RequestBody BookingInfoDTO bookingInfoDTO) {
        BookingInfoEntity newBookingInfoEntity = modelMapper.map(bookingInfoDTO, BookingInfoEntity.class);
        BookingInfoEntity savedBookingInfoEntity = bookingInfoService.acceptBookingDetails(newBookingInfoEntity);

        return new ResponseEntity(savedBookingInfoEntity, HttpStatus.CREATED);
    }

    @PostMapping(value="/booking/{bookingId}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateBookingWithPayment(@PathVariable(name = "bookingId") int bookingId, @Valid @RequestBody PaymentDTO paymentDTO) {
       BookingInfoEntity updatedBookingInfoEntity = bookingInfoService.updateBookingDetailsWithPayment(bookingId, paymentDTO);
       String message = "Booking confirmed for user with aadhaar number: "
                    + updatedBookingInfoEntity.getAadharNumber()
                    +    "    |    "
                    + "Here are the booking details:    " + updatedBookingInfoEntity.toString();
       System.out.println(message);

       return new ResponseEntity(updatedBookingInfoEntity, HttpStatus.CREATED);
    }

}
