package com.midway.poc_pix_recorrente.controller;

import com.midway.poc_pix_recorrente.domain.ScheduleLog;
import com.midway.poc_pix_recorrente.service.ScheduleLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/log")
@Tag(name = "Schedules Log")
public class ScheduleLogController {

    @Autowired
    private ScheduleLogService scheduleLogService;

    @GetMapping("v1/logs")
    public List<ScheduleLog> findAllByScheduleId(@RequestParam UUID idSchedule) {
        return scheduleLogService.findAllByIDSchedule(idSchedule);
    }
}
