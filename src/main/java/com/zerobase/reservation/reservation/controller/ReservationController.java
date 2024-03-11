package com.zerobase.reservation.reservation.controller;

import com.zerobase.reservation.reservation.request.ReservationConfirmation;
import com.zerobase.reservation.reservation.request.ReservationRequest;
import com.zerobase.reservation.reservation.request.Review;
import com.zerobase.reservation.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 예약 서비스 요청을 처리하는 컨트롤러
 * 인증된 유저만 요청가능(role 무관)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    /**
     * 가게 예약 요청
     */
    @PostMapping
    public ResponseEntity<?> makeReservation(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.makeReservation(request));
    }

    /**
     * 내가 예약한 가게 목록 불러오기
     */
    @GetMapping
    public ResponseEntity<?> getReservationList(@RequestBody long requesterId) {
        return ResponseEntity.ok(reservationService.getReservationList(requesterId));
    }

    /**
     * 가게 도착 확인
     * 예약의 상태를 도착으로 변경한다.
     */
    @PutMapping("/arrived/{reservationId}")
    public ResponseEntity<?> confirmArrive(@PathVariable long reservationId, @RequestBody long requesterId) {
        return ResponseEntity.ok(reservationService.confirmArrive(reservationId, requesterId));
    }

    /**
     * 리뷰쓰기
     */
    @PostMapping("/review/{reservationId}")
    public ResponseEntity<?> writeReview(@PathVariable long reservationId, @RequestBody Review request) {
        return ResponseEntity.ok(reservationService.writeReview(reservationId, request));
    }

    /**
     * 리뷰수정
     */
    @PutMapping("/review/{reservationId}")
    public ResponseEntity<?> editReview(@PathVariable long reservationId, @RequestBody Review request) {
        return ResponseEntity.ok(reservationService.writeReview(reservationId, request));
    }

    /**
     * 리뷰삭제(점장도 가능)
     */
    @DeleteMapping("/review/{reservationId}")
    public ResponseEntity<?> editReview(@PathVariable long reservationId, @RequestBody long requesterId) {
        return ResponseEntity.ok(reservationService.deleteReview(reservationId, requesterId));
    }

}
