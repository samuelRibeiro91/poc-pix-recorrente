package com.midway.poc_pix_recorrente.repository;

import com.midway.poc_pix_recorrente.domain.ScheduleLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ScheduleLogRepository extends JpaRepository<ScheduleLog, UUID> {
    List<ScheduleLog> findAllByScheduleId(UUID scheduleId);
}