package hello.scheduledevelop.global.common.exception;

import lombok.Getter;

/**
 * 공통 커스텀 예외 클래스
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
