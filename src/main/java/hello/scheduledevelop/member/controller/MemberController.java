package hello.scheduledevelop.member.controller;

import hello.scheduledevelop.member.dto.*;
import hello.scheduledevelop.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 유저 정보를 CRUD 하는 REST API 엔드포인트를 제공하는 컨트롤러
 *
 * @author jiwon jung
 */
@EnableJpaAuditing
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<CreateMemberResponse> createMember(@Valid @RequestBody CreateMemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(request));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<SearchMemberResponse> getMemberById(@PathVariable Long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findMemberById(memberId));
    }

    @GetMapping(params = "name")
    public ResponseEntity<SearchMemberResponse> getMemberByName(@Valid @ModelAttribute SearchMemberRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findMemberByName(request));
    }

    @GetMapping
    public ResponseEntity<List<SearchMemberResponse>> getMembers() {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findMembers());
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<UpdateMemberResponse> updateMember(
            @PathVariable Long memberId,
            @Valid @RequestBody UpdateMemberRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMember(memberId, request));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
