package com.zerobase.reservation.reservation.dto;

import com.zerobase.reservation.reservation.entity.Store;
import com.zerobase.reservation.reservation.request.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 상점 세부 정보
 * 리뷰를 리스트 형태로 담도록 설계
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DetailedStoreInfo {
    private long id;
    private String name;
    private String location;
    private double rating;

    private List<Review> reviewList;

    public static DetailedStoreInfo from(Store store, List<Review> reviewList) {
        return DetailedStoreInfo.builder()
                .id(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .rating(store.getRating())
                .reviewList(reviewList)
                .build();
    }

}
