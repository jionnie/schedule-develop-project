package hello.scheduledevelop.domain.schedule.dto;

import hello.scheduledevelop.domain.schedule.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 일정 조회 응답 DTO
 *
 * @author jiwon jung
 */
@Getter
@RequiredArgsConstructor
public class SearchScheduleResponse {

    private final Long id;
    private final Long memberId;
    private final String title;
    private final String content;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static SearchScheduleResponse from(Schedule schedule) {
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
}
