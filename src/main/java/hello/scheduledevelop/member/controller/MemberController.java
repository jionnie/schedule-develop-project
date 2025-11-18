package hello.scheduledevelop.member.controller;

import hello.scheduledevelop.common.exception.ErrorCode;
import hello.scheduledevelop.common.exception.UnauthorizedException;
import hello.scheduledevelop.member.dto.*;
import hello.scheduledevelop.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 유저 정보를 CRUD 하는 REST API 엔드포인트를 제공하는 컨트롤러
 *
 * @author jiwon jung
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public ResponseEntity<SearchMemberResponse> getMemberById(@PathVariable Long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findMemberById(memberId));
    }

    @GetMapping
    public ResponseEntity<SearchMemberResponse> getMemberByName(@Valid @ModelAttribute SearchMemberRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(memberService.findMemberByName(request));
    }

    @PatchMapping
    public ResponseEntity<UpdateMemberResponse> updateMember(
            HttpSession session,
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember,
            @Valid @RequestBody UpdateMemberRequest request) {

        // 세션에 loginMember로 찾은 SessionMember가 없으면 로그인된 사용자 X
        if (sessionMember == null) {
            throw new UnauthorizedException(ErrorCode.UNAUTHENTICATE_MEMBER);
        }

        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMember(sessionMember.getId(), request));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(
            HttpSession session,
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember) {

        // 세션에 loginMember로 찾은 SessionMember가 없으면 로그인된 사용자 X
        if (sessionMember == null) {
            throw new UnauthorizedException(ErrorCode.UNAUTHENTICATE_MEMBER);
        }

        memberService.deleteMember(sessionMember.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
