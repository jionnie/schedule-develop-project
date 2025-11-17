package hello.scheduledevelop.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 서버 내에서 로그인 상태를 유지하고 권한을 확인하기 위한 DTO
 *
 * @author jiwon jung
 */
@Getter
@RequiredArgsConstructor
public class SessionMember {

    private final Long id;
    private final String name;
    private final String email;
}