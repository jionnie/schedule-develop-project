package hello.scheduledevelop.member.service;

import hello.scheduledevelop.common.exception.DataNotFoundException;
import hello.scheduledevelop.common.exception.ErrorCode;
import hello.scheduledevelop.common.exception.UnauthorizedException;
import hello.scheduledevelop.member.dto.LoginRequest;
import hello.scheduledevelop.member.dto.SessionMember;
import hello.scheduledevelop.member.entity.Member;
import hello.scheduledevelop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 인증, 인가에 대한 핵심 비즈니스 로직을 담당하는 AuthService
 *
 * @author jiwon jung
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    /**
     * 로그인을 한다.
     *
     * @param request 로그인 요청 DTO
     */
    @Transactional(readOnly = true)
    public SessionMember login(LoginRequest request) {
        // 가입되지 않은 이메일이면 예외 발생
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new DataNotFoundException(ErrorCode.NOT_FOUND_MEMBER)
        );

        // 로그인 시 입력한 비밀번호가 틀릴 시 예외 발생
        if (!request.getPassword().equals(member.getPassword())) {
            throw new UnauthorizedException(ErrorCode.LOGIN_FAIL);
        }

        return new SessionMember(
                member.getId(),
                member.getName(),
                member.getEmail());
    }
}
