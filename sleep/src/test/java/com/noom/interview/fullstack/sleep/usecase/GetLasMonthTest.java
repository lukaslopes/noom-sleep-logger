package com.noom.interview.fullstack.sleep.usecase;

import com.noom.interview.fullstack.sleep.db.*;
import com.noom.interview.fullstack.sleep.entity.*;
import com.noom.interview.fullstack.sleep.fixtures.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ActiveProfiles("unittest")
@SpringBootTest
public class GetLasMonthTest {
    @InjectMocks
    private GetLastMonth getLastMonth;

    @Mock
    private DailySleepLogProvider provider;

    @Test
    public void getLastMonthTest() {
        List<DailySleepLog> fixtureList = DailySleepLogFixture.list();
        LocalDateTime startDateTime = LocalDate.now().minusMonths(1).atStartOfDay();

        when(provider.findByUserIdAndInterval(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(fixtureList);
        AvgSleepLog result = getLastMonth.getLastMonth(1L);
        assertNotNull(result);

        DailySleepLog log1 = fixtureList.get(0);
        DailySleepLog log2 = fixtureList.get(1);
        DailySleepLog log3 = fixtureList.get(2);

        //Test average
        int avgDuration = (log1.getSleepDuration().intValue() + log2.getSleepDuration().intValue() + log3.getSleepDuration().intValue()) / 3;
        assertEquals(result.getSleepDuration(), avgDuration);

        int avgStart = (log1.getSleepStart().toLocalTime().toSecondOfDay() + log2.getSleepStart().toLocalTime().toSecondOfDay() + log3.getSleepStart().toLocalTime().toSecondOfDay()) / 3;
        assertEquals(result.getSleepStart(), LocalTime.ofSecondOfDay(avgStart));

        int avgEnd = (log1.getSleepEnd().toLocalTime().toSecondOfDay() + log2.getSleepEnd().toLocalTime().toSecondOfDay() + log3.getSleepEnd().toLocalTime().toSecondOfDay()) / 3;
        assertEquals(result.getSleepEnd(), LocalTime.ofSecondOfDay(avgEnd));

        assertEquals(2, result.getSleepQualityCount().get(SleepQuality.GOOD));
        assertEquals(1, result.getSleepQualityCount().get(SleepQuality.BAD));

        // Test arguments
        ArgumentCaptor<LocalDateTime> start = ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<LocalDateTime> end = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(provider, times(1)).findByUserIdAndInterval(anyLong(), start.capture(), end.capture());
        assertEquals(startDateTime, start.getValue());
        assertTrue(end.getValue().isAfter(LocalDateTime.now().minusMinutes(10)));
    }

    @Test
    public void getAvgWhenNull() {
        when(provider.findByUserIdAndInterval(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(List.of());
        AvgSleepLog result = getLastMonth.getLastMonth(1L);
        assertNull(result);
    }
}
