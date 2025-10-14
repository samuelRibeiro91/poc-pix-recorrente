package com.midway.poc_pix_recorrente.repository;

import com.midway.poc_pix_recorrente.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {}