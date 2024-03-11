package com.zerobase.reservation.reservation.entity;

/**
 * 예약 상태
 */
public enum ReservationStatus {
    WAITING_CONFIRM, // 승인 대기
    DENIED, // 거절
    CONFIRMED, // 승인
    ARRIVED, // 도착
    CANCELED // 취소
}
