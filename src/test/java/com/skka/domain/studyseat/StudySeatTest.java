package com.skka.domain.studyseat;

import static com.skka.studyseat.StudySeatFixture.STUDY_SEAT;
import static com.skka.studyseat.StudySeatFixture.studySeatConstructor;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.skka.domain.studyseat.error.InvalidStudySeatException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("StudySeat 는 ")
class StudySeatTest {

    @DisplayName("생성된다")
    @Test
    void test1() {
        assertDoesNotThrow(() -> new StudySeat(
            STUDY_SEAT.getId(),
            STUDY_SEAT.getSeatNumber(),
            STUDY_SEAT.getOccupied(),
            STUDY_SEAT.getStartEndTime().getStartedTime(),
            STUDY_SEAT.getStartEndTime().getEndTime(),
            STUDY_SEAT.getCustomer()
        ));
    }

    @DisplayName("올바르지 않은 좌석 번호일 때 생성 되지 않는다.")
    @Test
    void test2() {

            assertThatThrownBy(() -> new StudySeat(
            STUDY_SEAT.getId(),
            null,
            STUDY_SEAT.getOccupied(),
            STUDY_SEAT.getStartEndTime().getStartedTime(),
            STUDY_SEAT.getStartEndTime().getEndTime(),
            STUDY_SEAT.getCustomer()
        ))
            .isInstanceOf(InvalidStudySeatException.class)
            .hasMessage("올바르지 않은 좌석 번호 입니다.");
    }

    @DisplayName("좌석 차지 여부가 NULL일 때 생성 되지 않는다.")
    @Test
    void test3() {

        assertThatThrownBy(() -> new StudySeat(
            STUDY_SEAT.getId(),
            STUDY_SEAT.getSeatNumber(),
            null,
            STUDY_SEAT.getStartEndTime().getStartedTime(),
            STUDY_SEAT.getStartEndTime().getEndTime(),
            STUDY_SEAT.getCustomer()
        ))
            .isInstanceOf(InvalidStudySeatException.class)
            .hasMessage("좌석 차지 여부는 NULL일 수 없습니다.");
    }

    @DisplayName("시작 시간이 현재보다 이전일 때 생성 되지 않는다.")
    @Test
    void test4() {
        LocalDateTime time = LocalDateTime.now();

        assertThatThrownBy(() -> studySeatConstructor(
            time.minusMinutes(1L), time.plusHours(2L)))
            .isInstanceOf(InvalidStudySeatException.class)
            .hasMessage("시작 시간은 현재보다 이전일 수 없습니다.");
    }

    @DisplayName("끝나는 시간은 시작 시간 보다 이전일 때 생성 되지 않는다.")
    @Test
    void test5() {
        LocalDateTime time = LocalDateTime.now();

        assertThatThrownBy(() -> studySeatConstructor(
            time, time.minusHours(2L)))
            .isInstanceOf(InvalidStudySeatException.class)
            .hasMessage("끝나는 시간은 시작 시간 보다 이전일 수 없습니다.");
    }
}