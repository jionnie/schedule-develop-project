package hello.scheduledevelop.common.exception;

import lombok.Getter;

@Getter
public class DataNotFoundException extends CustomException {

    public DataNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
