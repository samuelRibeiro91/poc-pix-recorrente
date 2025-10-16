package com.midway.poc_pix_recorrente.service;

import com.midway.poc_pix_recorrente.DTO.FraudDetectionReturnDTO;
import com.midway.poc_pix_recorrente.domain.Customer;
import com.midway.poc_pix_recorrente.domain.RecurringPayment;
import com.midway.poc_pix_recorrente.domain.Schedule;
import com.midway.poc_pix_recorrente.domain.ScheduleLog;
import com.midway.poc_pix_recorrente.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.midway.poc_pix_recorrente.domain.Schedule.FrequencyRecurrence.MONTHLY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FraudDetectionServiceTest {

    private ScheduleRepository scheduleRepository;
    private FraudDetectionService fraudDetectionService;

    @BeforeEach
    void setUp() {
        scheduleRepository = mock(ScheduleRepository.class);
        fraudDetectionService = new FraudDetectionService(scheduleRepository);
    }


    private Schedule createSchedule(BigDecimal value) {
        Schedule schedule = new Schedule();
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());

        RecurringPayment recurringPayment = new RecurringPayment();
        recurringPayment.setPixDestinationKey("pix@teste.com");
        recurringPayment.setValue(value);
        recurringPayment.setId(UUID.randomUUID());

        schedule.setId(UUID.randomUUID());
        schedule.setCustomer(customer);
        schedule.setRecurringPayment(recurringPayment);
        schedule.setDayOfMonth(10);
        schedule.setStartDate(LocalDate.now());
        schedule.setEndDate(LocalDate.now().plusMonths(1));
        schedule.setFrequency(MONTHLY);

        return schedule;
    }

    @Test
    void mustReturnManualReviewWhenValueIsSuspicious() {
        Schedule schedule = createSchedule(new BigDecimal("15000"));

        FraudDetectionReturnDTO result = fraudDetectionService.validateSchedule(schedule);

        assertEquals(Schedule.ScheduleStatus.PENDING, result.getScheduleStatus());
        assertEquals(ScheduleLog.LogStatus.MANUAL_REVIEW, result.getLogStatus());
        assertEquals("Schedule requires manual review", result.getDescription());
    }

    @Test
    void mustRejectWhenExceedQuantitySameKey() {
        Schedule schedule = createSchedule(new BigDecimal("5000"));

        when(scheduleRepository.findAllByCustomerAndDate(
                any(UUID.class),
                anyString(),
                eq(Schedule.ScheduleStatus.ACTIVE))
        ).thenReturn(List.of(new Schedule(), new Schedule(), new Schedule()));

        FraudDetectionReturnDTO result = fraudDetectionService.validateSchedule(schedule);

        assertEquals(Schedule.ScheduleStatus.REJECTED, result.getScheduleStatus());
        assertEquals(ScheduleLog.LogStatus.REJECTED, result.getLogStatus());
        assertEquals("Schedule rejected due to same key limit", result.getDescription());
    }

    @Test
    void mustRejectWhenDuplicate() {
        Schedule schedule = createSchedule(new BigDecimal("5000"));

        when(scheduleRepository.findAllByCustomerAndDate(any(), anyString(), any()))
                .thenReturn(Collections.emptyList());

        when(scheduleRepository.findDuplicatesSchedules(
                anyString(),
                any(UUID.class),
                any(Schedule.ScheduleStatus.class),
                any(UUID.class),
                any(UUID.class),
                anyInt(),
                any(LocalDate.class),
                any(LocalDate.class),
                any(Schedule.FrequencyRecurrence.class),
                any(BigDecimal.class)
        )).thenReturn(List.of(new Schedule()));

        FraudDetectionReturnDTO result = fraudDetectionService.validateSchedule(schedule);

        assertEquals(Schedule.ScheduleStatus.REJECTED, result.getScheduleStatus());
        assertEquals(ScheduleLog.LogStatus.REJECTED, result.getLogStatus());
        assertEquals("Schedule rejected due to duplication", result.getDescription());
    }

    @Test
    void mustApproveWhenNotSuspiciousOrDuplicate() {
        Schedule schedule = createSchedule(new BigDecimal("5000"));

        when(scheduleRepository.findAllByCustomerAndDate(any(), anyString(), any()))
                .thenReturn(Collections.emptyList());

        when(scheduleRepository.findDuplicatesSchedules(
                anyString(),
                any(UUID.class),
                any(Schedule.ScheduleStatus.class),
                any(UUID.class),
                any(UUID.class),
                anyInt(),
                any(LocalDate.class),
                any(LocalDate.class),
                any(Schedule.FrequencyRecurrence.class),
                any(BigDecimal.class)
        )).thenReturn(Collections.emptyList());

        FraudDetectionReturnDTO result = fraudDetectionService.validateSchedule(schedule);

        assertEquals(Schedule.ScheduleStatus.ACTIVE, result.getScheduleStatus());
        assertEquals(ScheduleLog.LogStatus.APPROVED, result.getLogStatus());
        assertEquals("Schedule status is ACTIVE", result.getDescription());
    }
}
