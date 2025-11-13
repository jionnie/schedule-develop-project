package hello.scheduledevelop.schedule.controller;

import hello.scheduledevelop.schedule.dto.CreateScheduleRequest;
import hello.scheduledevelop.schedule.dto.CreateScheduleResponse;
import hello.scheduledevelop.schedule.dto.SearchScheduleResponse;
import hello.scheduledevelop.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 일정 정보를 CRUD 하는 REST API 엔드포인트를 제공하는 컨트롤러
 *
 * @author jiwon jung
 */
@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/members/{memberId}")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/api/members/{memberId}/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @PathVariable Long memberId,
            @RequestBody CreateScheduleRequest request
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(memberId, request));
    }

    @GetMapping("/api/members/{memberId}/schedules/{scheduleId}")
    public ResponseEntity<SearchScheduleResponse> getScheduleById(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findScheduleById(scheduleId));
    }
}
