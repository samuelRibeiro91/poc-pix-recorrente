package com.midway.poc_pix_recorrente.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.midway.poc_pix_recorrente.DTO.FraudDetectionReturnDTO;
import com.midway.poc_pix_recorrente.domain.IdempotencyKeys;
import com.midway.poc_pix_recorrente.domain.Schedule;
import com.midway.poc_pix_recorrente.repository.IdempotencyKeysRepository;
import com.midway.poc_pix_recorrente.service.FraudDetectionService;
import com.midway.poc_pix_recorrente.service.ScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Schedules")
@AllArgsConstructor
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private FraudDetectionService fraudDetectionService;

    @Autowired
    private final IdempotencyKeysRepository idempotencyKeysRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String scheduleToJSON(Schedule schedule) {
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            return objectMapper.writeValueAsString(schedule);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar Agendamento", e);
        }
    }

    @GetMapping("v1/agendamentos/{id}")
    public Optional<Schedule> schedule(@PathVariable UUID id){
        return scheduleService.getSchedule(id);
    }

    @DeleteMapping("v1/agendamentos/{id}")
    public void deleteSchedule(@PathVariable UUID id){ scheduleService.delete(id);}

    @PostMapping("v1/agendamentos")
    public ResponseEntity<?> scheduleInsert(@RequestHeader("Idempotency-Key") UUID key, @RequestBody Schedule body){

        Optional<IdempotencyKeys> existing = idempotencyKeysRepository.findById(key);

        if (existing.isPresent()) {
            return ResponseEntity.status(existing.get().getHttp_status())
                    .body(existing.get().getResponse_body());
        }

        FraudDetectionReturnDTO fraudDetectionReturnDTO = fraudDetectionService.validateSchedule(body);

        Schedule schedule = scheduleService.save(body, fraudDetectionReturnDTO);

        IdempotencyKeys record = new IdempotencyKeys();
        record.setId(key);
        record.setEndPoint("v1/agendamentos");
        record.setHttp_status(201);
        record.setResponse_body(scheduleToJSON(schedule));
        idempotencyKeysRepository.save(record);

        return ResponseEntity.status(201).body(schedule);
    }

    @PutMapping("v1/agendamentos/{id}")
    public  ResponseEntity<?> scheduleUpdate(@PathVariable UUID id, @RequestHeader("Idempotency-Key") UUID key, @RequestBody Schedule body){

        Optional<IdempotencyKeys> existing = idempotencyKeysRepository.findById(key);

        if (existing.isPresent()) {
            return ResponseEntity.status(existing.get().getHttp_status())
                    .body(existing.get().getResponse_body());
        }

        body.setId(id);

        FraudDetectionReturnDTO fraudDetectionReturnDTO = fraudDetectionService.validateSchedule(body);

        Schedule schedule = scheduleService.update(id, body, fraudDetectionReturnDTO);

        IdempotencyKeys record = new IdempotencyKeys();
        record.setId(key);
        record.setEndPoint("v1/agendamentos/{id}");
        record.setHttp_status(200);
        record.setResponse_body(scheduleToJSON(schedule));
        idempotencyKeysRepository.save(record);

        return ResponseEntity.status(200).body(schedule);
    }

}
