package hello.scheduledevelop.domain.member.dto;

import hello.scheduledevelop.domain.member.entity.Member;
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

    // 정적 팩토리 메소드
    public static SignupResponse from(Member member) {
        return new SignupResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }
}
