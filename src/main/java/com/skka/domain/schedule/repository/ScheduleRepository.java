package com.skka.domain.schedule.repository;

import com.skka.domain.schedule.Schedule;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(
        value =
            "SELECT * FROM schedule s "
                + "WHERE "
                + "s.started_time = :startedTime "
                + "AND s.end_time = :endTime "
                + "LIMIT 1 "
        , nativeQuery = true
    )
    Schedule findScheduleByStartAndEndTime(
        LocalDateTime startedTime,
        LocalDateTime endTime
    );
}
