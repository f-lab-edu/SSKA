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
        "SELECT s FROM schedule s "
            + "LEFT JOIN s.studySeat ss "
            + "LEFT JOIN s.customer c "
            + "WHERE "
                + "("
                    + "NOT"
                        + "( "
                            + "(:endTime <= s.startedTime) "
                                + "OR (s.startedTime <= :startedTime AND :startedTime <= :endTime) "
                        + ") "
                    + "OR (s.startedTime <= :startedTime AND :endTime <= s.endTime) "
                    + "OR (s.endTime >= :startedTime AND s.endTime < :endTime)"
            + ") "
            + "AND s.studySeat.id = :studySeatId "
    )
    List<Schedule> findAllSchedulesByStartedEndTime(
        LocalDateTime startedTime,
        LocalDateTime endTime,
        long studySeatId
    );

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
        @Param("startedTime") LocalDateTime startedTime,
        @Param("endTime") LocalDateTime endTime
    );
}
