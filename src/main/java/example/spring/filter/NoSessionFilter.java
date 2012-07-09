package example.spring.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class NoSessionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        chain.doFilter(new NoSessionRequest(request), response);
    }

    public static class NoSessionRequest extends HttpServletRequestWrapper {

        public NoSessionRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public HttpSession getSession() {
            throw new UnsupportedOperationException("Session support disabled");
        }

        @Override
        public HttpSession getSession(boolean create) {
            if (create) {
                throw new UnsupportedOperationException("Session support disabled");
            }
            return null;
        }
    }
}
