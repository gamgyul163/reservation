package com.zerobase.reservation.reservation.request;

import lombok.Getter;

/**
 * 예약 승인 요청 폼
 */
@Getter
public class ReservationConfirmation {
    long requesterId;
    boolean confirm;
}
