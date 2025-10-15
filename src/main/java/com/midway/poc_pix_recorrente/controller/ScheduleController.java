package com.midway.poc_pix_recorrente.controller;

import com.midway.poc_pix_recorrente.domain.Schedule;
import com.midway.poc_pix_recorrente.service.ScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("v1/agendamentos/{id}")
    public Optional<Schedule> schedule(@PathVariable UUID id){
        return scheduleService.getSchedule(id);
    }

    @DeleteMapping("v1/agendamentos/{id}")
    public void deleteSchedule(@PathVariable UUID id){ scheduleService.delete(id);}

    @PostMapping("v1/agendamentos")
    public Schedule scheduleInsert(@RequestBody Schedule body){
        return scheduleService.save(body);
    }

    @PutMapping("v1/agendamentos/{id}")
    public Schedule scheduleUpdate(@PathVariable UUID id, @RequestBody Schedule body){
        body.setId(id);
        return scheduleService.update(id, body);
    }

}
