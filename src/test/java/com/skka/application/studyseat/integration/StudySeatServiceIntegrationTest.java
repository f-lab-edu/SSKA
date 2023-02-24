package com.skka.application.studyseat.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.skka.application.studyseat.dto.ReserveSeatRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.After;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Disabled("Disabled until application is up!")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudySeatServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);


    @Test
    @DisplayName("여러 사용자가 한꺼번에 동시적으로 하나의 스케줄을 점유 하려고 할 때 오버부킹 이슈가 일어나지 않는다.")
    @Transactional
    public void overBookingTest() throws InterruptedException, ExecutionException {
        // given
        ReserveSeatRequest request = new ReserveSeatRequest(
            1L,
            LocalDateTime.of(2023, 5, 10, 13, 10),
            LocalDateTime.of(2023, 5, 10, 17, 10)
        );

        long studySeatId = 2L;

        List<Callable<ResponseEntity<String>>> tasks = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReserveSeatRequest> httpEntity = new HttpEntity<>(request, headers);

        int threadCount = 5;

        // when
        for (int i = 0; i < threadCount; i++) {
            tasks.add(() -> restTemplate.exchange("http://localhost:8080/seats/" + studySeatId, HttpMethod.POST, httpEntity, String.class));
        }

        List<Future<ResponseEntity<String>>> results = executorService.invokeAll(tasks);

        // then
        Map<HttpStatus, Integer> actual = new HashMap<>();

        int badRequestCount = 1;
        for (Future<ResponseEntity<String>> result : results) {
            ResponseEntity<String> response = result.get();
            if (HttpStatus.BAD_REQUEST.equals(response.getStatusCode())) {
                actual.put(HttpStatus.BAD_REQUEST, badRequestCount);
                badRequestCount++;
            }
        }
        assertEquals(actual.get(HttpStatus.BAD_REQUEST), threadCount - 1);
    }

    @After
    public void teardown() {
        executorService.shutdown();
    }
}
