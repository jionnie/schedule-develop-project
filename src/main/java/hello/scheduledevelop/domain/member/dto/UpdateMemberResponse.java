package hello.scheduledevelop.domain.member.dto;

import hello.scheduledevelop.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * 유저 수정 응답 DTO
 *
 * @author jiwon jung
 */
@Getter
@RequiredArgsConstructor
public class UpdateMemberResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static UpdateMemberResponse from(Member member) {
        return new UpdateMemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }
}
