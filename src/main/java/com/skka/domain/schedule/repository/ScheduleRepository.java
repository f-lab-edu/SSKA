package com.skka.domain.schedule.repository;

import com.skka.domain.schedule.Schedule;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Schedule findScheduleByStartedTimeAndEndTime(
        LocalDateTime startedTime,
        LocalDateTime endTime
    );
}
