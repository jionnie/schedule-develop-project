package hello.scheduledevelop.domain.member.dto;

import hello.scheduledevelop.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * 유저 조회 응답 DTO
 *
 * @author jiwon jung
 */
@Getter
@RequiredArgsConstructor
public class SearchMemberResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static SearchMemberResponse from(Member member) {
        return new SearchMemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }
}
