package com.zerobase.reservation.reservation.service;

import com.zerobase.reservation.reservation.entity.Store;
import com.zerobase.reservation.reservation.dto.DetailedStoreInfo;
import com.zerobase.reservation.reservation.dto.SimpleStoreInfo;
import com.zerobase.reservation.reservation.repository.ReservationRepository;
import com.zerobase.reservation.reservation.repository.StoreRepository;
import com.zerobase.reservation.reservation.request.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 상점 관련 기능 중 권한 불필요한 로직
 */
@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 상점 상세 정보 조회
     */
    public DetailedStoreInfo findStoreDetail(long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("상점 정보를 찾을 수 없습니다."));

        List<Review> reviewList = reservationRepository.findByStoreId(store.getId()).stream()
                .map(reservation -> Review.from(reservation))
                .collect(Collectors.toList());

        return DetailedStoreInfo.from(store, reviewList);
    }

    /**
     * 상점 약식 정보 페이지 조회
     */
    public Page<SimpleStoreInfo> getSimpleStoreList(Pageable pageable) {
        Page<Store> storePage = storeRepository.findAll(pageable);
        Page<SimpleStoreInfo> simpleStoreInfo = convertPage(storePage);
        return simpleStoreInfo;
    }

    /**
     * 상점 페이지를 상점 약식 정보 페이지로 전환
     */
    private static Page<SimpleStoreInfo> convertPage(Page<Store> storePage) {
        List<SimpleStoreInfo> simpleStoreInfoList = storePage.getContent()
                .stream()
                .map((c) -> SimpleStoreInfo.from(c))
                .collect(Collectors.toList());
        return new PageImpl<>(simpleStoreInfoList, storePage.getPageable(), storePage.getTotalElements());
    }
}