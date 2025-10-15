package com.midway.poc_pix_recorrente.service;

import com.midway.poc_pix_recorrente.DTO.FraudDetectionReturnDTO;
import com.midway.poc_pix_recorrente.domain.Schedule;
import com.midway.poc_pix_recorrente.domain.ScheduleLog;
import com.midway.poc_pix_recorrente.repository.ScheduleLogRepository;
import com.midway.poc_pix_recorrente.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Setter
@Getter
@AllArgsConstructor
public class ScheduleService {

    @Autowired
    private final ScheduleRepository repository;

    @Autowired
    private final ScheduleLogRepository scheduleLogRepository;

    public Optional<Schedule> getSchedule(UUID id){
        return repository.findById(id);
    }

    public Schedule save(Schedule schedule, FraudDetectionReturnDTO fraudDetectionReturnDTO){

        schedule.setStatus(fraudDetectionReturnDTO.getScheduleStatus());
        schedule.setCreated_at(LocalDateTime.now());

        Schedule saveReturn = repository.save(schedule);

        scheduleLogRepository.save(
                new ScheduleLog(
                        null,
                        schedule,
                        LocalDateTime.now(),
                        fraudDetectionReturnDTO.getLogStatus(),
                        fraudDetectionReturnDTO.getDescription()
                ));

        return saveReturn;
    }

    public Schedule update(UUID id, Schedule schedule, FraudDetectionReturnDTO fraudDetectionReturnDTO){

        schedule.setStatus(fraudDetectionReturnDTO.getScheduleStatus());

        Schedule saveReturn = repository.save(schedule);

        scheduleLogRepository.save(
                new ScheduleLog(
                        null,
                        schedule,
                        LocalDateTime.now(),
                        fraudDetectionReturnDTO.getLogStatus(),
                        fraudDetectionReturnDTO.getDescription()
                        ));

        return saveReturn;
    }


    public void delete(UUID id){
        Optional<Schedule> schedule = repository.findById(id);

        if (schedule.isPresent()){repository.delete(repository.findById(id).get());}
    }


}

