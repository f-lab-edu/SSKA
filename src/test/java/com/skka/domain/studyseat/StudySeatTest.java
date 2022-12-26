package com.skka.domain.studyseat;

import static com.skka.studyseat.StudySeatFixture.STUDY_SEAT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
            STUDY_SEAT.isOccupied()
        ));
    }

    @DisplayName("올바르지 않은 좌석 번호일 때 생성 되지 않는다.")
    @Test
    void test2() {

            assertThatThrownBy(() -> StudySeat.of(
            STUDY_SEAT.getId(),
            null,
            STUDY_SEAT.isOccupied()
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("올바르지 않은 좌석 번호 입니다.");
    }
}