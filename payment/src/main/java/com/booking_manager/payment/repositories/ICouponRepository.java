package com.booking_manager.payment.repositories;

import com.booking_manager.payment.models.entities.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICouponRepository extends JpaRepository<CouponEntity,Long> {
   Boolean existsByCodeAndDeleted(String code, boolean deleted);

   List<CouponEntity> findAllByDeleted(Boolean deleted);

   Boolean existsByIdAndDeleted(Long id, boolean deleted);

   CouponEntity getReferenceByCodeAndDeleted(String code, boolean deleted);
}
