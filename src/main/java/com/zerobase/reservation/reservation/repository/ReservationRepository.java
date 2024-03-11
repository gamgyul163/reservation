package com.zerobase.reservation.reservation.repository;

import com.zerobase.reservation.reservation.entity.Reservation;
import com.zerobase.reservation.reservation.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByStoreIdAndReservationStatus(long storeId, ReservationStatus reservationStatus);

    List<Reservation> findByMemberId(long requesterId);

    List<Reservation> findByStoreId(long id);
}
