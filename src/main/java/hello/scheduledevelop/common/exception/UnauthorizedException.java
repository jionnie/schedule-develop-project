package hello.scheduledevelop.common.exception;

/**
 * 인증 실패 시 예외
 *
 * @author jiwon jung
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
