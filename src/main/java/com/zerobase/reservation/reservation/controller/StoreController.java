package com.zerobase.reservation.reservation.controller;

import com.zerobase.reservation.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 상점 서비스 요청을 처리하는 컨트롤러
 * 인증하지 않아도 사용 가능하다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    /**
     * 모든 상점 목록
     * 요청에따라 상점 목록 페이지를 응답한다.
     */
    @GetMapping
    public ResponseEntity<?> getAllStore(final Pageable pageable) {
        return ResponseEntity.ok(storeService.getSimpleStoreList(pageable));
    }

    /**
     * 상점 세부 정보
     * 상점의 세부 정보를 조회한다.
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<?> getStoreDetail(@PathVariable long storeId) {
        return ResponseEntity.ok(storeService.findStoreDetail(storeId));
    }
}
