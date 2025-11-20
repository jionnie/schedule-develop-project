package hello.scheduledevelop.domain.schedule.service;

import hello.scheduledevelop.domain.schedule.dto.*;
import hello.scheduledevelop.global.common.exception.ErrorCode;
import hello.scheduledevelop.domain.member.entity.Member;
import hello.scheduledevelop.domain.member.service.MemberService;
import hello.scheduledevelop.domain.schedule.entity.Schedule;
import hello.scheduledevelop.domain.schedule.repository.ScheduleRepository;
import hello.scheduledevelop.global.common.exception.ScheduleNotFoundException;
import hello.scheduledevelop.global.common.exception.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 일정에 대한 핵심 비즈니스 로직을 담당하는 ScheduleService
 *
 * @author jiwon jung
 */
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberService memberService; // MemberRepository에 직접 의존하는 것을 지양

    /**
     * 일정을 생성한다.
     *
     * @param memberId 유저 id
     * @param request 일정 생성 요청 DTO
     * @return 일정 생성 응답 DTO
     */
    @Transactional
    public CreateScheduleResponse createSchedule(Long memberId, CreateScheduleRequest request) {
        Member member = memberService.getMember(memberId);

        Schedule schedule = scheduleRepository.save(new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getStartDate(),
                request.getEndDate(),
                member
        ));

        return CreateScheduleResponse.from(schedule);
    }

    /**
     * 일정 단 건을 조회한다.
     *
     * @param scheduleId 일정 id
     * @return 일정 조회 응답 DTO
     */
    @Transactional(readOnly = true)
    public SearchScheduleResponse findScheduleById(Long scheduleId, Long memberId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException(ErrorCode.NOT_FOUND_SCHEDULE)
        );

        // 현재 요청 중인 세션 id와 수정을 요청하는 유저의 id가 다르면 예외 발생
        if (!memberId.equals(schedule.getMember().getId())) {
            throw new UnauthorizedAccessException(ErrorCode.ACCESS_DENIED);
        }

        return SearchScheduleResponse.from(schedule);
    }

    /**
     * 멤버별 전체 일정을 조회한다.
     *
     * @param memberId 유저 id
     * @return 일정 조회 응답 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<SearchScheduleResponse> findSchedules(Long memberId) {
        List<Schedule> schedules = scheduleRepository.findByMemberId(memberId);

        if (schedules.isEmpty()) {
            throw new ScheduleNotFoundException(ErrorCode.NOT_FOUND_SCHEDULE);
        }

        // 현재 요청 중인 세션 id와 수정을 요청하는 유저의 id가 다르면 예외 발생
        if (!memberId.equals(schedules.get(0).getMember().getId())) {
            throw new UnauthorizedAccessException(ErrorCode.ACCESS_DENIED);
        }

        List<SearchScheduleResponse> dtos = schedules.stream()
                .map(SearchScheduleResponse::from)
                .toList();

        return dtos;
    }

    /**
     * 일정을 수정한다.
     *
     * @param scheduleId 유저 id
     * @return 일정 수정 응답 DTO
     */
    @Transactional
    public UpdateScheduleResponse updateSchedule(Long scheduleId, Long memberId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException(ErrorCode.NOT_FOUND_SCHEDULE)
        );

        // 현재 요청 중인 세션 id와 수정을 요청하는 유저의 id가 다르면 예외 발생
        if (!memberId.equals(schedule.getMember().getId())) {
            throw new UnauthorizedAccessException(ErrorCode.ACCESS_DENIED);
        }

        schedule.updateSchedule(
                request.getTitle(),
                request.getContent(),
                request.getStartDate(),
                request.getEndDate()
        );

        scheduleRepository.flush(); // 변경 시간 바로 반영

        return UpdateScheduleResponse.from(schedule);
    }

    /**
     * 일정을 삭제한다.
     *
     * @param scheduleId 일정 id
     */
    @Transactional
    public void deleteSchedule(Long scheduleId, Long memberId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException(ErrorCode.NOT_FOUND_SCHEDULE)
        );

        // 현재 요청 중인 세션 id와 수정을 요청하는 유저의 id가 다르면 예외 발생
        if (!memberId.equals(schedule.getMember().getId())) {
            throw new UnauthorizedAccessException(ErrorCode.ACCESS_DENIED);
        }

        scheduleRepository.deleteById(scheduleId);
    }
}
