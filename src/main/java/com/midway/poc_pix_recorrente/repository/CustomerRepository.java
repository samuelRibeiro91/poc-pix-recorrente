package com.midway.poc_pix_recorrente.repository;

import com.midway.poc_pix_recorrente.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> { }
