package com.zerobase.reservation.reservation.service;

import com.zerobase.reservation.reservation.entity.Store;
import com.zerobase.reservation.reservation.repository.StoreRepository;
import com.zerobase.reservation.reservation.request.StoreRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 상점 파트너 서비스
 * 상점 관련 로직중 파트너 권한이 필요한 로직
 */
@Service
@RequiredArgsConstructor
public class StorePartnerService {
    private final StoreRepository storeRepository;

    /**
     * 상점 등록
     */
    @Transactional
    public Store registrateStore(StoreRequest request) {
        Store store = Store.from(request);
        return storeRepository.save(store);
    }

    /**
     * 상점 정보 변경
     */
    @Transactional
    public Store updateStore(long storeId, StoreRequest request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("상점 정보를 찾을 수 없습니다."));
        validatePatnerId(request, store);

        store = Store.from(request);
        store.setId(storeId);
        return storeRepository.save(store);
    }

    /**
     * 관리자 검증
     */
    private static void validatePatnerId(StoreRequest request, Store store) {
        if (store.getMemberId() != request.getRequesterId()) {
            throw new RuntimeException("관리자 정보 불일치");
        }
    }

    /**
     * 상점 삭제
     */
    @Transactional
    public String deleteStore(long storeId, long requesterId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("상점 정보를 찾을 수 없습니다."));
        if (store.getMemberId() != requesterId) {
            throw new RuntimeException("사용자 정보 불일치");
        }
        storeRepository.deleteById(storeId);
        return store.getName();
    }

    /**
     * 특정 관리자의 상점 목록 전체 조회
     */
    public List<Store> findStoresByMemberId(long memberId) {
        return storeRepository.findAllByMemberId(memberId);
    }
}
