package com.upgrad.booking.services;


import com.upgrad.booking.dao.BookingInfoDao;
import com.upgrad.booking.dto.PaymentDTO;
import com.upgrad.booking.entities.BookingInfoEntity;
import com.upgrad.booking.exception.InvalidInputException;
import com.upgrad.booking.model.PaymentMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookingInfoServiceImpl implements BookingInfoService {

    @Autowired
    private BookingInfoDao bookingInfoDao;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${paymentApp.url}")
    private String paymentAppUrl;

    private static final int BASE_PRICE = 1000;
    private static final int TOTAL_ROOMS = 100;

    /**
     * This method calculates the number of days by using the difference in number of days between fromDate and toDate
     * If fromDate is after toDate then the numOfDays is 0
     *
     * @param bookingInfoEntity
     * @return numOfDays
     */
    private static int calculateNumOfDays(BookingInfoEntity bookingInfoEntity) {
        long numOfDays;
        LocalDateTime fromDate = bookingInfoEntity.getFromDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime toDate = bookingInfoEntity.getToDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        numOfDays = Duration.between(fromDate, toDate).toDays();
        System.out.println ("Days: " + numOfDays);
        if(numOfDays < 0) {
            return 0;
        }

        return (int) numOfDays;
    }

    /**
     * This method assigns random room numbers based on the numOfRooms passed as argument
     *
     * @param count
     * @return List of roomNumbers
     */
    private static List<String> getRandomRoomNumbers(int count){
        Random rand = new Random();
        List<String>numberList = new ArrayList<>();

        for (int i=0; i<count; i++){
            numberList.add(String.valueOf(rand.nextInt(TOTAL_ROOMS)));
        }

        return numberList;
    }


    /**
     * This method calculates the room price using the below formula:
     * roomPrice = 1000* numOfRooms*(number of days)
     * Here, 1000 INR is the base price/day/room.
     *
     * number of days - obtained from calculateNumOfDays
     *
     * If roomPrice is calculated to 0, no room is assigned and no booking is done
     *
     * @param bookingInfoEntity
     * @return bookingInfoEntity containing roomPrice, roomNumbers and bookedOn date
     */
    @Override
    public BookingInfoEntity acceptBookingDetails(BookingInfoEntity bookingInfoEntity) {
        int numOfDays = calculateNumOfDays(bookingInfoEntity);

        int roomPrice = BASE_PRICE * bookingInfoEntity.getNumOfRooms() *(numOfDays);
        bookingInfoEntity.setRoomPrice(roomPrice);

        if(numOfDays > 0) {
            List<String> roomList = getRandomRoomNumbers(bookingInfoEntity.getNumOfRooms());
            bookingInfoEntity.setRoomNumbers(String.join(",", roomList));

            bookingInfoEntity.setBookedOn(new Date());
        }

        return bookingInfoDao.save(bookingInfoEntity);
    }

    /**
     * This method uses Rest Template to call Payment Service to get the transaction id for booking
     * This transactionId is updated in the booking info and saved to the database
     * @param bookingId
     * @param paymentDTO
     * @return bookingInfoEntity updated with transaction id
     */
    @Override
    public BookingInfoEntity updateBookingDetailsWithPayment(int bookingId, PaymentDTO paymentDTO) throws InvalidInputException {
        Set<String> paymentModeSet = getPaymentModeSet();

        if (!(paymentModeSet.contains(paymentDTO.getPaymentMode().toUpperCase()))) {
            throw new InvalidInputException("Invalid mode of payment");
        }

        Optional<BookingInfoEntity> bookingInfoEntityOptional = bookingInfoDao.findById(bookingId);
        if(!bookingInfoEntityOptional.isPresent()) {
            throw new InvalidInputException("Invalid Booking Id");
        }

        BookingInfoEntity bookingInfoEntity = bookingInfoEntityOptional.get();
        Integer transactionId = restTemplate.postForObject(paymentAppUrl, paymentDTO, Integer.class);
        if(transactionId == null) {
            throw new InvalidInputException("Invalid Transaction Id");
        }

        bookingInfoEntity.setTransactionId(transactionId);
        return bookingInfoDao.save(bookingInfoEntity);
    }

    /**
     * This method returns a Set containing all the available mode of payment for the payment service
     * @return Set paymentModes
     */
    private Set<String> getPaymentModeSet() {
        Set<String> paymentModeSet = Stream.of(PaymentMode.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
        paymentModeSet = new HashSet<>(paymentModeSet);
        return paymentModeSet;
    }

}
