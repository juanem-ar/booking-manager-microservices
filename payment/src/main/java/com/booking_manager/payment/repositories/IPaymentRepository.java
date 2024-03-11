package com.booking_manager.payment.repositories;

import com.booking_manager.payment.models.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPaymentRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity findByBookingIdAndDeleted(Long id, boolean b);

    List<PaymentEntity> findAllByBookingIdAndDeleted(Long id, boolean b);
}
