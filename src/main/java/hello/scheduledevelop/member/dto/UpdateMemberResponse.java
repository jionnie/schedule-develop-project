package hello.scheduledevelop.member.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 유저 수정 응답 DTO
 *
 * @author jiwon jung
 */
@Getter
public class UpdateMemberResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UpdateMemberResponse(Long id, String name, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
