package com.skka.domain.schedule;

import static com.skka.schedule.ScheduleFixture.SCHEDULE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.skka.adapter.common.exception.BadRequestException;
import com.skka.domain.studyseat.schedule.Schedule;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Schedule 은 ")
class ScheduleTest {

    @DisplayName("생성된다")
    @Test
    void test1() {
        assertDoesNotThrow(() -> Schedule.of(
            SCHEDULE.getCustomer(),
            SCHEDULE.getStudySeat(),
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(1L)
        ));
    }

    @DisplayName("Customer 가 없으면 생성되지 않는다.")
    @Test
    void test2() {
        assertThatThrownBy(() -> Schedule.of(
            null,
            SCHEDULE.getStudySeat(),
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(2L)
        ))
            .isInstanceOf(BadRequestException.class)
            .hasMessage("올바르지 않은 고객 입니다.");
    }

    @DisplayName("StudySeat 이 없으면 생성되지 않는다.")
    @Test
    void test3() {
        assertThatThrownBy(() -> Schedule.of(
            SCHEDULE.getCustomer(),
            null,
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(2L)
        ))
            .isInstanceOf(BadRequestException.class)
            .hasMessage("올바르지 않은 좌석 입니다.");
    }

    @DisplayName("최소 이용 시간이 1시간이 넘지 않으면 생성되지 않는다")
    @Test
    void test4() {
        assertThatThrownBy(() -> Schedule.of(
            SCHEDULE.getCustomer(),
            SCHEDULE.getStudySeat(),
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(0L)
        ))
            .isInstanceOf(BadRequestException.class)
            .hasMessage("이용 시간은 최소 1시간 이상 입니다.");
    }
}
