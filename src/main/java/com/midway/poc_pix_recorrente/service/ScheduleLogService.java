package com.midway.poc_pix_recorrente.service;

import com.midway.poc_pix_recorrente.domain.ScheduleLog;
import com.midway.poc_pix_recorrente.repository.ScheduleLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ScheduleLogService {

    @Autowired
    private ScheduleLogRepository repository;

    public ScheduleLog save(ScheduleLog scheduleLog) {
        return repository.save(scheduleLog);
    }

    public List<ScheduleLog> findAllByIDSchedule(UUID idSchedule) {
        return repository.findAllByScheduleId(idSchedule);
    }

}
