package hello.scheduledevelop.schedule.controller;

import hello.scheduledevelop.member.dto.SessionMember;
import hello.scheduledevelop.schedule.dto.*;
import hello.scheduledevelop.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            HttpSession session,
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember,
            @Valid @RequestBody CreateScheduleRequest request) {

        // 세션에 loginMember로 찾은 SessionMember가 없으면 로그인된 사용자 X
        if (sessionMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(sessionMember.getId(), request));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<SearchScheduleResponse> getScheduleById(
            HttpSession session,
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember,
            @PathVariable Long scheduleId) {

        // 세션에 loginMember로 찾은 SessionMember가 없으면 로그인된 사용자 X
        if (sessionMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findScheduleById(scheduleId, sessionMember.getId()));
    }

    @GetMapping
    public ResponseEntity<List<SearchScheduleResponse>> getSchedules(
            HttpSession session,
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember) {

        // 세션에 loginMember로 찾은 SessionMember가 없으면 로그인된 사용자 X
        if (sessionMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findSchedules(sessionMember.getId()));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            HttpSession session,
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember,
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request) {

        // 세션에 loginMember로 찾은 SessionMember가 없으면 로그인된 사용자 X
        if (sessionMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(scheduleId, sessionMember.getId(), request));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            HttpSession session,
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember,
            @PathVariable Long scheduleId) {

        // 세션에 loginMember로 찾은 SessionMember가 없으면 로그인된 사용자 X
        if (sessionMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        scheduleService.deleteSchedule(scheduleId, sessionMember.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
