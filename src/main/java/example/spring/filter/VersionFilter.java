package example.spring.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang.StringUtils.defaultIfEmpty;

public class VersionFilter extends OncePerRequestFilter {

    private String version;

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    protected void initFilterBean() throws ServletException {
        version = defaultIfEmpty(version, randomAlphanumeric(7));
        super.initFilterBean();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        request.setAttribute("servletPath", request.getContextPath() + request.getServletPath());
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("version", version);
        filterChain.doFilter(request, response);
    }
}
