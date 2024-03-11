package com.zerobase.reservation.reservation.service;

import com.zerobase.reservation.reservation.entity.Reservation;
import com.zerobase.reservation.reservation.entity.ReservationStatus;
import com.zerobase.reservation.reservation.entity.Store;
import com.zerobase.reservation.reservation.repository.ReservationRepository;
import com.zerobase.reservation.reservation.repository.StoreRepository;
import com.zerobase.reservation.reservation.request.ReservationRequest;
import com.zerobase.reservation.reservation.request.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 예약서비스 일반 로직들
 * 미구현 사항:  리뷰 작성후 상점 별정 갱신, 시간 초과한 예약에 대한 상태 변경(커스텀 익셉션)
 * 리팩토링 필요 사항: 커스텀 익셉션, 파트너서비스와 공통되는 상위 클래스 만들어서 유효성 검증, 조회등 일부 공통 매서드 처리
 */
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    /**
     * 예약하기 요청 처리
     */
    @Transactional
    public Reservation makeReservation(ReservationRequest request) {
        if (!storeRepository.existsById(request.getStoreId())) {
            throw new RuntimeException("상점 없음");
        }

        timeValidationCheck(request.getReservatedAt());

        return reservationRepository.save(Reservation.from(request));

    }

    /**
     * 가게 정보 조회(ID)
     */
    private Store getStore(long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("가게 없음"));
        return store;
    }

    /**
     * 예약 정보 조회
     */
    private Reservation getReservation(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("예약 없음"));
        return reservation;
    }

    /**
     * 특정 유저 예약 목록 조회
     */
    public List<Reservation> getReservationList(long requesterId) {
        return reservationRepository.findByMemberId(requesterId);
    }

    /**
     * 예약 상태 도착으로 변경
     */
    @Transactional
    public Reservation confirmArrive(long reservationId, long requesterId) {
        Reservation reservation = getReservation(reservationId);
        memberCheck(requesterId, reservation.getMemberId());
        reservationStatusCheck(reservation);
        timeValidationCheck(reservation.getReservatedAt());
        reservation.arrive();

        return reservationRepository.save(reservation);
    }

    /**
     * 예약 승인 여부 체크
     */
    private static void reservationStatusCheck(Reservation reservation) {
        if (reservation.getReservationStatus() != ReservationStatus.CONFIRMED) {
            throw new RuntimeException("승인되지 않은 예약");
        }
    }

    /**
     * 예약자 정보 체크
     */
    private static void memberCheck(long requesterId, long reservationMemberId) {
        if (requesterId != reservationMemberId) {
            throw new RuntimeException("예약자 정보 불일치");
        }
    }

    /**
     * 리뷰 쓰기
     * 상점 별점 갱신 처리 추가 필요
     */
    @Transactional
    public Reservation writeReview(long reservationId, Review request) {
        Reservation reservation = getReservation(reservationId);
        memberCheck(reservation.getMemberId(), request.getRequesterId());
        reservationStatusCheck(reservation);
        if (LocalDateTime.now().isBefore(reservation.getReservatedAt().plusHours(1))) {
            throw new RuntimeException("리뷰 작성 가능한 시간이 아님");
        }
        reservation.writeReview(request);

        return reservationRepository.save(reservation);
    }

    /**
     * 리뷰 삭제
     * 작성자 및 점장 가능
     */
    @Transactional
    public Reservation deleteReview(long reservationId, long requesterId) {
        Reservation reservation = getReservation(reservationId);
        Store store = getStore(reservation.getStoreId());

        if (reservation.getMemberId() != requesterId && store.getMemberId() != requesterId) {
            throw new RuntimeException("리뷰 삭제 권한이 없습니다.");
        }
        reservation.deleteReview();

        return reservationRepository.save(reservation);
    }


    /**
     * 예약 시간 유효성 검사
     */
    private static void timeValidationCheck(LocalDateTime reservatedAt) {
        if (LocalDateTime.now().minusMinutes(10).isAfter(reservatedAt)) {
            throw new RuntimeException("유효하지 않은 예약 시간");
        }
    }
}
