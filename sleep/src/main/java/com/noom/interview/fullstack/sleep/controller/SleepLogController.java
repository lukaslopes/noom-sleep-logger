package com.noom.interview.fullstack.sleep.controller;

import com.noom.interview.fullstack.sleep.controller.dto.*;
import com.noom.interview.fullstack.sleep.domain.*;
import com.noom.interview.fullstack.sleep.usecase.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/sleeplog")
public class SleepLogController {

    private final SaveSleepLog saveSleepLog;
    private final GetLastMonth getLastMonth;
    private final GetLastSleep getLastSleep;

    @PostMapping
    public ResponseEntity<SleepLogResponse> save(@RequestHeader @NotNull Long userId, @RequestBody @Valid AddSleepLogRequest request) {

        DailySleepLog result = saveSleepLog.save(toEntityValidate(userId, request));

        return new ResponseEntity<>(toLogResponseDTO(result), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<SleepLogResponse> lastSleep(@RequestHeader @NotNull Long userId) {

        DailySleepLog result = getLastSleep.execute(userId);

        return new ResponseEntity<>(toLogResponseDTO(result), HttpStatus.OK);
    }

    @GetMapping(path = "/month")
    public ResponseEntity<AvgSleepLogResponse> lastMonth(@RequestHeader @NotNull Long userId) {

        AvgSleepLog result = getLastMonth.execute(userId);

        return new ResponseEntity<>(toAvgLogResponseDTO(result), HttpStatus.OK);
    }


    private DailySleepLog toEntityValidate(Long userId, AddSleepLogRequest request) {
        LocalDate sleepDate = Objects.isNull(request.getSleepDate())
            ? LocalDate.now()
            : request.getSleepDate();

        LocalDateTime start = getStartFromDayBefore(request, sleepDate);
        LocalDateTime end = LocalDateTime.of(sleepDate, request.getSleepEnd());

        return DailySleepLog.builder()
            .userId(userId)
            .sleepStart(start)
            .sleepEnd(end)
            .sleepQuality(request.getSleepQuality())
            .build();
    }

    @NotNull
    private static LocalDateTime getStartFromDayBefore(AddSleepLogRequest request, LocalDate sleepDate) {
        return request.getSleepStart().isAfter(request.getSleepEnd())
            ? LocalDateTime.of(sleepDate.minusDays(1), request.getSleepStart())
            : LocalDateTime.of(sleepDate, request.getSleepStart());
    }

    private SleepLogResponse toLogResponseDTO(DailySleepLog result) {
        if(Objects.isNull(result)) {
            return null;
        }
        return SleepLogResponse
            .builder()
            .id(result.getId())
            .userId(result.getUserId())
            .sleepDate(result.getSleepEnd().toLocalDate())
            .sleepStart(result.getSleepStart().toLocalTime())
            .sleepEnd(result.getSleepEnd().toLocalTime())
            .sleepTime(calculateSleepTime(result.getSleepStart().toLocalTime(), result.getSleepEnd().toLocalTime()))
            .sleepQuality(result.getSleepQuality())
            .build();
    }

    private AvgSleepLogResponse toAvgLogResponseDTO(AvgSleepLog result) {
        if(Objects.isNull(result)) {
            return null;
        }

        return AvgSleepLogResponse
            .builder()
            .userId(result.getUserId())
            .sleepDateStart(result.getStartDate())
            .sleepDateEnd(result.getEndDate())
            .sleepStart(result.getSleepStart())
            .sleepEnd(result.getSleepEnd())
            .sleepTime(calculateSleepTime(result.getSleepStart(), result.getSleepEnd()))
            .sleepQualityCount(result.getSleepQualityCount())
            .build();
    }

    private LocalTime calculateSleepTime(LocalTime sleepStart, LocalTime sleepEnd) {
        return sleepStart.isAfter(sleepEnd)
            ? LocalTime.ofSecondOfDay( Duration.between(sleepStart, LocalTime.MAX).toSeconds() + sleepEnd.toSecondOfDay()+1 )
            : LocalTime.ofSecondOfDay( sleepEnd.toSecondOfDay()+1 - sleepStart.toSecondOfDay() );
    }
}
