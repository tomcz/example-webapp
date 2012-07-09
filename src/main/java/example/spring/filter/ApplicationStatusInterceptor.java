package example.spring.filter;

import example.spring.ApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApplicationStatusInterceptor extends HandlerInterceptorAdapter {

    private final ApplicationStatus status;

    @Autowired
    public ApplicationStatusInterceptor(ApplicationStatus status) {
        this.status = status;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!status.ready()) {
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return false;
        }
        return true;
    }
}
