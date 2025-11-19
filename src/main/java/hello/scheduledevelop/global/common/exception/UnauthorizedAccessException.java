package hello.scheduledevelop.global.common.exception;

import lombok.Getter;

/**
 * 권한이 없는 사용자의 접근이 있을 시 발생하는 예외
 *
 * @author jiwon jung
 */
@Getter
public class UnauthorizedAccessException extends CustomException {

    public UnauthorizedAccessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
