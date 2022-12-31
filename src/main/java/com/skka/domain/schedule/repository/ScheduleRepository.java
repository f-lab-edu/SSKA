package com.skka.domain.schedule.repository;

import com.skka.domain.schedule.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
        @Param("startedTime") LocalDateTime startedTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("studySeatId") long studySeatId
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

    @Query(
        value =
            "SELECT * FROM schedule s "
                + "WHERE s.study_seat_id = :studySeatId "
                + "AND NOT(( "
                + ":postponedTime <= s.started_time "
                + ") "
                + "OR ( "
                + "s.end_time <= :endTime "
                + "AND "
                + ":endTime <= :postponedTime "
                + ") "
                + ") "
        , nativeQuery = true
    )
    List<Schedule> findAllSchedulesFromEndTimeToPostponedEndTime(
        @Param("endTime") LocalDateTime endTime,
        @Param("postponedTime") LocalDateTime postponedTime,
        @Param("studySeatId") long studySeatId
    );
}
