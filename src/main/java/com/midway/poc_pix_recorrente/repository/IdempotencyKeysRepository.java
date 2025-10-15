package com.midway.poc_pix_recorrente.repository;

import com.midway.poc_pix_recorrente.domain.IdempotencyKeys;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IdempotencyKeysRepository extends JpaRepository<IdempotencyKeys, UUID> { }
