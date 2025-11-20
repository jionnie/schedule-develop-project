package hello.scheduledevelop.global.common.exception;

import lombok.Getter;

/**
 * 이메일을 잘못 입력했거나 유저를 찾을 수 없을 때 발생하는 예외
 *
 * @author jiwon jung
 */
@Getter
public class MemberNotFoundException extends CustomException {

    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
