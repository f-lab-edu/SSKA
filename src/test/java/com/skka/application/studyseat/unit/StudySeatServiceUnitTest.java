package com.skka.application.studyseat.unit;

import static com.skka.customer.CustomerFixture.CUSTOMER;
import static com.skka.schedule.ScheduleFixture.SCHEDULE;
import static com.skka.schedule.ScheduleFixture.SCHEDULE_2;
import static com.skka.studyseat.StudySeatFixture.STUDY_SEAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.skka.adapter.common.exception.BadRequestException;
import com.skka.application.studyseat.StudySeatService;
import com.skka.application.studyseat.dto.ChangeStudyTimeRequest;
import com.skka.application.studyseat.dto.CheckoutScheduleRequest;
import com.skka.application.studyseat.dto.ReserveSeatRequest;
import com.skka.application.studyseat.response.CommandCheckOutScheduleResponse;
import com.skka.application.studyseat.response.CommandChangeStudyTimeResponse;
import com.skka.application.studyseat.response.CommandExtractScheduleResponse;
import com.skka.application.studyseat.response.CommandReserveSeatResponse;
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
class StudySeatServiceUnitTest {

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
            .thenReturn(Optional.of(CUSTOMER));

        when(studySeatRepository.findByIdForLock(studySeatId))
            .thenReturn(Optional.of(STUDY_SEAT));

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
                .thenReturn(Optional.of(CUSTOMER));

            when(studySeatRepository.findByIdForLock(studySeatId))
                .thenReturn(Optional.of(STUDY_SEAT));

            assertThrows(BadRequestException.class,
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
                .thenReturn(Optional.of(CUSTOMER));

            when(studySeatRepository.findByIdForLock(studySeatId))
                .thenReturn(Optional.of(STUDY_SEAT));

            assertThrows(BadRequestException.class,
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
                .thenReturn(Optional.of(CUSTOMER));

            when(studySeatRepository.findByIdForLock(studySeatId))
                .thenReturn(Optional.of(STUDY_SEAT));

            assertThrows(BadRequestException.class,
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
                .thenReturn(Optional.of(CUSTOMER));

            when(studySeatRepository.findByIdForLock(studySeatId))
                .thenReturn(Optional.of(STUDY_SEAT));

            assertThrows(BadRequestException.class,
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
                .thenReturn(Optional.of(CUSTOMER));

            when(studySeatRepository.findByIdForLock(studySeatId))
                .thenReturn(Optional.of(STUDY_SEAT));

            assertThrows(BadRequestException.class,
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
                .thenReturn(Optional.of(CUSTOMER));

            when(studySeatRepository.findByIdForLock(studySeatId))
                .thenReturn(Optional.of(STUDY_SEAT));

            assertThrows(BadRequestException.class,
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
                .thenReturn(Optional.of(CUSTOMER));

            when(studySeatRepository.findByIdForLock(studySeatId))
                .thenReturn(Optional.of(STUDY_SEAT));

            assertThrows(BadRequestException.class,
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
                .thenReturn(Optional.of(CUSTOMER));

            when(studySeatRepository.findByIdForLock(studySeatId))
                .thenReturn(Optional.of(STUDY_SEAT));

            assertThrows(BadRequestException.class,
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
                .thenReturn(Optional.of(CUSTOMER));

            when(studySeatRepository.findByIdForLock(studySeatId))
                .thenReturn(Optional.of(STUDY_SEAT));

            assertThrows(BadRequestException.class,
                () -> studySeatService.reserveSeat(command, studySeatId),
                "다른 스케쥴과 겹칩니다.");
        }
    }


    @Test
    @DisplayName("유저는 좌석을 옮길 수 있다.")
    void moveSeat_test1() {

        long extractingStudySeatId = 1L;
        long scheduleId = 0L;

        // when
        when(studySeatRepository.findById(extractingStudySeatId))
            .thenReturn(Optional.of(STUDY_SEAT));

        // then
        CommandExtractScheduleResponse actual = studySeatService.extractSchedule(extractingStudySeatId, scheduleId);

        assertThat(actual.getMessage()).isEqualTo("success");
        assertThat(actual.getStudySeatIdExtractedSchedule()).isEqualTo(extractingStudySeatId);
        assertThat(actual.getExtractedScheduleId()).isEqualTo(scheduleId);
    }

    @Test
    @DisplayName("추츌 하려는 좌석이 없으면 에러가 발생한다.")
    void moveSeat_test2() {

        STUDY_SEAT.getSchedules().clear();

        long scheduleId = 0L;
        long studySeatId = 1L;

        // when
        when(studySeatRepository.findById(studySeatId))
            .thenReturn(Optional.of(STUDY_SEAT));

        assertThrows(BadRequestException.class,
            () -> studySeatService.extractSchedule(
                studySeatId, scheduleId)
            , "스케줄이 존재하지 않습니다.");
    }


    @Test
    @DisplayName("유저는 시간을 변경할 수 있다.")
    void changeStudyTime_test1() {

        // given
        ChangeStudyTimeRequest command = new ChangeStudyTimeRequest(
            1L,
            LocalDateTime.of(2023, 1, 10, 11, 10),
            LocalDateTime.of(2023, 1, 10, 17, 10)
        );

        long studySeatId = 1L;
        long scheduleId = 0L;

        // when
        when(studySeatRepository.findById(studySeatId))
            .thenReturn(Optional.of(STUDY_SEAT));

        when(customerRepository.findById(command.getCustomerId()))
            .thenReturn(Optional.of(CUSTOMER));

        when(studySeatRepository.findByIdForLock(studySeatId))
            .thenReturn(Optional.of(STUDY_SEAT));

        // then
        CommandChangeStudyTimeResponse actual = studySeatService.changeStudyTime(
            command, studySeatId, scheduleId);

        assertThat(actual.getMessage()).isEqualTo("success");
        assertThat(actual.getChangedStartedTime()).isEqualTo(
            LocalDateTime.of(2023, 1, 10, 11, 10)
        );
        assertThat(actual.getChangedEndTime()).isEqualTo(
            LocalDateTime.of(2023, 1, 10, 17, 10)
        );
    }

    @Test
    @DisplayName(
        "유저는 좌석을 최소 1시간을 이용 해야 한다."
    )
    void changeStudyTime_test2() {

        // given
        ChangeStudyTimeRequest command = new ChangeStudyTimeRequest(
            1L,
            LocalDateTime.of(2023, 1, 10, 11, 10),
            LocalDateTime.of(2023, 1, 10, 11, 50)
        );

        long studySeatId = 1L;
        long scheduleId = 0L;

        // when
        when(studySeatRepository.findById(studySeatId))
            .thenReturn(Optional.of(STUDY_SEAT));

        when(customerRepository.findById(command.getCustomerId()))
            .thenReturn(Optional.of(CUSTOMER));

        // then
        assertThrows(BadRequestException.class,
            () -> studySeatService.changeStudyTime(
                command, studySeatId, scheduleId),
            "이용 시간은 최소 1시간 이상 입니다.");
    }

    @Test
    @DisplayName(
        "유저가 좌석 시간을 연장하고 싶어도 같은 좌석에 예약된 스케쥴과 겹치면 연장하지 못한다."
    )
    void changeStudyTime_test3() {

        STUDY_SEAT.getSchedules().add(SCHEDULE_2);

        ChangeStudyTimeRequest command = new ChangeStudyTimeRequest(
            1L,
            LocalDateTime.of(2023, 1, 10, 15, 10),
            LocalDateTime.of(2023, 1, 10, 17, 10)
        );

        long studySeatId = 1L;
        long scheduleId = 0L;

        // when
        when(studySeatRepository.findById(studySeatId))
            .thenReturn(Optional.of(STUDY_SEAT));

        when(customerRepository.findById(command.getCustomerId()))
            .thenReturn(Optional.of(CUSTOMER));

        when(studySeatRepository.findByIdForLock(studySeatId))
            .thenReturn(Optional.of(STUDY_SEAT));

        assertThrows(BadRequestException.class,
            () -> studySeatService.changeStudyTime(
                command, studySeatId, scheduleId),
            "다른 스케쥴과 겹칩니다.");
    }


    @Test
    @DisplayName("유저는 스케줄을 취소할 수 있다.")
    void checkoutSchedule_test1() {

        // given
        SCHEDULE.getCustomer().getSchedules().add(SCHEDULE);

        CheckoutScheduleRequest command = new CheckoutScheduleRequest(
            "check-out"
        );

        long studySeatId = 1L;
        long scheduleId = 0L;

        // when
        when(studySeatRepository.findById(studySeatId))
            .thenReturn(Optional.of(STUDY_SEAT));

        // then
        CommandCheckOutScheduleResponse actual = studySeatService.checkoutSchedule(
            command,
            studySeatId,
            scheduleId
        );

        assertThat(actual.getMessage()).isEqualTo("success");
        assertThat(actual.getCheckedOutScheduleId()).isEqualTo(0L);
    }

    @Test
    @DisplayName("유저는 스케줄 번호가 올바르지 않으면 취소할 수 없다.")
    void checkoutSchedule_test2() {

        // given
        SCHEDULE.getCustomer().getSchedules().add(SCHEDULE);

        CheckoutScheduleRequest command = new CheckoutScheduleRequest(
            "check-out"
        );

        long studySeatId = 1L;
        long scheduleId = 2L;

        // when
        when(studySeatRepository.findById(studySeatId))
            .thenReturn(Optional.of(STUDY_SEAT));

        // then
        assertThrows(BadRequestException.class,
            () -> studySeatService.checkoutSchedule(
                command,
                studySeatId,
                scheduleId
            ), "스케줄이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("유저는 잘못된 스케줄 상태를 입력하면 취소할 수 없다.")
    void checkoutSchedule_test3() {

        // given
        SCHEDULE.getCustomer().getSchedules().add(SCHEDULE);

        CheckoutScheduleRequest command = new CheckoutScheduleRequest(
            "checkout"
        );

        long studySeatId = 1L;
        long scheduleId = 2L;

        // when
        when(studySeatRepository.findById(studySeatId))
            .thenReturn(Optional.of(STUDY_SEAT));

        // then
        assertThrows(BadRequestException.class,
            () -> studySeatService.checkoutSchedule(
                command,
                studySeatId,
                scheduleId
            ), "잘못된 스케줄 상태 입니다.");
    }
}
