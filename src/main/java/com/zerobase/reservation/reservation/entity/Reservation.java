package com.zerobase.reservation.reservation.entity;

import com.zerobase.reservation.reservation.request.ReservationRequest;
import com.zerobase.reservation.reservation.request.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 예약 Entity
 * 리뷰는 별도 Entity를 생성하지 않고 예약에 종속된 정보로 처리하였음
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long storeId;

    private long memberId;
    private String phoneNumber;

    private LocalDateTime reservatedAt;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private String reviewerName;
    private Long rating;
    private String reviewText;
    private LocalDateTime reviewedAt;

    public static Reservation from(ReservationRequest request) {
        return Reservation.builder()
                .storeId(request.getStoreId())
                .memberId(request.getRequesterId())
                .phoneNumber(request.getPhoneNumber())
                .reservatedAt(request.getReservatedAt())
                .reservationStatus(ReservationStatus.WAITING_CONFIRM)
                .build();
    }

    public void confirm(boolean isConfirm) {
        if (isConfirm) {
            reservationStatus = ReservationStatus.CONFIRMED;
        } else {
            reservationStatus = ReservationStatus.DENIED;
        }
    }

    public void arrive() {
        reservationStatus = ReservationStatus.ARRIVED;
    }

    public void writeReview(Review review) {
        reviewerName = review.getReviewerName();
        rating = review.getRating();
        reviewText = review.getReviewText();
        reviewedAt = LocalDateTime.now();
    }

    public void deleteReview() {
        rating = null;
        reviewText = null;
        reviewedAt = null;
    }
}
