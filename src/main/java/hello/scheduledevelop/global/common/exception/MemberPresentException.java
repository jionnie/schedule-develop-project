package hello.scheduledevelop.global.common.exception;

import lombok.Getter;

/**
 * 이미 가입된 이메일 또는 이름일 경우 발생하는 예외
 *
 * @author jiwon jung
 */
@Getter
public class MemberPresentException extends CustomException {

    public MemberPresentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
