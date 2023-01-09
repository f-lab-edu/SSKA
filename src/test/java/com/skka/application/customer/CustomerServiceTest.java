package com.skka.application.customer;

import static com.skka.customer.CustomerFixture.CUSTOMER;
import static com.skka.schedule.ScheduleFixture.SCHEDUEL_SERVICE_TEST;
import static com.skka.schedule.ScheduleFixture.SCHEDULE;
import static com.skka.studyseat.StudySeatFixture.MOVING_STUDY_SEAT;
import static com.skka.studyseat.StudySeatFixture.STUDY_SEAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

        ReserveSeatRequest command = new ReserveSeatRequest(
            1L,
            LocalDateTime.of(2023,1,10,17,0),
            LocalDateTime.of(2023,1,10,20,0)
        );

        long studySeatId = 1L;

        when(customerRepository.findById(command.getCustomerId()))
            .thenReturn(Optional.ofNullable(CUSTOMER));

        when(studySeatRepository.findById(studySeatId))
            .thenReturn(Optional.ofNullable(STUDY_SEAT));

        CommandReserveSeatResponse actual = customerService.reserveSeat(command, studySeatId);
        assertThat(actual.getMessage()).isEqualTo("success");
        assertThat(actual.getReservedSeatId()).isEqualTo(1L);
    }

    @ParameterizedTest
    @MethodSource("inputForTest")
    @DisplayName(
        "유저는 등록 하고자 하는 좌석에 등록 하고자 하는 시간대에 이미 예약 되어 있으면 좌석을 예약할 수 없다."
    )
    void reserveSeat_test9_time_validation(LocalDateTime startedTime, LocalDateTime endTime) {

        CommandReserveSeatWebRequestV1 command = new CommandReserveSeatWebRequestV1(
            1L,
            startedTime,
            endTime
        );

        assertThrows(IllegalStateException.class,
            () -> SCHEDULE.getStudySeat().isReservable(
                command.getStartedTime(), command.getEndTime()
            ), "다른 스케쥴과 겹칩니다.");
    }

    private static Collection<Arguments> inputForTest() {
        Collection<Arguments> list = new ArrayList<>();

        list.add(Arguments.of( // case 1
            LocalDateTime.of(2023,1,10,13,0),
            LocalDateTime.of(2023,1,10,17,0)
        ));

        list.add(Arguments.of( // case 2
            LocalDateTime.of(2023,1,10,11,0),
            LocalDateTime.of(2023,1,10,13,0)
        ));

        list.add(Arguments.of( // case 3
            LocalDateTime.of(2023,1,10,13,0),
            LocalDateTime.of(2023,1,10,14,0)
        ));

        list.add(Arguments.of( // case 4
            LocalDateTime.of(2023,1,10,13,0),
            LocalDateTime.of(2023,1,10,14,0)
        ));

        list.add(Arguments.of( // case 5
            LocalDateTime.of(2023,1,10,12,0),
            LocalDateTime.of(2023,1,10,14,0)
        ));

        list.add(Arguments.of( // case 6
            LocalDateTime.of(2023,1,10,13,0),
            LocalDateTime.of(2023,1,10,15,0)
        ));

        list.add(Arguments.of( // case 7
            LocalDateTime.of(2023,1,10,12,0),
            LocalDateTime.of(2023,1,10,15,0)
        ));

        return list;
    }

    @Test
    @DisplayName("유저는 좌석을 옮길 수 있다.")
    void moveSeat_test1() {

        MoveSeatRequest command = new MoveSeatRequest(
            1L,
            LocalDateTime.of(2023,1,10,17,0),
            LocalDateTime.of(2023,1,10,20,0)
        );

        long movingStudySeatId = 2L;

        when(scheduleRepository.findScheduleByStartedTimeAndEndTime(
            command.getStartedTime(),
            command.getEndTime()
        ))
            .thenReturn(SCHEDULE);

        when(customerRepository.findById(command.getCustomerId()))
            .thenReturn(Optional.ofNullable(CUSTOMER));

        when(studySeatRepository.findById(movingStudySeatId))
            .thenReturn(Optional.ofNullable(MOVING_STUDY_SEAT));

        CommandMoveSeatResponse actual = customerService.moveSeat(command, movingStudySeatId);

        assertThat(actual.getMessage()).isEqualTo("success");
        assertThat(actual.getMovedSeatId()).isEqualTo(2L);
    }

    @Test
    @DisplayName(
        "유저는 옮기려고 하는 좌석에 같은 시간대가 이미 예약 되어 있으면 좌석을 옮길 수 없다."
    )
    void moveSeat_test2() {

        CommandReserveSeatWebRequestV1 command = new CommandReserveSeatWebRequestV1(
            1L,
            LocalDateTime.of(2023, 1, 10, 13, 0),
            LocalDateTime.of(2023, 1, 10, 17, 0)
        );

        assertThrows(IllegalStateException.class,
            () -> SCHEDULE.getStudySeat().isReservable(
                command.getStartedTime(), command.getEndTime()
            ), "다른 스케쥴과 겹칩니다.");
    }
}