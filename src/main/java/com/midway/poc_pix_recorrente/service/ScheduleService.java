package com.midway.poc_pix_recorrente.service;

import com.midway.poc_pix_recorrente.domain.Schedule;
import com.midway.poc_pix_recorrente.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Setter
@Getter
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository repository;

    public Optional<Schedule> getSchedule(UUID id){
        return repository.findById(id);
    }

    public Schedule save(Schedule schedule){
        return repository.save(schedule);
    }

    public Schedule update(UUID id, Schedule schedule){
        return repository.save(schedule);
    }


    public void delete(UUID id){
        Optional<Schedule> schedule = repository.findById(id);

        if (schedule.isPresent()){repository.delete(repository.findById(id).get());}
    }


}

