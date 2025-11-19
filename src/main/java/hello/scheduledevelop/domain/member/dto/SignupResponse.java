package hello.scheduledevelop.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * 유저 생성 응답 DTO
 *
 * @author jiwon jung
 */
@Getter
@RequiredArgsConstructor
public class SignupResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
