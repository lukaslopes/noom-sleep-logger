package com.noom.interview.fullstack.sleep.controller;

import com.noom.interview.fullstack.sleep.controller.dto.*;
import com.noom.interview.fullstack.sleep.entity.*;
import com.noom.interview.fullstack.sleep.usecase.*;
import lombok.*;
import org.jetbrains.annotations.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.time.*;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/sleeplog")
public class SleepLogController {

    private static final Logger log = LoggerFactory.getLogger(SleepLogController.class);
    private final SaveSleepLog saveSleepLog;
    private final GetLastMonth getLastMonth;
    private final GetLastSleep getLastSleep;



    @PostMapping
    public ResponseEntity<SleepLogResponse> save(@RequestHeader Long userId, @RequestBody AddSleepLogRequest request) {

        DailySleepLog result = saveSleepLog.save(toEntityValidate(userId, request));

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

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(HttpServletRequest request, Exception e) {
        log.error(e.getMessage(), e);
        return ErrorDTO.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .message(e.getMessage())
            .build();
    }

    private DailySleepLog toEntityValidate(Long userId, AddSleepLogRequest request) {
        if(Objects.isNull(userId)) {
            throw new IllegalArgumentException("userId is required");
        }
        if(Objects.isNull(request.getSleepQuality())) {
            throw new IllegalArgumentException("sleepQuality is required");
        }
        if(Objects.isNull(request.getSleepStart()) || Objects.isNull(request.getSleepEnd())) {
            throw new IllegalArgumentException("sleepStart and sleepEnd are required");
        }

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
            .timeInBedInMinutes(result.getSleepDuration().intValue())
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
            .timeInBedInMinutes(result.getSleepDuration())
            .sleepQualityCount(result.getSleepQualityCount())
            .build();
    }
}
