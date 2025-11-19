package hello.scheduledevelop.domain.member.service;

import hello.scheduledevelop.domain.member.dto.*;
import hello.scheduledevelop.global.common.exception.ErrorCode;
import hello.scheduledevelop.global.common.exception.MemberNotFoundException;
import hello.scheduledevelop.global.common.exception.MemberPresentException;
import hello.scheduledevelop.domain.member.entity.Member;
import hello.scheduledevelop.domain.member.repository.MemberRepository;
import hello.scheduledevelop.global.common.exception.UnauthorizedAccessException;
import hello.scheduledevelop.global.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 유저에 대한 핵심 비즈니스 로직을 담당하는 MemberService
 * 
 * @author jiwon jung
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화는 서비스 계층에서 처리하는 것이 좋음

    /**
     * 새로운 회원을 생성한다.
     *
     * @param request 회원 생성 요청 DTO
     * @return 회원 생성 응답 DTO
     */
    @Transactional
    public SignupResponse signup(SignupRequest request) {

        // 이미 가입된 이메일이 있을 시 예외 발생
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new MemberPresentException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 이미 가입된 이름이 있을 시 예외 발생
        if (memberRepository.existsByName(request.getName())) {
            throw new MemberPresentException(ErrorCode.DUPLICATE_NAME);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Member member = new Member(
                request.getName(),
                request.getEmail(),
                encodedPassword
        );

        memberRepository.save(member);

        return new SignupResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }

    /**
     * id로 회원을 조회한다.
     *
     * @param memberId 회원 id
     * @return 회원 조회 응답 DTO
     */
    @Transactional(readOnly = true)
    public SearchMemberResponse findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.NOT_FOUND_MEMBER)
        );

        return new SearchMemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }

    /**
     * 사용자 이름으로 회원을 조회한다.
     *
     * @param request 회원 조회 요청 DTO
     * @return 회원 조회 응답 DTO
     */
    @Transactional(readOnly = true)
    public SearchMemberResponse findMemberByName(SearchMemberRequest request) {
        Member member = memberRepository.findByName(request.getName()).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.NOT_FOUND_MEMBER)
        );

        return new SearchMemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }

    /**
     * 회원 정보를 수정한다.
     *
     * @param memberId 세션 멤버 id
     * @param request 회원 수정 요청 DTO
     * @return 회원 수정 응답 DTO
     */
    @Transactional
    public UpdateMemberResponse updateMember(Long memberId, UpdateMemberRequest request) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.NOT_FOUND_MEMBER)
        );

        // 현재 요청 중인 세션 id와 수정을 요청하는 유저의 id가 다르면 예외 발생
        if (!memberId.equals(member.getId())) {
            throw new UnauthorizedAccessException(ErrorCode.ACCESS_DENIED);
        }

        member.updateName(request.getName());

        return new UpdateMemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }

    /**
     * 사용자를 삭제한다.
     *
     * @param memberId 유저 id
     */
    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.NOT_FOUND_MEMBER)
        );

        // 현재 요청 중인 세션 id와 수정을 요청하는 유저의 id가 다르면 예외 발생
        if (!memberId.equals(member.getId())) {
            throw new UnauthorizedAccessException(ErrorCode.ACCESS_DENIED);
        }

        memberRepository.delete(member);
    }

    /**
     * 사용자를 DTO가 아닌 Member 자체로 얻어온다.
     *
     * @param memberId 유저 id
     * @return Member 객체
     */
    @Transactional
    public Member getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.NOT_FOUND_MEMBER)
        );

        return member;
    }
}
