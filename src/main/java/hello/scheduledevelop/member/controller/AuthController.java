package hello.scheduledevelop.member.controller;

import hello.scheduledevelop.member.dto.LoginRequest;
import hello.scheduledevelop.member.dto.SessionMember;
import hello.scheduledevelop.member.dto.SignupRequest;
import hello.scheduledevelop.member.dto.SignupResponse;
import hello.scheduledevelop.member.service.AuthService;
import hello.scheduledevelop.member.service.MemberService;
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
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> createMember(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRequest> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        SessionMember sessionMember = authService.login(request);
        session.setAttribute("loginMember", sessionMember);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpSession session,
            @SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember) {

        // 세션에 loginMember로 찾은 SessionMember가 없으면 로그인된 사용자 X
        if (sessionMember == null) {
            return ResponseEntity.badRequest().build();
        }

        // 존재하면 세션을 무력화
        session.invalidate();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
