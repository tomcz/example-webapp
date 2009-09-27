package example.error;

import example.spring.path.PathBuilder;
import example.spring.path.RequestMappingPathBuilder;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ExceptionHandler implements HandlerExceptionResolver {

    private final UrlPathHelper pathHelper = new UrlPathHelper();
    private final Logger logger = Logger.getLogger(getClass());

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {

        String lookupPath = pathHelper.getLookupPathForRequest(request);
        String errorRef = RandomStringUtils.randomAlphanumeric(7).toUpperCase();
        logger.error("Unexpected error [" + errorRef + "] for path [" + lookupPath + "]: " + ex, ex);

        PathBuilder builder = new RequestMappingPathBuilder(pathHelper.getServletPath(request));
        RedirectView view = builder.redirectTo(ErrorPresenter.class, "errorRef", errorRef);
        return new ModelAndView(view);
    }
}
