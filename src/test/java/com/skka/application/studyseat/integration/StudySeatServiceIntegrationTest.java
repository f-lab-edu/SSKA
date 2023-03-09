package com.skka.application.studyseat.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.skka.application.studyseat.dto.ReserveSeatRequest;
import com.skka.domain.customer.Customer;
import com.skka.domain.studyseat.StudySeat;
import com.skka.domain.studyseat.repository.StudySeatRepository;
import com.skka.domain.studyseat.schedule.Schedule;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("여러 사용자가 한꺼번에 동시적으로 하나의 스케줄을 점유 하려고 할 때")
class StudySeatServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudySeatRepository studySeatRepository;

    @LocalServerPort
    int randomServerPort;

    private ExecutorService executorService;

    @BeforeEach
    void init() {
        executorService = Executors.newFixedThreadPool(5);
    }


    @Test
    @DisplayName("오버부킹 이슈가 일어나지 않는다.")
    @Transactional
    void overBookingTest() throws InterruptedException, URISyntaxException {
        // given
        ReserveSeatRequest request = new ReserveSeatRequest(
            1L,
            LocalDateTime.of(2023, 5, 10, 13, 10),
            LocalDateTime.of(2023, 5, 10, 17, 10)
        );

        long studySeatId = 2L;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReserveSeatRequest> httpEntity = new HttpEntity<>(request, headers);

        final String baseUrl = "/seats/" + studySeatId;
        URI uri = new URI(baseUrl);

        int threadCount = 5;
        List<Callable<ResponseEntity<String>>> tasks = new ArrayList<>();

        // when
        IntStream.range(0, threadCount)
            .mapToObj(i -> executorService.submit(() -> restTemplate.postForEntity(uri, httpEntity, String.class)))
            .forEach(future -> tasks.add(future::get));
        
        executorService.invokeAll(tasks);

        Optional<StudySeat> foundStudySeat = studySeatRepository.findById(studySeatId);

        int actualSize = getSizeBy(foundStudySeat);
        Schedule actual = getSchedulesBy(foundStudySeat);

        // then
        assertThat(actual).isNotNull();
        assertThat(actualSize).isEqualTo(1);
        assertThat(actual.getId()).isEqualTo(1L);

        Customer actualCustomer = actual.getCustomer();
        assertThat(actualCustomer.getId()).isEqualTo(1L);
        assertThat(actualCustomer.getName()).isEqualTo("용용");
        assertThat(actualCustomer.getEmail()).isEqualTo("yongyong@naver.com");
        assertThat(actualCustomer.getTel()).isEqualTo("010-1111-7777");

        StudySeat actualStudySeat = actual.getStudySeat();
        assertThat(actualStudySeat.getId()).isEqualTo(2L);
        assertThat(actualStudySeat.getSeatNumber()).isEqualTo("2");
        assertThat(actual.getStartedTime()).isEqualTo(LocalDateTime.of(2023, 5, 10, 13, 10));
        assertThat(actual.getEndTime()).isEqualTo(LocalDateTime.of(2023, 5, 10, 17, 10));
    }

    private Integer getSizeBy(Optional<StudySeat> studySeatOptionalSeat) {
        return studySeatOptionalSeat
            .map(seat -> seat.getSchedules().size())
            .orElse(null);
    }

    private Schedule getSchedulesBy(Optional<StudySeat> studySeatOptionalSeat) {
        return studySeatOptionalSeat
            .map(seat -> seat.getSchedules().get(0))
            .orElse(null);
    }

    @AfterEach
    public void teardown() {
        executorService.shutdown();
    }
}
