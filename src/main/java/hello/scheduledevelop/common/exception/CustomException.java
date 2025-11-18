package hello.scheduledevelop.common.exception;

import lombok.Getter;

/**
 * 공통 예외를 처리하는 클래스
 *
 * @author jiwon jung
 */
@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
