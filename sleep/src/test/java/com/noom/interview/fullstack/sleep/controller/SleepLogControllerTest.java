package com.noom.interview.fullstack.sleep.controller;

import com.fasterxml.jackson.databind.*;
import com.noom.interview.fullstack.sleep.controller.dto.*;
import com.noom.interview.fullstack.sleep.domain.*;
import com.noom.interview.fullstack.sleep.fixtures.*;
import com.noom.interview.fullstack.sleep.usecase.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.test.context.*;
import org.springframework.test.web.servlet.*;

import java.time.format.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SleepLogController.class)
@ActiveProfiles("unittest")
public class SleepLogControllerTest {

    private static final String API_URL = "/v1/sleeplog";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SaveSleepLog saveSleepLog;
    @MockBean
    private GetLastMonth getLastMonth;
    @MockBean
    private GetLastSleep getLastSleep;

    @Test
    public void saveSleepLogSuccess() throws Exception {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DailySleepLog fixture = DailySleepLogFixture.defaultValuesNoId();
        DailySleepLog resultFixture =  DailySleepLogFixture.defaultValues();

        AddSleepLogRequest request = AddSleepLogRequest.builder()
            .sleepDate(fixture.getSleepEnd().toLocalDate())
            .sleepStart(fixture.getSleepStart().toLocalTime())
            .sleepEnd(fixture.getSleepEnd().toLocalTime())
            .sleepQuality(fixture.getSleepQuality())
            .build();

        when(saveSleepLog.save(ArgumentMatchers.any())).thenReturn(resultFixture);

        mockMvc.perform(post(API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .header("userId", fixture.getUserId().toString())
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(resultFixture.getId().toString()))
            .andExpect(jsonPath("$.userId").value(resultFixture.getUserId().toString()))
            .andExpect(jsonPath("$.sleepDate").value(dateFormatter.format(resultFixture.getSleepEnd().toLocalDate())))
            .andExpect(jsonPath("$.sleepStart").value(timeFormatter.format(resultFixture.getSleepStart().toLocalTime())))
            .andExpect(jsonPath("$.sleepEnd").value(timeFormatter.format(resultFixture.getSleepEnd().toLocalTime())))
            .andExpect(jsonPath("$.timeInBedInMinutes").value(resultFixture.getSleepDuration().intValue()))
            .andExpect(jsonPath("$.sleepQuality").value(resultFixture.getSleepQuality().toString()));


        // Test conversion
        ArgumentCaptor<DailySleepLog> sleepLog = ArgumentCaptor.forClass(DailySleepLog.class);
        verify(saveSleepLog).save(sleepLog.capture());
        assertNull(sleepLog.getValue().getId());
        assertEquals(fixture.getSleepEnd(), sleepLog.getValue().getSleepEnd());
        assertEquals(fixture.getSleepStart(), sleepLog.getValue().getSleepStart());
        assertEquals(fixture.getSleepQuality(), sleepLog.getValue().getSleepQuality());
        assertEquals(fixture.getUserId(), sleepLog.getValue().getUserId());

    }

    @Test
    public void lastSleepSuccessTest() throws Exception {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        DailySleepLog resultFixture =  DailySleepLogFixture.defaultValues();
        when(getLastSleep.execute(ArgumentMatchers.any())).thenReturn(resultFixture);

        mockMvc.perform(get(API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .header("userId", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(resultFixture.getId().toString()))
            .andExpect(jsonPath("$.userId").value(resultFixture.getUserId().toString()))
            .andExpect(jsonPath("$.sleepDate").value(dateFormatter.format(resultFixture.getSleepEnd().toLocalDate())))
            .andExpect(jsonPath("$.sleepStart").value(timeFormatter.format(resultFixture.getSleepStart().toLocalTime())))
            .andExpect(jsonPath("$.sleepEnd").value(timeFormatter.format(resultFixture.getSleepEnd().toLocalTime())))
            .andExpect(jsonPath("$.timeInBedInMinutes").value(resultFixture.getSleepDuration().intValue()))
            .andExpect(jsonPath("$.sleepQuality").value(resultFixture.getSleepQuality().toString()));

    }

    @Test
    public void lastMonthSuccessTest() throws Exception {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        AvgSleepLog resultFixture =  AvgSleepLogFixture.defaultValues();
        when(getLastMonth.execute(ArgumentMatchers.any())).thenReturn(resultFixture);

        mockMvc.perform(get(API_URL + "/month")
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userId").value(resultFixture.getUserId().toString()))
            .andExpect(jsonPath("$.sleepDateStart").value(dateFormatter.format(resultFixture.getStartDate())))
            .andExpect(jsonPath("$.sleepDateEnd").value(dateFormatter.format(resultFixture.getEndDate())))
            .andExpect(jsonPath("$.sleepStart").value(timeFormatter.format(resultFixture.getSleepStart())))
            .andExpect(jsonPath("$.sleepEnd").value(timeFormatter.format(resultFixture.getSleepEnd())))
            .andExpect(jsonPath("$.timeInBedInMinutes").value(resultFixture.getSleepDuration().toString()))
            .andExpect(jsonPath("$.sleepQualityCount.BAD").value(resultFixture.getSleepQualityCount().get(SleepQuality.BAD)))
            .andExpect(jsonPath("$.sleepQualityCount.OK").value(resultFixture.getSleepQualityCount().get(SleepQuality.OK)))
            .andExpect(jsonPath("$.sleepQualityCount.GOOD").value(resultFixture.getSleepQualityCount().get(SleepQuality.GOOD)));
    }

}
