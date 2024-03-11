package com.zerobase.reservation.reservation.entity;

import com.zerobase.reservation.reservation.request.StoreRequest;
import jakarta.persistence.*;
import lombok.*;

/**
 * 상점 Entity
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String businessRegistrationNumber;

    private long memberId;
    private String location;
    private double rating;

    public static Store from(StoreRequest request) {
        return Store.builder()
                .name(request.getName())
                .businessRegistrationNumber(request.getBusinessRegistrationNumber())
                .memberId(request.getMemberId())
                .location(request.getLocation())
                .build();
    }
}
