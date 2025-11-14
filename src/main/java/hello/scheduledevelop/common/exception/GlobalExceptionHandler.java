package hello.scheduledevelop.common.exception;

import org.springframework.http.HttpStatus;
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
     * 비밀번호 불일치 시 처리
     *
     * @param e 비밀번호 불일치 예외 클래스
     * @return 에러 메세지
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleInvalidPassword(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}
