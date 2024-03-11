package com.zerobase.reservation.reservation.dto;

import com.zerobase.reservation.reservation.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상점 간략 정보
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SimpleStoreInfo {
    private long id;
    private String name;
    private double rating;

    public static SimpleStoreInfo from(Store store) {
        return SimpleStoreInfo.builder()
                .id(store.getId())
                .name(store.getName())
                .rating(store.getRating())
                .build();
    }

}
