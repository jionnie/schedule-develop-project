package hello.scheduledevelop.common.exception;

import hello.scheduledevelop.common.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 사용자 예외를 핸들링 하는 클래스
 * 컨트롤러까지 올라온 예외들을 이 곳에서 잡아서 처리한다.
 *
 * @author jiwon jung
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 인증, 인가 실패 시 처리
     *
     * @param e 이메일, 비밀번호 불일치 예외 클래스
     * @return 에러 응답 객체
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
    }

    /**
     * 회원가입 실패 시 처리
     *
     * @param e 이메일, 비밀번호가 이미 존재 시 예외 클래스
     * @return 에러 응답 객체
     */
    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ErrorResponse> handleFailedSignup(DuplicateDataException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
    }

    /**
     * 유저, 일정이 존재하지 않을 시 처리
     * 
     * @param e 유저, 일정이 존재하지 않을 시 예외 클래스
     * @return 에러 응답 객체
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFound(DataNotFoundException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
    }
}
