package com.zerobase.reservation.reservation.service;

import com.zerobase.reservation.reservation.entity.Reservation;
import com.zerobase.reservation.reservation.entity.ReservationStatus;
import com.zerobase.reservation.reservation.entity.Store;
import com.zerobase.reservation.reservation.repository.ReservationRepository;
import com.zerobase.reservation.reservation.repository.StoreRepository;
import com.zerobase.reservation.reservation.request.ReservationConfirmation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 예약서비스 중 파트너 권한 필요한 로직들
 * 미구현 사항: 시간 초과한 예약에 대한 상태 변경(커스텀 익셉션)
 */
@Service
@RequiredArgsConstructor
public class ReservationPartnerService {
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    /**
     * 예약 승인 처리
     */
    @Transactional
    public Reservation confirmReservation(long reservationId, ReservationConfirmation request) {
        Reservation reservation = getReservation(reservationId);
        Store store = getStore(reservation.getStoreId());
        if (store.getMemberId() != request.getRequesterId()) {
            throw new RuntimeException("점장 정보 불일치");
        }
        timeValidationCheck(reservation.getReservatedAt());
        if (reservation.getReservationStatus() != ReservationStatus.WAITING_CONFIRM) {
            throw new RuntimeException("승인 대기 중인 예약이 아님");
        }

        reservation.confirm(request.isConfirm());

        return reservationRepository.save(reservation);
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
     * 승인 대기 예약 목록 조회
     */
    public List<Reservation> getWaitingConfirmList(long requesterId) {
        List<Long> storeIdList = storeRepository.findAllByMemberId(requesterId).stream()
                .map(store -> store.getId())
                .collect(Collectors.toList());
        ;
        List<Reservation> reservationList = new ArrayList<>();
        for (long storeId : storeIdList) {
            reservationList.addAll(reservationRepository.findAllByStoreIdAndReservationStatus(storeId, ReservationStatus.WAITING_CONFIRM));
        }
        return reservationList;
    }

    /**
     * 예약 시간 유효성 검사
     */
    private void timeValidationCheck(LocalDateTime reservatedAt) {
        if (LocalDateTime.now().minusMinutes(10).isAfter(reservatedAt)) {
            throw new RuntimeException("유효하지 않은 예약 시간");
        }
    }
}
