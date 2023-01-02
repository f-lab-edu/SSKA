package com.skka.domain.schedule.repository;

import com.skka.domain.schedule.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(
        value =
            "SELECT * FROM schedule s "
                + "WHERE s.started_time BETWEEN :startedTime AND :endTime "
                + "OR s.end_time BETWEEN :startedTime AND :endTime "
                + "HAVING s.study_seat_id = :studySeatId "
        , nativeQuery = true
    )
    List<Schedule> findAllSchedulesByStartedEndTime(
        LocalDateTime startedTime,
        LocalDateTime endTime,
        long studySeatId
    );
}
