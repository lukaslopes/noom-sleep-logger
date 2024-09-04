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
import static org.mockito.Mockito.*;

@ActiveProfiles("unittest")
@SpringBootTest
public class GetLastSleepTest {

    @InjectMocks
    private GetLastSleep getLastSleep;

    @Mock
    private DailySleepLogProvider provider;

    @Test
    public void getLastSleepTest() {

        DailySleepLog expected = DailySleepLogFixture.defaultValues();
        when(provider.findByUserIdAndInterval(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(List.of(expected));
        DailySleepLog actual = getLastSleep.execute(1L);
        assertEquals(expected, actual);

        ArgumentCaptor<LocalDateTime> start = ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<LocalDateTime> end = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(provider, times(1)).findByUserIdAndInterval(anyLong(), start.capture(), end.capture());
        assertEquals(LocalDate.now().atStartOfDay(), start.getValue());
        assertTrue(end.getValue().isAfter(LocalDateTime.now().minusMinutes(10)));
    }

    @Test
    public void getLastSleepNotFoundTest() {
        when(provider.findByUserIdAndInterval(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(List.of());
        DailySleepLog actual = getLastSleep.execute(1L);
        assertNull(actual);

    }
}
