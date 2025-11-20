package hello.scheduledevelop.global.config;

import hello.scheduledevelop.global.common.exception.ErrorCode;
import hello.scheduledevelop.global.common.exception.UnauthenticatedMemberException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class LoginCheckFilter implements Filter {

    // 로그인 필요 없는 경로 목록
    private static final String[] whiteList =
            {
            "/api/signup",
            "/api/login"
            };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();

        // 화이트리스트에 속하지 않은 경로는 로그인 체크
        if (isLoginCheckRequired(requestURI)) {
            // 세션에 로그인 정보가 있는지 확인
            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("loginMember") == null) {
                log.error("로그인 되지 않은 사용자 요청 {}", ErrorCode.UNAUTHENTICATE_MEMBER);
                throw new UnauthenticatedMemberException(ErrorCode.UNAUTHENTICATE_MEMBER);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("filter destroy");
    }

    private boolean isLoginCheckRequired(String requestURI) {
        for (String path : whiteList) {
            if (requestURI.startsWith(path)) {
                return false;
            }
        }

        return true;
    }
}
