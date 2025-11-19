package hello.scheduledevelop.global.common.exception;

import hello.scheduledevelop.global.common.dto.ApiResponse;
import hello.scheduledevelop.global.common.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
     * 커스텀 예외 처리
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e) {

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.error(new ErrorResponse(
                        e.getErrorCode().getStatus().value(), e.getErrorCode().name(), e.getMessage())));
    }

    /**
     * 검증 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException e) {

        return ResponseEntity
                .status(e.getStatusCode())
                .body(ApiResponse.error(new ErrorResponse(
                        e.getStatusCode().value(), e.getTypeMessageCode(), e.getMessage())));
    }
}
