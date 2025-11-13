package hello.scheduledevelop.member.service;

import hello.scheduledevelop.member.dto.*;
import hello.scheduledevelop.member.entity.Member;
import hello.scheduledevelop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CreateMemberResponse signup(CreateMemberRequest request) {
        Member member = new Member(
                request.getName(),
                request.getEmail()
        );

        memberRepository.save(member);

        return new CreateMemberResponse(
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
     * @param memberId 회원 id
     * @param request 회원 수정 요청 DTO
     * @return 회원 수정 응답 DTO
     */
    @Transactional
    public UpdateMemberResponse updateMember(Long memberId, UpdateMemberRequest request) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );

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
