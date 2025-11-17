package hello.scheduledevelop.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 조회 요청 DTO
 *
 * @author jiwon jung
 */
@Getter
@Setter
public class SearchMemberRequest {

    @NotBlank(message = "사용자 이름은 반드시 입력해야 합니다.")
    @Size(min = 2, max = 10, message = "사용자 이름은 2자 이상 10자 이하여야 합니다.")
    private String name;
}
