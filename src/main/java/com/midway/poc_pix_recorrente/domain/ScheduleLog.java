package com.midway.poc_pix_recorrente.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
@Table(name = "schedule_log")
public class ScheduleLog {
    @Id
    @GeneratedValue
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    @JsonIgnore
    private Schedule      schedule;
    private LocalDateTime created_at;
    private LogStatus     status;
    private String        description;

    public enum LogStatus {
        APPROVED,
        REJECTED,
        MANUAL_REVIEW
    }
}
