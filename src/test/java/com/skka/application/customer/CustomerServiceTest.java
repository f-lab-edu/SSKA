package com.skka.application.customer;

import static com.skka.customer.CustomerFixture.CUSTOMER;
import static com.skka.schedule.ScheduleFixture.MOVING_SCHEDULE;
import static com.skka.schedule.ScheduleFixture.SCHEDUEL_SERVICE_TEST;
import static com.skka.schedule.ScheduleFixture.SCHEDULE;
import static com.skka.studyseat.StudySeatFixture.STUDY_SEAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.skka.application.customer.dto.MoveSeatRequest;
import com.skka.application.customer.dto.ReserveSeatRequest;
import com.skka.application.customer.response.CommandMoveSeatResponse;
import com.skka.application.customer.response.CommandReserveSeatResponse;
import com.skka.application.customer.webrequest.CommandReserveSeatWebRequestV1;
import com.skka.domain.customer.repository.CustomerRepository;
import com.skka.domain.schedule.repository.ScheduleRepository;
import com.skka.domain.studyseat.repository.StudySeatRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private StudySeatRepository studySeatRepository;
    @Mock
    private ScheduleRepository scheduleRepository;

    @BeforeAll
    static void init() {
        STUDY_SEAT.getSchedules().add(SCHEDUEL_SERVICE_TEST);
    }

    @Test
    @DisplayName("유저는 좌석을 예약할 수 있다.")
    void reserveSeat_test1() {

        // given
        ReserveSeatRequest command = new ReserveSeatRequest(
            1L,
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(1L)
        );

        long studySeatId = 1L;

        // when
        when(customerRepository.findById(command.getCustomerId()))
            .thenReturn(Optional.ofNullable(CUSTOMER));

        when(studySeatRepository.findById(studySeatId))
            .thenReturn(Optional.ofNullable(STUDY_SEAT));

        // then
        CommandReserveSeatResponse actual = customerService.reserveSeat(command, studySeatId);
        assertThat(actual.getMessage()).isEqualTo("success");
        assertThat(actual.getReservedSeatId()).isEqualTo(1L);
    }

    @Test
    @DisplayName(
        "유저는 등록 하고자 하는 좌석에 등록 하고자 하는 시간대에 이미 예약 되어 있으면 좌석을 예약할 수 없다."
    )
    void reserveSeat_test2() {

        CommandReserveSeatWebRequestV1 command = new CommandReserveSeatWebRequestV1(
            1L,
            LocalDateTime.of(2023,1,10,13,0),
            LocalDateTime.of(2023,1,10,17,0)
        );

        assertThrows(IllegalStateException.class,
            () -> SCHEDULE.getStudySeat().isReservable(
                command.getStartedTime(), command.getEndTime()
            ), "다른 스케쥴과 겹칩니다.");
    }

    @Test
    @DisplayName("유저는 좌석을 옮길 수 있다.")
    void moveSeat_test1() {

        // given
        MoveSeatRequest command = new MoveSeatRequest(
            1L,
            LocalDateTime.of(2021, 1, 1, 0, 0, 0),
            LocalDateTime.of(2021, 1, 1, 2, 0, 0)
        );

        long movingStudySeatId = 1L;

        // when
        when(customerRepository.findById(command.getCustomerId()))
            .thenReturn(Optional.ofNullable(CUSTOMER));

        when(studySeatRepository.findById(movingStudySeatId))
            .thenReturn(Optional.ofNullable(STUDY_SEAT));

        when(scheduleRepository.findScheduleByStartedTimeAndEndTime(
            command.getStartedTime(),
            command.getEndTime()
        ))
            .thenReturn(MOVING_SCHEDULE);

        // then
        CommandMoveSeatResponse actual = customerService.moveSeat(command, movingStudySeatId);
        assertThat(actual.getMessage()).isEqualTo("success");
        assertThat(actual.getMovedSeatId()).isEqualTo(1L);
    }

    @Test
    @DisplayName(
        "유저는 옮기려고 하는 좌석에 같은 시간대가 이미 예약 되어 있으면 좌석을 옮길 수 없다."
    )
    void moveSeat_test2() {

        CommandReserveSeatWebRequestV1 command = new CommandReserveSeatWebRequestV1(
            1L,
            LocalDateTime.of(2023,1,10,13,0),
            LocalDateTime.of(2023,1,10,17,0)
        );

        assertThrows(IllegalStateException.class,
            () -> SCHEDULE.getStudySeat().isReservable(
                command.getStartedTime(), command.getEndTime()
            ), "다른 스케쥴과 겹칩니다.");
    }
}