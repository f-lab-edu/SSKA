package com.skka.adaptor.controller.studyseat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skka.adaptor.controller.studyseat.webrequest.CommandChangeStudyTimeWebRequestV1;
import com.skka.adaptor.controller.studyseat.webrequest.CommandCheckoutScheduleWebRequestV1;
import com.skka.adaptor.controller.studyseat.webrequest.CommandReserveSeatWebRequestV1;
import com.skka.application.studyseat.StudySeatService;
import com.skka.application.studyseat.response.CommandChangeStudyTimeResponse;
import com.skka.application.studyseat.response.CommandCheckOutScheduleResponse;
import com.skka.application.studyseat.response.CommandExtractScheduleResponse;
import com.skka.application.studyseat.response.CommandReserveSeatResponse;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StudySeatController.class)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class StudySeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudySeatService studySeatService;

    @Test
    @DisplayName("스케줄을 예약한다.")
    void reserveSeat() throws Exception {
        CommandReserveSeatWebRequestV1 command = new CommandReserveSeatWebRequestV1(
            1L,
            LocalDateTime.of(2023,1,10,17,0),
            LocalDateTime.of(2023,1,10,20,0)
        );

        long studySeatId = 1L;
        CommandReserveSeatResponse response = new CommandReserveSeatResponse(
            "success", studySeatId
        );

        given(studySeatService.reserveSeat(any(), anyLong())).willReturn(response);

        mockMvc.perform(
                post("/seats/{studySeatId}", studySeatId)
                    .content(objectMapper.writeValueAsString(command))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                content().string(objectMapper.writeValueAsString(response))
            )

            .andDo(document("schedules/schedule-reserve",
                Preprocessors.preprocessRequest(prettyPrint()),
                Preprocessors.preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("studySeatId").description("studySeatId")
                ),
                requestFields(
                    fieldWithPath("customerId").type(JsonFieldType.NUMBER).description("customerId"),
                    fieldWithPath("startedTime").type(LocalDateTime.class).description("startedTime"),
                    fieldWithPath("endTime").type(LocalDateTime.class).description("endTime")
                ),
                responseFields(
                    fieldWithPath("message").type(JsonFieldType.STRING).description("message"),
                    fieldWithPath("reservedSeatId").type(JsonFieldType.NUMBER).description("reservedSeatId")
                )
        ));
    }

    @Test
    @DisplayName("예약된 스케줄을 삭제한다.")
    void extractSchedule() throws Exception {
        long studySeatId = 1L;
        long scheduleId = 1L;

        CommandExtractScheduleResponse response = new CommandExtractScheduleResponse(
            "success",
            studySeatId,
            scheduleId
        );
        given(studySeatService.extractSchedule(anyLong(), anyLong())).willReturn(response);

        mockMvc.perform(
                delete("/seats/{studySeatId}/schedules/{scheduleId}", studySeatId, scheduleId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent())

            .andDo(document("schedules/schedule-extract",
                Preprocessors.preprocessRequest(prettyPrint()),
                Preprocessors.preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("studySeatId").description("studySeatId"),
                    parameterWithName("scheduleId").description("scheduleId")
                )
            ));
    }

    @Test
    @DisplayName("스케줄을 업데이트 한다.")
    void updateSchedule() throws Exception {
        CommandChangeStudyTimeWebRequestV1 command = new CommandChangeStudyTimeWebRequestV1(
            1L,
            LocalDateTime.of(2023,1,10,17,0),
            LocalDateTime.of(2023,1,10,20,0)
        );

        long studySeatId = 1L;
        long scheduleId = 1L;

        CommandChangeStudyTimeResponse response = new CommandChangeStudyTimeResponse(
            "success",
            LocalDateTime.of(2023,1,10,17,0),
            LocalDateTime.of(2023,1,10,20,0)
        );

        given(studySeatService.changeStudyTime(any(), anyLong(), anyLong())).willReturn(response);

        mockMvc.perform(
                put("/seats/{studySeatId}/schedules/{scheduleId}", studySeatId, scheduleId)
                    .content(objectMapper.writeValueAsString(command))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                content().string(objectMapper.writeValueAsString(response))
            )

            .andDo(document("schedules/schedule-update",
                Preprocessors.preprocessRequest(prettyPrint()),
                Preprocessors.preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("studySeatId").description("studySeatId"),
                    parameterWithName("scheduleId").description("scheduleId")
                ),
                requestFields(
                    fieldWithPath("customerId").type(JsonFieldType.NUMBER).description("customerId"),
                    fieldWithPath("changingStartedTime").type(LocalDateTime.class).description("changingStartedTime"),
                    fieldWithPath("changingEndTime").type(LocalDateTime.class).description("changingEndTime")
                ),
                responseFields(
                    fieldWithPath("message").type(JsonFieldType.STRING).description("message"),
                    fieldWithPath("changedStartedTime").type(LocalDateTime.class).description("changedStartedTime"),
                    fieldWithPath("changedEndTime").type(LocalDateTime.class).description("changedEndTime")
                )
            ));
    }

    @Test
    @DisplayName("좌석을 퇴실한다.")
    void checkoutSchedule() throws Exception {
        CommandCheckoutScheduleWebRequestV1 command = new CommandCheckoutScheduleWebRequestV1(
            "check-out"
        );

        long studySeatId = 1L;
        long scheduleId = 1L;

        CommandCheckOutScheduleResponse response = new CommandCheckOutScheduleResponse(
            "success", scheduleId);

        given(studySeatService.checkoutSchedule(any(), anyLong(), anyLong())).willReturn(response);

        mockMvc.perform(
                patch("/seats/{studySeatId}/schedules/{scheduleId}", studySeatId, scheduleId)
                    .content(objectMapper.writeValueAsString(command))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                content().string(objectMapper.writeValueAsString(response))
            )

            .andDo(document("schedules/schedule-checkout",
                Preprocessors.preprocessRequest(prettyPrint()),
                Preprocessors.preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("studySeatId").description("studySeatId"),
                    parameterWithName("scheduleId").description("scheduleId")
                ),
                requestFields(
                    fieldWithPath("scheduleState").type(JsonFieldType.STRING).description("scheduleState")
                ),
                responseFields(
                    fieldWithPath("message").type(JsonFieldType.STRING).description("message"),
                    fieldWithPath("checkedOutScheduleId").type(JsonFieldType.NUMBER).description("changedStartedTime")
                )
            ));
    }
}