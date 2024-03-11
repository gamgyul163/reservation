package com.zerobase.reservation.reservation.request;

import lombok.Getter;

/**
 * 상점 추가, 수정 요청 폼
 */
@Getter
public class StoreRequest {
    private Long requesterId;
    private String name;
    private String businessRegistrationNumber;
    private Long memberId;
    private String location;
}
