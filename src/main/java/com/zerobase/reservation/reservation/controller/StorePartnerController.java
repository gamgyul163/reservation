package com.zerobase.reservation.reservation.controller;

import com.zerobase.reservation.reservation.request.StoreRequest;
import com.zerobase.reservation.reservation.service.StorePartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 상점 서비스 요청을 처리하는 컨트롤러
 * 파트너 인증이 필요한 요청들이다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner/store")
public class StorePartnerController {

    private final StorePartnerService storePartnerService;

    /**
     * 상점 추가
     */
    @PostMapping
    public ResponseEntity<?> addStore(@RequestBody StoreRequest request) {
        return ResponseEntity.ok(storePartnerService.registrateStore(request));
    }

    /**
     * 상점 정보 업데이트
     */
    @PutMapping("/{storeId}")
    public ResponseEntity<?> updateStore(@PathVariable long storeId, @RequestBody StoreRequest request) {
        return ResponseEntity.ok(storePartnerService.updateStore(storeId, request));
    }

    /**
     * 상점 정보 삭제
     */
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteStore(@PathVariable long storeId, @RequestBody long requesterId) {
        return ResponseEntity.ok(storePartnerService.deleteStore(storeId, requesterId));
    }

    /**
     * 내 상점 목록 조회
     */
    @GetMapping("/myStore")
    public ResponseEntity<?> getStores(@RequestBody long memberId) {
        return ResponseEntity.ok(storePartnerService.findStoresByMemberId(memberId));
    }
}
