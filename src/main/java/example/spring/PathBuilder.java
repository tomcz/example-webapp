package example.spring;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriTemplate;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PathBuilder {

    private Class handler;
    private String methodName;
    private RequestMethod method;

    private Map<String, String> pathVariables = new HashMap<String, String>();

    private PathBuilder(Class handler) {
        this.handler = handler;
    }

    public static PathBuilder pathTo(Class handler) {
        return new PathBuilder(handler).withMethod(RequestMethod.GET);
    }

    public PathBuilder POST() {
        return withMethod(RequestMethod.POST);
    }

    public PathBuilder withMethod(RequestMethod method) {
        this.method = method;
        return this;
    }

    public PathBuilder withMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public PathBuilder withVar(String name, Object value) {
        pathVariables.put(name, ObjectUtils.toString(value));
        return this;
    }

    public String build() {
        return expandPathVariables(findHandlerClassMapping() + findHandlerMethodMapping());
    }

    public RedirectView redirect() {
        return new ServletRelativeRedirectView(build());
    }

    private String findHandlerClassMapping() {
        RequestMapping mapping = AnnotationUtils.findAnnotation(handler, RequestMapping.class);
        return (mapping != null) ? getFirstPath(mapping) : "";
    }

    private String findHandlerMethodMapping() {
        return (methodName != null) ? findMappingForMethodName() : findMappingForRequestMethod();
    }

    private String findMappingForMethodName() {
        for (Method classMethod : handler.getMethods()) {
            if (classMethod.getName().equals(methodName)) {
                RequestMapping mapping = AnnotationUtils.findAnnotation(classMethod, RequestMapping.class);
                if (mapping != null) {
                    return getFirstPath(mapping);
                }
            }
        }
        throw new IllegalArgumentException(handler.getName() +
                " does not contain an annotated method named '" + methodName + "'");
    }

    private String findMappingForRequestMethod() {
        for (Method classMethod : handler.getMethods()) {
            RequestMapping mapping = AnnotationUtils.findAnnotation(classMethod, RequestMapping.class);
            if (mapping != null && ArrayUtils.contains(mapping.method(), method)) {
                return getFirstPath(mapping);
            }
        }
        throw new IllegalArgumentException(handler.getName() + " cannot handle " + method + " requests");
    }

    private String getFirstPath(RequestMapping mapping) {
        String[] paths = mapping.value();
        return (paths.length > 0) ? paths[0] : "";
    }

    private String expandPathVariables(String url) {
        UriTemplate template = new UriTemplate(url);
        return template.expand(pathVariables).toString();
    }
}
