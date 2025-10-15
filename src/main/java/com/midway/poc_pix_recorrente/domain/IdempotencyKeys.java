package com.midway.poc_pix_recorrente.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "idempotency_keys")
public class IdempotencyKeys {
    @Id
    private UUID   id;
    private String endPoint;
    private String response_body;
    private int http_status;
    private LocalDateTime created_at;
}
