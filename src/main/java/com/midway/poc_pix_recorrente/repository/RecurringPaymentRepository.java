package com.midway.poc_pix_recorrente.repository;

import com.midway.poc_pix_recorrente.domain.RecurringPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecurringPaymentRepository extends JpaRepository<RecurringPayment, UUID> {}