package com.zerobase.reservation.reservation.request;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 예약 요청 폼
 */
@Getter
public class ReservationRequest {
    private String phoneNumber;
    private LocalDateTime reservatedAt;
    private long requesterId;
    private long storeId;
}
