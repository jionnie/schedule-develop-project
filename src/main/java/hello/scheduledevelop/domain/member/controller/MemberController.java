package hello.scheduledevelop.domain.member.controller;

import hello.scheduledevelop.domain.member.dto.*;
import hello.scheduledevelop.global.common.dto.ApiResponse;
import hello.scheduledevelop.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 유저 조회, 수정, 삭제 REST API 엔드포인트를 제공하는 컨트롤러
 *
 * @author jiwon jung
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * id로 유저 조회
     *
     * @param memberId 유저 고유 id
     * @return ResponseEntity<ApiResponse<SearchMemberResponse>>
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<SearchMemberResponse>> getMemberById(@PathVariable Long memberId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(memberService.findMemberById(memberId)));
    }

    /**
     * 이름으로 유저 조회
     *
     * @param request 유저 이름이 담긴 요청 DTO
     * @return ResponseEntity<ApiResponse<SearchMemberResponse>>
     */
    @GetMapping
    public ResponseEntity<ApiResponse<SearchMemberResponse>> getMemberByName(@Valid @ModelAttribute SearchMemberRequest request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(memberService.findMemberByName(request)));
    }

    /**
     * 유저 정보 수정
     *
     * @param sessionMember 세션 정보가 담긴 DTO
     * @param request 사용자 정보 수정 요청 DTO
     * @return ResponseEntity<ApiResponse<UpdateMemberResponse>>
     */
    @PatchMapping
    public ResponseEntity<ApiResponse<UpdateMemberResponse>> updateMember(
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember,
            @Valid @RequestBody UpdateMemberRequest request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(memberService.updateMember(sessionMember.getId(), request)));
    }

    /**
     * 유저 삭제
     *
     * @param sessionMember 세션 정보가 담긴 DTO
     * @return ResponseEntity<Void>
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteMember(
            HttpSession session,
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember) {

        memberService.deleteMember(sessionMember.getId());

        // 유저가 삭제 됐으므로 세션 무력화
        session.invalidate();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
