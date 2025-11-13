package hello.scheduledevelop.member.service;

import hello.scheduledevelop.member.dto.*;
import hello.scheduledevelop.member.entity.Member;
import hello.scheduledevelop.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 유저에 대한 핵심 비즈니스 로직을 담당하는 MemberService
 * 
 * @author jiwon jung
 */
@Service
@Slf4j
public class MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

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
        log.info("name: " + request.getName());
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
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );

        memberRepository.delete(member);
    }
}
