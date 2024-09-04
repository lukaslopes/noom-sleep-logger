package com.noom.interview.fullstack.sleep.controller;

import com.noom.interview.fullstack.sleep.controller.dto.*;
import com.noom.interview.fullstack.sleep.entity.*;
import com.noom.interview.fullstack.sleep.usecase.*;
import lombok.*;
import org.jetbrains.annotations.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/sleeplog")
public class SleepLogController {

    private final SaveSleepLog saveSleepLog;
    private final GetLastMonth getLastMonth;
    private final GetLastSleep getLastSleep;



    @PostMapping
    public ResponseEntity<SleepLogResponse> save(@RequestHeader Long userId, @RequestBody AddSleepLogRequest request) {

        DailySleepLog result = saveSleepLog.save(toEntity(userId, request));

        return new ResponseEntity<>(toLogResponseDTO(result), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<SleepLogResponse> lastSleep(@RequestHeader Long userId) {

        DailySleepLog result = getLastSleep.execute(userId);

        return new ResponseEntity<>(toLogResponseDTO(result), HttpStatus.OK);
    }

    @GetMapping(path = "/month")
    public ResponseEntity<AvgSleepLogResponse> lastMonth(@RequestHeader Long userId) {

        AvgSleepLog result = getLastMonth.execute(userId);

        return new ResponseEntity<>(toAvgLogResponseDTO(result), HttpStatus.OK);
    }

    private DailySleepLog toEntity(Long userId, AddSleepLogRequest request) {

        LocalDateTime start = getStartFromDayBefore(request);
        LocalDateTime end = LocalDateTime.of(request.getSleepDate(), request.getSleepEnd());

        return DailySleepLog.builder()
            .userId(userId)
            .sleepStart(start)
            .sleepEnd(end)
            .sleepDuration(request.getTimeInBedInMinutes().longValue())
            .sleepQuality(request.getSleepQuality())
            .build();
    }

    @NotNull
    private static LocalDateTime getStartFromDayBefore(AddSleepLogRequest request) {
        return request.getSleepStart().isAfter(request.getSleepEnd())
            ? LocalDateTime.of(request.getSleepDate().minusDays(1), request.getSleepStart())
            : LocalDateTime.of(request.getSleepDate(), request.getSleepStart());
    }

    private SleepLogResponse toLogResponseDTO(DailySleepLog result) {
        return SleepLogResponse
            .builder()
            .id(result.getId())
            .userId(result.getUserId())
            .sleepDate(result.getSleepEnd().toLocalDate())
            .sleepStart(result.getSleepStart().toLocalTime())
            .sleepEnd(result.getSleepEnd().toLocalTime())
            .timeInBedInMinutes(result.getSleepDuration().intValue())
            .sleepQuality(result.getSleepQuality())
            .build();
    }

    private AvgSleepLogResponse toAvgLogResponseDTO(AvgSleepLog result) {
        return AvgSleepLogResponse
            .builder()
            .userId(result.getUserId())
            .sleepDateStart(result.getStartDate())
            .sleepDateEnd(result.getEndDate())
            .sleepStart(result.getSleepStart())
            .sleepEnd(result.getSleepEnd())
            .timeInBedInMinutes(result.getSleepDuration())
            .sleepQualityCount(result.getSleepQualityCount())
            .build();
    }
}
