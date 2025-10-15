package com.midway.poc_pix_recorrente.repository;

import com.midway.poc_pix_recorrente.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

    @Query("SELECT s FROM Schedule s " +
            "WHERE s.recurringPayment.pixDestinationKey = :pixDestinationKey " +
            "AND  s.customer.id = :customerId " +
            "AND  s.status =  :status")
    List<Schedule> findAllByCustomerAndDate(@Param("customerId") UUID customerId,
                                            @Param("pixDestinationKey") String pixDestinationKey,
                                            @Param("status") Schedule.ScheduleStatus status);


    @Query("""
    SELECT s FROM Schedule s
    WHERE s.recurringPayment.pixDestinationKey = :pixDestinationKey
      AND s.recurringPayment.value             = :value
      AND s.customer.id = :customerId
      AND s.dayOfMonth = :dayOfMonth
      AND s.startDate  = :startDate
      AND s.frequency  = :frequency
      AND s.endDate    = :endDate
      AND s.status = :status
      AND s.id <> :scheduleId
      AND s.recurringPayment.id <> :recurringPaymentId
""")
    List<Schedule> findDuplicatesSchedules(
            @Param("pixDestinationKey") String pixDestinationKey,
            @Param("customerId") UUID customerId,
            @Param("status") Schedule.ScheduleStatus status,
            @Param("scheduleId") UUID scheduleId,
            @Param("recurringPaymentId") UUID recurringPaymentId,
            @Param("dayOfMonth") int dayOfMonth,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("frequency") Schedule.FrequencyRecurrence frequency,
            @Param("value") BigDecimal value

    );

}