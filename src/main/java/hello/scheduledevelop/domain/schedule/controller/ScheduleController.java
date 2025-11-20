package hello.scheduledevelop.domain.schedule.controller;

import hello.scheduledevelop.domain.schedule.dto.*;
import hello.scheduledevelop.global.common.dto.ApiResponse;
import hello.scheduledevelop.domain.member.dto.SessionMember;
import hello.scheduledevelop.domain.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 일정 정보를 CRUD 하는 REST API 엔드포인트를 제공하는 컨트롤러
 *
 * @author jiwon jung
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateScheduleResponse>> createSchedule(
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember,
            @Valid @RequestBody CreateScheduleRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(scheduleService.createSchedule(sessionMember.getId(), request)));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse<SearchScheduleResponse>> getScheduleById(
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember,
            @PathVariable Long scheduleId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(scheduleService.findScheduleById(scheduleId, sessionMember.getId())));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SearchScheduleResponse>>> getSchedules(
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(scheduleService.findSchedules(sessionMember.getId())));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse<UpdateScheduleResponse>> updateSchedule(
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember,
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(scheduleService.updateSchedule(scheduleId, sessionMember.getId(), request)));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember,
            @PathVariable Long scheduleId) {

        scheduleService.deleteSchedule(scheduleId, sessionMember.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
