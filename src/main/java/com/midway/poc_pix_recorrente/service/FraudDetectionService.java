package com.midway.poc_pix_recorrente.service;

import com.midway.poc_pix_recorrente.DTO.FraudDetectionReturnDTO;
import com.midway.poc_pix_recorrente.domain.Schedule;
import com.midway.poc_pix_recorrente.domain.ScheduleLog;
import com.midway.poc_pix_recorrente.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Setter
@Getter
@AllArgsConstructor
public class FraudDetectionService {

    private static final BigDecimal MAX_VALUE = new BigDecimal("10000");

    private static final int  MAX_QUANTITY_SAME_KEY = 3;

    @Autowired
    private final ScheduleRepository scheduleRepository;

    private boolean suspiciousValue(BigDecimal value) {
        return value.compareTo(MAX_VALUE) > 0;
    }

    private boolean maxQuantitySameKey(UUID customerId, String keyDestination) {
        List<Schedule> findAllByCustomerAndDate = scheduleRepository.findAllByCustomerAndDate(customerId, keyDestination, Schedule.ScheduleStatus.ACTIVE);

        return (findAllByCustomerAndDate.size() >= MAX_QUANTITY_SAME_KEY);
    }

    private boolean duplicatedSchedule(Schedule schedule) {

        List<Schedule> findDuplicatesSchedules = scheduleRepository.findDuplicatesSchedules(
                schedule.getRecurringPayment().getPixDestinationKey(),
                schedule.getCustomer().getId(),
                Schedule.ScheduleStatus.ACTIVE,
                schedule.getId() != null ? schedule.getId() : UUID.randomUUID(),
                schedule.getRecurringPayment().getId() != null ? schedule.getRecurringPayment().getId() : UUID.randomUUID(),
                schedule.getDayOfMonth(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getFrequency(),
                schedule.getRecurringPayment().getValue()
        );

        return (!findDuplicatesSchedules.isEmpty());
    }

    public FraudDetectionReturnDTO validateSchedule(Schedule schedule) {

        //1 - Check Suspicious Value
        if  (suspiciousValue(schedule.getRecurringPayment().getValue())) {
            return new FraudDetectionReturnDTO(
                    Schedule.ScheduleStatus.PENDING,
                    ScheduleLog.LogStatus.MANUAL_REVIEW,
                    "Schedule requires manual review"
            );
        }

        //2 - Check Quantity Same Key
        if (maxQuantitySameKey(schedule.getCustomer().getId(), schedule.getRecurringPayment().getPixDestinationKey())){
            return new FraudDetectionReturnDTO(
                    Schedule.ScheduleStatus.REJECTED,
                    ScheduleLog.LogStatus.REJECTED,
                    "Schedule rejected due to same key limit"
            );
        }

        //3 -   Check Duplicate Schedule
        if (duplicatedSchedule(schedule)){
            return new FraudDetectionReturnDTO(
                    Schedule.ScheduleStatus.REJECTED,
                    ScheduleLog.LogStatus.REJECTED,
                    "Schedule rejected due to duplication"
            );
        }

        return new FraudDetectionReturnDTO(
            Schedule.ScheduleStatus.ACTIVE,
            ScheduleLog.LogStatus.APPROVED,
            "Schedule status is ACTIVE"
        );
    }
}
