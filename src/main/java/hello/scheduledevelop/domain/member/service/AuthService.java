package hello.scheduledevelop.domain.member.service;

import hello.scheduledevelop.domain.member.dto.SessionMember;
import hello.scheduledevelop.global.common.exception.ErrorCode;
import hello.scheduledevelop.global.common.exception.InvalidPasswordException;
import hello.scheduledevelop.global.common.exception.MemberNotFoundException;
import hello.scheduledevelop.domain.member.dto.LoginRequest;
import hello.scheduledevelop.domain.member.entity.Member;
import hello.scheduledevelop.domain.member.repository.MemberRepository;
import hello.scheduledevelop.global.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 인증, 인가에 대한 핵심 비즈니스 로직을 담당하는 AuthService
 *
 * @author jiwon jung
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인을 한다.
     *
     * @param request 로그인 요청 DTO
     */
    @Transactional(readOnly = true)
    public SessionMember login(LoginRequest request) {
        // 가입되지 않은 이메일이면 예외 발생
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.NOT_FOUND_MEMBER)
        );

        // 로그인 시 입력한 비밀번호가 틀릴 시 예외 발생
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) { // 평문 오리지널 패스워드, 암호화 된 패스워드 순서
            throw new InvalidPasswordException(ErrorCode.LOGIN_FAIL);
        }

        return SessionMember.from(member);
    }
}
