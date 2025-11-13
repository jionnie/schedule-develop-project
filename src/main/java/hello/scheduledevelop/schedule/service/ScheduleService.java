package hello.scheduledevelop.schedule.service;

import hello.scheduledevelop.member.entity.Member;
import hello.scheduledevelop.member.service.MemberService;
import hello.scheduledevelop.schedule.dto.*;
import hello.scheduledevelop.schedule.entity.Schedule;
import hello.scheduledevelop.schedule.repository.ScheduleRepository;
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

        return new CreateScheduleResponse(
                schedule.getId(),
                schedule.getMember().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    /**
     * 일정 단 건을 조회한다.
     *
     * @param scheduleId 일정 id
     * @return 일정 조회 응답 DTO
     */
    @Transactional(readOnly = true)
    public SearchScheduleResponse findScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );

        return new SearchScheduleResponse(
                schedule.getId(),
                schedule.getMember().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
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

        List<SearchScheduleResponse> dtos = schedules.stream()
                .map(schedule -> new SearchScheduleResponse(
                        schedule.getId(),
                        schedule.getMember().getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getStartDate(),
                        schedule.getEndDate(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()))
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
    public UpdateScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );

        schedule.updateSchedule(
                request.getTitle(),
                request.getContent(),
                request.getStartDate(),
                request.getEndDate()
        );

        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getMember().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}
