package com.skka.application.customer;

import static com.skka.customer.CustomerFixture.CUSTOMER;
import static com.skka.schedule.ScheduleFixture.MOVING_SCHEDULE;
import static com.skka.schedule.ScheduleFixture.SCHEDUEL_ERROR;
import static com.skka.schedule.ScheduleFixture.SCHEDULE;
import static com.skka.studyseat.StudySeatFixture.STUDY_SEAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.skka.application.customer.dto.CommandAddStudyTime;
import com.skka.application.customer.dto.CommandMoveSeat;
import com.skka.application.customer.dto.CommandReserveSeat;
import com.skka.domain.customer.repository.CustomerRepository;
import com.skka.domain.schedule.Schedule;
import com.skka.domain.schedule.repository.ScheduleRepository;
import com.skka.domain.studyseat.repository.StudySeatRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Test
    @DisplayName("유저는 좌석을 예약할 수 있다.")
    void reserveSeat_test1() {

        // given
        CommandReserveSeat command = new CommandReserveSeat(
            1L,
            1L,
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(1L)
        );

        List<Schedule> scheduleList = getScheduleList();

        // when
        when(customerRepository.findById(command.getCustomerId()))
            .thenReturn(Optional.ofNullable(CUSTOMER));

        when(studySeatRepository.findById(command.getSeatNumber()))
            .thenReturn(Optional.ofNullable(STUDY_SEAT));

        when(scheduleRepository.findAllSchedulesByStartedEndTime(
            command.getStartedTime(),
            command.getEndTime(),
            command.getSeatNumber()
        ))
            .thenReturn(scheduleList);

        // then
        String actual = customerService.reserveSeat(command);
        assertThat(actual).isEqualTo(command.getSeatNumber() + "번 자리에 " + "예약 되었습니다.");
    }

    @Test
    @DisplayName(
        "유저는 등록 하고자 하는 좌석에 등록 하고자 하는 시간대에 이미 예약 되어 있으면 좌석을 예약할 수 없다."
    )
    void reserveSeat_test2() {

        // given
        CommandReserveSeat command = new CommandReserveSeat(
            1L,
            1L,
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(1L)
        );

        List<Schedule> scheduleList = getScheduleList();
        scheduleList.add(SCHEDULE);

        // when
        when(customerRepository.findById(command.getCustomerId()))
            .thenReturn(Optional.ofNullable(CUSTOMER));

        when(studySeatRepository.findById(command.getSeatNumber()))
            .thenReturn(Optional.ofNullable(STUDY_SEAT));

        when(scheduleRepository.findAllSchedulesByStartedEndTime(
            command.getStartedTime(),
            command.getEndTime(),
            command.getSeatNumber()
        ))
            .thenReturn(scheduleList);

        // then
        assertThatThrownBy(() -> customerService.reserveSeat(command))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 예약된 좌석 입니다.");
    }

    private List<Schedule> getScheduleList() {
        return new ArrayList<>();
    }


    @Test
    @DisplayName("유저는 좌석을 옮길 수 있다.")
    void moveSeat_test1() {

        // given
        CommandMoveSeat command = new CommandMoveSeat(
            1L,
            2L,
            LocalDateTime.of(2021, 1, 1, 0, 0, 0),
            LocalDateTime.of(2021, 1, 1, 2, 0, 0)
        );

        List<Schedule> scheduleList = getScheduleList();

        // when
        when(customerRepository.findById(command.getCustomerId()))
            .thenReturn(Optional.ofNullable(CUSTOMER));

        when(studySeatRepository.findById(command.getMovingSeatNumber()))
            .thenReturn(Optional.ofNullable(STUDY_SEAT));

        when(scheduleRepository.findAllSchedulesByStartedEndTime(
            command.getStartedTime(),
            command.getEndTime(),
            command.getMovingSeatNumber()
        ))
            .thenReturn(scheduleList);

        when(scheduleRepository.findScheduleByStartAndEndTime(
            command.getStartedTime(),
            command.getEndTime()
        ))
            .thenReturn(MOVING_SCHEDULE);

        // then
        String actual = customerService.moveSeat(command);
        assertThat(actual).isEqualTo(STUDY_SEAT.getSeatNumber() + "에서 " + command.getMovingSeatNumber() + "로 자리 옮기기 성공");
    }

    @Test
    @DisplayName(
        "유저는 옮기려고 하는 좌석에 같은 시간대가 이미 예약 되어 있으면 좌석을 옮길 수 없다."
    )
    void moveSeat_test2() {

        // given
        CommandMoveSeat command = new CommandMoveSeat(
            1L,
            2L,
            LocalDateTime.of(2021, 1, 1, 0, 0, 0),
            LocalDateTime.of(2021, 1, 1, 2, 0, 0)
        );

        List<Schedule> scheduleList = getScheduleList();
        scheduleList.add(SCHEDULE);
        scheduleList.add(MOVING_SCHEDULE);

        // when
        when(customerRepository.findById(command.getCustomerId()))
            .thenReturn(Optional.ofNullable(CUSTOMER));

        when(studySeatRepository.findById(command.getMovingSeatNumber()))
            .thenReturn(Optional.ofNullable(STUDY_SEAT));

        when(scheduleRepository.findAllSchedulesByStartedEndTime(
            command.getStartedTime(),
            command.getEndTime(),
            command.getMovingSeatNumber()
        ))
            .thenReturn(scheduleList);

        // given
        assertThatThrownBy(() -> customerService.moveSeat(command))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 예약된 좌석 입니다.");
    }

    @Test
    @DisplayName(
        "유저는 시간을 연장할 수 있다."
    )
    void addStudyTime_test1() {

        // given
        CommandAddStudyTime command = new CommandAddStudyTime(
            1L,
            1L,
            LocalDateTime.of(2021, 1, 1, 0, 0, 0),
            LocalDateTime.of(2021, 1, 1, 2, 0, 0),
            2L
        );

        List<Schedule> scheduleList = getScheduleList();

        // when
        when(scheduleRepository.findScheduleByStartAndEndTime(
            command.getStartedTime(),
            command.getEndTime()
        ))
            .thenReturn(SCHEDULE);

        when(scheduleRepository.findAllSchedulesFromEndTimeToPostponedEndTime(
            command.getEndTime(),
            command.getEndTime().plusHours(command.getPlusHour()),
            command.getSeatNumber()
        ))
            .thenReturn(scheduleList);

        // then
        String actual = customerService.addStudyTime(command);
        assertThat(actual).isEqualTo("ok");
    }

    @Test
    @DisplayName(
        "유저 자신만이 시간을 연장할 수 있다."
    )
    void addStudyTime_test2() {

        // given
        CommandAddStudyTime command = new CommandAddStudyTime(
            2L,
            1L,
            LocalDateTime.of(2021, 1, 1, 0, 0, 0),
            LocalDateTime.of(2021, 1, 1, 2, 0, 0),
            2L
        );

        // when
        when(scheduleRepository.findScheduleByStartAndEndTime(
            command.getStartedTime(),
            command.getEndTime()
        ))
            .thenReturn(SCHEDULE);

        assertThatThrownBy(() -> customerService.addStudyTime(command))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("자신의 예약 정보가 아닙니다.");
    }

    @Test
    @DisplayName(
        "유저 자신의 자리만 시간을 연장할 수 있다."
    )
    void addStudyTime_test3() {

        // given
        CommandAddStudyTime command = new CommandAddStudyTime(
            1L,
            2L,
            LocalDateTime.of(2021, 1, 1, 0, 0, 0),
            LocalDateTime.of(2021, 1, 1, 2, 0, 0),
            2L
        );

        // when
        when(scheduleRepository.findScheduleByStartAndEndTime(
            command.getStartedTime(),
            command.getEndTime()
        ))
            .thenReturn(SCHEDULE);

        assertThatThrownBy(() -> customerService.addStudyTime(command))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("자신의 자리가 아닙니다.");
    }

    @Test
    @DisplayName(
        "유저가 좌석 시간을 연장하고 싶어도 같은 좌석에 예약된 스케쥴과 겹치면 연장하지 못한다."
    )
    void addStudyTime_test4() {

        // given
        CommandAddStudyTime command = new CommandAddStudyTime(
            1L,
            1L,
            LocalDateTime.of(2021, 1, 10, 10, 0, 0),
            LocalDateTime.of(2021, 1, 10, 12, 0, 0),
            2L
        );

        List<Schedule> scheduleList = getScheduleList();
        scheduleList.add(SCHEDUEL_ERROR);

        // when
        when(scheduleRepository.findScheduleByStartAndEndTime(
            command.getStartedTime(),
            command.getEndTime()
        ))
            .thenReturn(SCHEDULE);

        when(scheduleRepository.findAllSchedulesFromEndTimeToPostponedEndTime(
            command.getEndTime(),
            command.getEndTime().plusHours(command.getPlusHour()),
            command.getSeatNumber()
        ))
            .thenReturn(scheduleList);

        // then
        assertThatThrownBy(() -> customerService.addStudyTime(command))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("다른 스케쥴과 겹치므로 다시 입력 하십시오.");
    }
}