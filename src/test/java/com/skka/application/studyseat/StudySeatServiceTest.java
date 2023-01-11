package com.skka.application.studyseat;

import static com.skka.customer.CustomerFixture.CUSTOMER;
import static com.skka.schedule.ScheduleFixture.SCHEDULE;
import static com.skka.studyseat.StudySeatFixture.STUDY_SEAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.skka.application.studyseat.dto.ReserveSeatRequest;
import com.skka.application.studyseat.response.CommandReserveSeatResponse;
import com.skka.application.studyseat.webrequest.CommandReserveSeatWebRequestV1;
import com.skka.domain.customer.repository.CustomerRepository;
import com.skka.domain.studyseat.repository.StudySeatRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudySeatServiceTest {

    private CustomerRepository customerRepository = mock(CustomerRepository.class);
    private StudySeatRepository studySeatRepository = mock(StudySeatRepository.class);

    private StudySeatService studySeatService = new StudySeatService(
        customerRepository,
        studySeatRepository
    );

    @BeforeEach
    void before() {
        STUDY_SEAT.getSchedules().add(SCHEDULE);
    }

    @AfterEach
    void after() {
        STUDY_SEAT.getSchedules().clear();
    }

    @Test
    @DisplayName("유저는 좌석을 예약할 수 있다.")
    void reserveSeat_test1() {

        // given
        ReserveSeatRequest command = new ReserveSeatRequest(
            1L,
            LocalDateTime.of(2023,1,10,17,0),
            LocalDateTime.of(2023,1,10,20,0)
        );

        long studySeatId = 1L;

        // when
        when(customerRepository.findById(command.getCustomerId()))
            .thenReturn(Optional.ofNullable(CUSTOMER));

        when(studySeatRepository.findById(studySeatId))
            .thenReturn(Optional.ofNullable(STUDY_SEAT));

        // then
        CommandReserveSeatResponse actual = studySeatService.reserveSeat(command, studySeatId);
        assertThat(actual.getMessage()).isEqualTo("success");
        assertThat(actual.getReservedSeatId()).isEqualTo(1L);
    }

    @Nested
    @DisplayName("9가지 경우의 수에 대한 시간 검증 테스트")
    class time_validation_test {

        @Test
        @DisplayName("Case 1")
        void reserveSeat_test_time_validation_1 () {

            ReserveSeatRequest command = new ReserveSeatRequest(
                1L,
                LocalDateTime.of(2023, 1, 10, 13, 0),
                LocalDateTime.of(2023, 1, 10, 14, 0)
            );

            long studySeatId = 1L;

            when(customerRepository.findById(command.getCustomerId()))
                .thenReturn(Optional.ofNullable(CUSTOMER));

            when(studySeatRepository.findById(studySeatId))
                .thenReturn(Optional.ofNullable(STUDY_SEAT));

            assertThrows(IllegalStateException.class,
                () -> studySeatService.reserveSeat(command, studySeatId),
                "다른 스케쥴과 겹칩니다.");
        }

        @Test
        @DisplayName("Case 2")
        void reserveSeat_test_time_validation_2 () {

            ReserveSeatRequest command = new ReserveSeatRequest(
                1L,
                LocalDateTime.of(2023, 1, 10, 13, 0),
                LocalDateTime.of(2023, 1, 10, 16, 0)
            );

            long studySeatId = 1L;

            when(customerRepository.findById(command.getCustomerId()))
                .thenReturn(Optional.ofNullable(CUSTOMER));

            when(studySeatRepository.findById(studySeatId))
                .thenReturn(Optional.ofNullable(STUDY_SEAT));

            assertThrows(IllegalStateException.class,
                () -> studySeatService.reserveSeat(command, studySeatId),
                "다른 스케쥴과 겹칩니다.");
        }

        @Test
        @DisplayName("Case 3")
        void reserveSeat_test_time_validation_3 () {

            ReserveSeatRequest command = new ReserveSeatRequest(
                1L,
                LocalDateTime.of(2023, 1, 10, 11, 0),
                LocalDateTime.of(2023, 1, 10, 14, 0)
            );

            long studySeatId = 1L;

            when(customerRepository.findById(command.getCustomerId()))
                .thenReturn(Optional.ofNullable(CUSTOMER));

            when(studySeatRepository.findById(studySeatId))
                .thenReturn(Optional.ofNullable(STUDY_SEAT));

            assertThrows(IllegalStateException.class,
                () -> studySeatService.reserveSeat(command, studySeatId),
                "다른 스케쥴과 겹칩니다.");
        }

        @Test
        @DisplayName("Case 4")
        void reserveSeat_test_time_validation_4 () {

            ReserveSeatRequest command = new ReserveSeatRequest(
                1L,
                LocalDateTime.of(2023, 1, 10, 11, 0),
                LocalDateTime.of(2023, 1, 10, 16, 0)
            );

            long studySeatId = 1L;

            when(customerRepository.findById(command.getCustomerId()))
                .thenReturn(Optional.ofNullable(CUSTOMER));

            when(studySeatRepository.findById(studySeatId))
                .thenReturn(Optional.ofNullable(STUDY_SEAT));

            assertThrows(IllegalStateException.class,
                () -> studySeatService.reserveSeat(command, studySeatId),
                "다른 스케쥴과 겹칩니다.");
        }

        @Test
        @DisplayName("Case 5")
        void reserveSeat_test_time_validation_5 () {

            ReserveSeatRequest command = new ReserveSeatRequest(
                1L,
                LocalDateTime.of(2023, 1, 10, 12, 0),
                LocalDateTime.of(2023, 1, 10, 14, 0)
            );

            long studySeatId = 1L;

            when(customerRepository.findById(command.getCustomerId()))
                .thenReturn(Optional.ofNullable(CUSTOMER));

            when(studySeatRepository.findById(studySeatId))
                .thenReturn(Optional.ofNullable(STUDY_SEAT));

            assertThrows(IllegalStateException.class,
                () -> studySeatService.reserveSeat(command, studySeatId),
                "다른 스케쥴과 겹칩니다.");
        }

        @Test
        @DisplayName("Case 6")
        void reserveSeat_test_time_validation_6 () {

            ReserveSeatRequest command = new ReserveSeatRequest(
                1L,
                LocalDateTime.of(2023, 1, 10, 14, 0),
                LocalDateTime.of(2023, 1, 10, 15, 10)
            );

            long studySeatId = 1L;

            when(customerRepository.findById(command.getCustomerId()))
                .thenReturn(Optional.ofNullable(CUSTOMER));

            when(studySeatRepository.findById(studySeatId))
                .thenReturn(Optional.ofNullable(STUDY_SEAT));

            assertThrows(IllegalStateException.class,
                () -> studySeatService.reserveSeat(command, studySeatId),
                "다른 스케쥴과 겹칩니다.");
        }

        @Test
        @DisplayName("Case 7")
        void reserveSeat_test_time_validation_7 () {

            ReserveSeatRequest command = new ReserveSeatRequest(
                1L,
                LocalDateTime.of(2023, 1, 10, 12, 10),
                LocalDateTime.of(2023, 1, 10, 15, 10)
            );

            long studySeatId = 1L;

            when(customerRepository.findById(command.getCustomerId()))
                .thenReturn(Optional.ofNullable(CUSTOMER));

            when(studySeatRepository.findById(studySeatId))
                .thenReturn(Optional.ofNullable(STUDY_SEAT));

            assertThrows(IllegalStateException.class,
                () -> studySeatService.reserveSeat(command, studySeatId),
                "다른 스케쥴과 겹칩니다.");
        }

        @Test
        @DisplayName("Case 8")
        void reserveSeat_test_time_validation_8 () {

            ReserveSeatRequest command = new ReserveSeatRequest(
                1L,
                LocalDateTime.of(2023, 1, 10, 10, 10),
                LocalDateTime.of(2023, 1, 10, 15, 10)
            );

            long studySeatId = 1L;

            when(customerRepository.findById(command.getCustomerId()))
                .thenReturn(Optional.ofNullable(CUSTOMER));

            when(studySeatRepository.findById(studySeatId))
                .thenReturn(Optional.ofNullable(STUDY_SEAT));

            assertThrows(IllegalStateException.class,
                () -> studySeatService.reserveSeat(command, studySeatId),
                "다른 스케쥴과 겹칩니다.");
        }

        @Test
        @DisplayName("Case 9")
        void reserveSeat_test_time_validation_9 () {

            ReserveSeatRequest command = new ReserveSeatRequest(
                1L,
                LocalDateTime.of(2023, 1, 10, 12, 10),
                LocalDateTime.of(2023, 1, 10, 17, 10)
            );

            long studySeatId = 1L;

            when(customerRepository.findById(command.getCustomerId()))
                .thenReturn(Optional.ofNullable(CUSTOMER));

            when(studySeatRepository.findById(studySeatId))
                .thenReturn(Optional.ofNullable(STUDY_SEAT));

            assertThrows(IllegalStateException.class,
                () -> studySeatService.reserveSeat(command, studySeatId),
                "다른 스케쥴과 겹칩니다.");
        }
    }
}