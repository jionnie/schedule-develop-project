package hello.scheduledevelop.common.dto;

import hello.scheduledevelop.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 상태 코드를 관리하는 Enum 클래스
 *
 * @author jiwon jung
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final int status;
    private final String code;
    private final String message;

    public ErrorResponse(ErrorCode errorCode, String message) {
        this.status = errorCode.getStatus().value();
        this.code = errorCode.name();
        this.message = message;
    }
}
