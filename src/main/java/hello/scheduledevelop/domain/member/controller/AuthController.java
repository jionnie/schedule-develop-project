package hello.scheduledevelop.domain.member.controller;

import hello.scheduledevelop.domain.member.dto.*;
import hello.scheduledevelop.global.common.dto.ApiResponse;
import hello.scheduledevelop.domain.member.service.AuthService;
import hello.scheduledevelop.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 유저 로그인, 로그아웃과 관련된 REST API 엔드포인트를 제공하는 컨트롤러
 *
 * @author jiwon jung
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(
            @Valid @RequestBody SignupRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(memberService.signup(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<SessionMember>> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        SessionMember sessionMember = authService.login(request);
        session.setAttribute("loginMember", sessionMember); // 세션에 사용자 정보 저장

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(sessionMember));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            HttpSession session) {

        // 로그아웃 시 세션 무력화
        session.invalidate();

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
