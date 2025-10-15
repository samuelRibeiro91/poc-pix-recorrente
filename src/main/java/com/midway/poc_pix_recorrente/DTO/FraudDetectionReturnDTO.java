package com.midway.poc_pix_recorrente.DTO;


import com.midway.poc_pix_recorrente.domain.Schedule;
import com.midway.poc_pix_recorrente.domain.ScheduleLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FraudDetectionReturnDTO {
    private Schedule.ScheduleStatus scheduleStatus;
    private ScheduleLog.LogStatus   logStatus;
    private String                  description;
}
