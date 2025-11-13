package hello.scheduledevelop.member.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

/**
 * 유저 생성 요청 DTO
 *
 * @author jiwon jung
 */
@Getter
public class CreateMemberRequest {

    @NotBlank(message = "사용자 이름은 반드시 입력해야 합니다.")
    @Size(min = 2, max = 10, message = "사용자 이름은 2자 이상 10자 이하여야 합니다.")
    private String name;

    @NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
    @Size(min = 8, max = 24, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])(?=.*\\d).{8,24}$")
    private String password;

    @NotBlank(message = "이메일은 반드시 입력해야 합니다.")
    @Size(min = 8, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;
}
