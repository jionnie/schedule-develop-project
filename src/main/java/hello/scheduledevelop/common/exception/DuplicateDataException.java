package hello.scheduledevelop.common.exception;

import lombok.Getter;

@Getter
public class DuplicateDataException extends CustomException {

    public DuplicateDataException(ErrorCode errorCode) {
        super(errorCode);
    }
}
