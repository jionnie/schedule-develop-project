package hello.scheduledevelop.member.service;

import hello.scheduledevelop.common.exception.UnauthorizedException;
import hello.scheduledevelop.member.dto.*;
import hello.scheduledevelop.member.entity.Member;
import hello.scheduledevelop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 유저에 대한 핵심 비즈니스 로직을 담당하는 MemberService
 * 
 * @author jiwon jung
 */
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }

        // 이미 가입된 이름이 있을 시 예외 발생
        if (memberRepository.existsByName(request.getName())) {
            throw new IllegalStateException("이미 가입된 이름입니다");
        }

        Member member = new Member(
                request.getName(),
                request.getEmail(),
                request.getPassword()
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
     * 로그인을 한다.
     *
     * @param request 로그인 요청 DTO
     */
    @Transactional(readOnly = true)
    public SessionMember login(LoginRequest request) {
        // 가입되지 않은 이메일이면 예외 발생
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );
        
        if (!request.getPassword().equals(member.getPassword())) {
            throw new UnauthorizedException("비밀번호가 틀립니다.");
        }

        return new SessionMember(
                member.getId(),
                member.getName(),
                member.getEmail());
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
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
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
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
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
     * 전체 사용자를 조회한다.
     * 
     * @return 회원 조회 응답 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<SearchMemberResponse> findMembers() {
        List<Member> members = memberRepository.findAll();

        List<SearchMemberResponse> dtos = members.stream()
                .map(member -> new SearchMemberResponse(
                        member.getId(),
                        member.getName(),
                        member.getEmail(),
                        member.getCreatedAt(),
                        member.getModifiedAt()
                ))
                .toList();

        return dtos;
    }

    /**
     * 회원 정보를 수정한다.
     *
     * @param sessionMemberId 세션 멤버 id
     * @param request 회원 수정 요청 DTO
     * @return 회원 수정 응답 DTO
     */
    @Transactional
    public UpdateMemberResponse updateMember(Long sessionMemberId, UpdateMemberRequest request) {
        Member member = memberRepository.findById(sessionMemberId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );

        // 현재 요청 중인 세션 id와 수정을 요청하는 유저의 id가 다르면 예외 발생
        if (!sessionMemberId.equals(member.getId())) {
            throw new IllegalStateException("접근할 수 없습니다.");
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
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );
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
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );

        return member;
    }
}
