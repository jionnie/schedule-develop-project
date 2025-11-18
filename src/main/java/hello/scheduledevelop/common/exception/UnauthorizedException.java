package hello.scheduledevelop.common.exception;

import lombok.Getter;

/**
 * 인증 실패 시 예외
 *
 * @author jiwon jung
 */
@Getter
public class UnauthorizedException extends CustomException {

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
