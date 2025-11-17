package hello.scheduledevelop.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 일정 생성 요청 DTO
 *
 * @author jiwon jung
 */
@Getter
public class CreateScheduleRequest {

    @NotBlank(message = "일정 제목은 반드시 입력해야 합니다.")
    @Size(min = 1, max = 15, message = "일정 제목은 1자 이상 15자 이히여야 합니다.")
    private String title;

    @Size(max = 80, message = "일정 내용은 80자를 넘을 수 없습니다.")
    private String content;

    @NotNull(message = "일정 시작일은 반드시 입력해야 합니다.")
    private LocalDate startDate;

    @NotNull(message = "일정 마감일은 반드시 입력해야 합니다.")
    private LocalDate endDate;

}
