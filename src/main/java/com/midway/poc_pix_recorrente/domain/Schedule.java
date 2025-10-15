package com.midway.poc_pix_recorrente.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;
    private String                description;
    private FrequencyRecurrence   frequency;
    private LocalDate             startDate;
    private LocalDate             endDate;
    private int                   dayOfMonth;
    private LocalDateTime         created_at;
    private ScheduleStatus        status;
    private LocalDateTime         lastPaymentData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recurringpayment_id", referencedColumnName = "id")
    private RecurringPayment      recurringPayment;


    public enum FrequencyRecurrence {
        DAILY,
        WEEKLY,
        MONTHLY
    }

    public enum ScheduleStatus {
        ACTIVE,
        PAUSED,
        CANCELED
    }
}
