package example.spring.filter;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VersionFilter extends OncePerRequestFilter {

    private String version = RandomStringUtils.randomAlphanumeric(7);

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        request.setAttribute("version", version);
        filterChain.doFilter(request, response);
    }
}
