package com.midway.poc_pix_recorrente.controller;

import com.midway.poc_pix_recorrente.DTO.FraudDetectionReturnDTO;
import com.midway.poc_pix_recorrente.domain.Schedule;
import com.midway.poc_pix_recorrente.service.FraudDetectionService;
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

    @Autowired
    private FraudDetectionService fraudDetectionService;

    @GetMapping("v1/agendamentos/{id}")
    public Optional<Schedule> schedule(@PathVariable UUID id){
        return scheduleService.getSchedule(id);
    }

    @DeleteMapping("v1/agendamentos/{id}")
    public void deleteSchedule(@PathVariable UUID id){ scheduleService.delete(id);}

    @PostMapping("v1/agendamentos")
    public Schedule scheduleInsert(@RequestBody Schedule body){
        FraudDetectionReturnDTO fraudDetectionReturnDTO = fraudDetectionService.validateSchedule(body);

        return scheduleService.save(body, fraudDetectionReturnDTO);
    }

    @PutMapping("v1/agendamentos/{id}")
    public Schedule scheduleUpdate(@PathVariable UUID id, @RequestBody Schedule body){
        body.setId(id);

        FraudDetectionReturnDTO fraudDetectionReturnDTO = fraudDetectionService.validateSchedule(body);

        return scheduleService.update(id, body, fraudDetectionReturnDTO);
    }

}
