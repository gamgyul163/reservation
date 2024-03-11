package com.zerobase.reservation.reservation.repository;

import com.zerobase.reservation.reservation.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAllByMemberId(long partnerId);

}
