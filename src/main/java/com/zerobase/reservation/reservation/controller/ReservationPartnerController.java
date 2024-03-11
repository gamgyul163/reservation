package com.zerobase.reservation.reservation.controller;

import com.zerobase.reservation.reservation.request.ReservationConfirmation;
import com.zerobase.reservation.reservation.request.ReservationRequest;
import com.zerobase.reservation.reservation.request.Review;
import com.zerobase.reservation.reservation.service.ReservationPartnerService;
import com.zerobase.reservation.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 예약 서비스중 파트너 권한이 필요한 요청이 분리되어있는 컨트롤러
 * 회원 타입이 파트너여야만 요청이 가능하다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner/reservation")
public class ReservationPartnerController {
    private final ReservationPartnerService reservationPartnerService;

    /**
     * 예약 승인
     * 예약의 상태를 컨펌으로 변경한다.
     */
    @PutMapping("/confirm/{reservationId}")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> confirmReservation(@PathVariable long reservationId, @RequestBody ReservationConfirmation request) {
        return ResponseEntity.ok(reservationPartnerService.confirmReservation(reservationId, request));
    }

    /**
     * 예약 승인 대기 목록 조회
     * 승인 대기중인 예약 리스트를 가져온다.
     */
    @GetMapping("/confirm")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getWaitingConfirmList(@RequestBody long requesterId) {
        return ResponseEntity.ok(reservationPartnerService.getWaitingConfirmList(requesterId));
    }
}
