package com.upgrad.booking.dao;

import com.upgrad.booking.entities.BookingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingInfoDao extends JpaRepository<BookingInfoEntity, Integer> {
}
