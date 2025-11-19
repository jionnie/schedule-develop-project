package hello.scheduledevelop.global.common.exception;

import lombok.Getter;

/**
 * 일정이 존재하지 않을 시 발생하는 예외
 *
 * @author jiwon jung
 */
@Getter
public class ScheduleNotFoundException extends CustomException {

    public ScheduleNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
