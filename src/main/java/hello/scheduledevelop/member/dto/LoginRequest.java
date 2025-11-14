package hello.scheduledevelop.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 로그인 요청 DTO
 *
 * @author jiwon jung
 */
@Getter
public class LoginRequest {

    @NotBlank(message = "이메일은 반드시 입력해야 합니다.")
    @Size(min = 8, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
    private String password;
}
