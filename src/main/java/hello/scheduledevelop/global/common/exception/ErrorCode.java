package hello.scheduledevelop.global.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "비밀번호가 틀립니다."),
    UNAUTHENTICATE_MEMBER(HttpStatus.UNAUTHORIZED, "로그인 되지 않은 사용자입니다."),

    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    DUPLICATE_NAME(HttpStatus.CONFLICT, "이미 가입된 이름입니다."),

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND, "해당 일정을 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
