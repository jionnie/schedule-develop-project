package hello.scheduledevelop.global.common.exception;

import lombok.Getter;

/**
 * 로그인 되지 않은 사용자일 경우 발생하는 예외
 *
 * @author jiwon jung
 */
@Getter
public class UnauthenticatedMemberException extends CustomException {

    public UnauthenticatedMemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
