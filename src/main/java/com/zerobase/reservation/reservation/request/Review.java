package com.zerobase.reservation.reservation.request;

import com.zerobase.reservation.reservation.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 리뷰 추가,수정 폼
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private long requesterId;
    private String reviewerName;
    private long rating;
    private String reviewText;
    private LocalDateTime reviewedAt;

    public static Review from(Reservation reservation) {
        return Review.builder()
                .reviewerName(reservation.getReviewerName())
                .reviewText(reservation.getReviewText())
                .rating(reservation.getRating())
                .reviewedAt(reservation.getReviewedAt())
                .build();
    }
}
