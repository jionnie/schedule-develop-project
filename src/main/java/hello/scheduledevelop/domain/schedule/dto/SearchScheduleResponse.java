package hello.scheduledevelop.domain.schedule.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 일정 조회 응답 DTO
 *
 * @author jiwon jung
 */
@Getter
public class SearchScheduleResponse {

    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public SearchScheduleResponse(Long id, Long memberId, String title, String content, LocalDate startDate, LocalDate endDate, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
