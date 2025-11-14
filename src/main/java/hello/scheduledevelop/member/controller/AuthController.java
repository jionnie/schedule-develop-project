package hello.scheduledevelop.member.controller;

import hello.scheduledevelop.member.dto.LoginRequest;
import hello.scheduledevelop.member.dto.SessionMember;
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

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginRequest> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        SessionMember sessionMember = memberService.login(request);
        session.setAttribute("loginMember", sessionMember);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@SessionAttribute(name = "loginMember", required = false) SessionMember sessionMember, HttpSession session) {
        if (sessionMember == null) {
            return ResponseEntity.badRequest().build();
        }

        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
