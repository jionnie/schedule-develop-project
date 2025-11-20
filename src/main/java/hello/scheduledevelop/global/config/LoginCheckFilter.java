package hello.scheduledevelop.global.config;

import hello.scheduledevelop.global.common.exception.ErrorCode;
import hello.scheduledevelop.global.common.exception.UnauthenticatedMemberException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@Component
public class LoginCheckFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;

    // 스프링 컨테이너에 등록된 bean 이름 handlerExceptionResolver
    @Autowired
    public LoginCheckFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    // 로그인 필요 없는 경로 목록
    private static final String[] whiteList =
            {
            "/api/signup",
            "/api/login"
            };


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String requestURI = request.getRequestURI();

            // 화이트리스트에 속하지 않은 경로는 로그인 체크
            if (isLoginCheckRequired(requestURI)) {
                // 세션에 로그인 정보가 있는지 확인
                HttpSession session = request.getSession(false);

                if (session == null || session.getAttribute("loginMember") == null) {
                    log.error("로그인 되지 않은 사용자 요청 {}", ErrorCode.UNAUTHENTICATE_MEMBER);
                    throw new UnauthenticatedMemberException(ErrorCode.UNAUTHENTICATE_MEMBER);
                }
            }

            filterChain.doFilter(request, response);

        } catch (UnauthenticatedMemberException e) {
            resolver.resolveException(request, response, null, e);
        }

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
