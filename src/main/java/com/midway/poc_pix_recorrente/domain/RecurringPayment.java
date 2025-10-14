package com.midway.poc_pix_recorrente.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recurringpayment")
public class RecurringPayment {
    @Id
    @GeneratedValue
    private UUID id;
    private String          description;
    private String          pixDestinationKey;
    private BigDecimal value;
    private LocalDateTime created_at;
}
