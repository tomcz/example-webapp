package example.spring.path;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

public class PathBuilderArgumentResolver implements WebArgumentResolver {

    private final UrlPathHelper helper = new UrlPathHelper();

    private boolean useServletPath = true;

    public void setUseServletPath(boolean useServletPath) {
        this.useServletPath = useServletPath;
    }

    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
        if (methodParameter.getParameterType().equals(PathBuilder.class)) {
            if (useServletPath) {
                HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
                return new RequestMappingPathBuilder(helper.getServletPath(request));
            }
            return new RequestMappingPathBuilder();
        }
        return UNRESOLVED;
    }
}
