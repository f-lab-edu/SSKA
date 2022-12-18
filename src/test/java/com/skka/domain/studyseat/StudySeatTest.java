package com.skka.domain.studyseat;

import static com.skka.studyseat.StudySeatFixture.STUDY_SEAT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.skka.domain.studyseat.error.InvalidStudySeatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("StudySeat 는 ")
class StudySeatTest {

    @DisplayName("생성된다")
    @Test
    void test1() {
        assertDoesNotThrow(() -> StudySeat.of(
            STUDY_SEAT.getId(),
            STUDY_SEAT.getSeatNumber(),
            STUDY_SEAT.getOccupied()
        ));
    }

    @DisplayName("올바르지 않은 좌석 번호일 때 생성 되지 않는다.")
    @Test
    void test2() {

            assertThatThrownBy(() -> StudySeat.of(
            STUDY_SEAT.getId(),
            null,
            STUDY_SEAT.getOccupied()
        ))
            .isInstanceOf(InvalidStudySeatException.class)
            .hasMessage("올바르지 않은 좌석 번호 입니다.");
    }

    @DisplayName("좌석 차지 여부가 NULL일 때 생성 되지 않는다.")
    @Test
    void test3() {

        assertThatThrownBy(() -> StudySeat.of(
            STUDY_SEAT.getId(),
            STUDY_SEAT.getSeatNumber(),
            null
        ))
            .isInstanceOf(InvalidStudySeatException.class)
            .hasMessage("좌석 차지 여부는 NULL일 수 없습니다.");
    }
}