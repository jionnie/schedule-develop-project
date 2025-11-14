package hello.scheduledevelop.member.controller;

import hello.scheduledevelop.member.dto.*;
import hello.scheduledevelop.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 유저 정보를 CRUD 하는 REST API 엔드포인트를 제공하는 컨트롤러
 *
 * @author jiwon jung
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> createMember(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(request));
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<SearchMemberResponse> getMemberById(@PathVariable Long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findMemberById(memberId));
    }

    @GetMapping(params = "name")
    public ResponseEntity<SearchMemberResponse> getMemberByName(@Valid @ModelAttribute SearchMemberRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findMemberByName(request));
    }

    @PatchMapping("/members")
    public ResponseEntity<UpdateMemberResponse> updateMember(
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember,
            HttpSession session,
            @Valid @RequestBody UpdateMemberRequest request) {

        SessionMember loggedInMember = (SessionMember) session.getAttribute("loginMember");

        if (loggedInMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMember(sessionMember.getId(), request));
    }

    @DeleteMapping("/members")
    public ResponseEntity<Void> deleteMember(
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember,
            HttpSession session) {

        SessionMember loggedInMember = (SessionMember) session.getAttribute("loginMember");

        if (loggedInMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        memberService.deleteMember(sessionMember.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
