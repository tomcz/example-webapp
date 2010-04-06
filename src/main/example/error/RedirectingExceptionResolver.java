package example.error;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static example.spring.view.RedirectBuilder.redirectTo;

@Component
public class RedirectingExceptionResolver implements HandlerExceptionResolver {

    private final Logger logger = Logger.getLogger(getClass());
    private final UrlPathHelper pathHelper = new UrlPathHelper();

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {

        String lookupPath = pathHelper.getLookupPathForRequest(request);
        String errorRef = RandomStringUtils.randomAlphanumeric(7).toUpperCase();

        logger.error("Unexpected error [" + errorRef + "] for path [" + lookupPath + "]: " + ex, ex);

        return new ModelAndView(redirectTo(ErrorPresenter.class).withVar("errorRef", errorRef).build());
    }
}
