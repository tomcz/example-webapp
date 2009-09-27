package example.spring.path;

import example.spring.Path;
import example.utils.Maps;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriTemplate;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

public class RequestMappingPathBuilder implements PathBuilder {

    private final String servletPath;

    public RequestMappingPathBuilder() {
        this("");
    }

    public RequestMappingPathBuilder(String servletPath) {
        this.servletPath = servletPath;
    }

    public RedirectView redirectTo(Class handlerClass) {
        return redirectTo(handlerClass, Collections.<String, String>emptyMap());
    }

    public RedirectView redirectTo(Class handlerClass, String paramName, Object paramValue) {
        return redirectTo(handlerClass, Maps.create(paramName, paramValue.toString()));
    }

    public RedirectView redirectTo(Class handlerClass, Map<String, String> pathVariables) {
        Path path = httpGet(handlerClass, pathVariables);
        return new RedirectView(path.getUri(), path.isContextRelative(), true, false);
    }

    public Path httpGet(Class handlerClass) {
        return httpGet(handlerClass, Collections.<String, String>emptyMap());
    }

    public Path httpGet(Class handlerClass, String paramName, Object paramValue) {
        return httpGet(handlerClass, Maps.create(paramName, paramValue.toString()));
    }

    public Path httpGet(Class handlerClass, Map<String, String> pathVariables) {
        return build(RequestMethod.GET, handlerClass, pathVariables);
    }

    public Path httpPost(Class handlerClass) {
        return httpPost(handlerClass, Collections.<String, String>emptyMap());
    }

    public Path httpPost(Class handlerClass, String paramName, Object paramValue) {
        return httpPost(handlerClass, Maps.create(paramName, paramValue.toString()));
    }

    public Path httpPost(Class handlerClass, Map<String, String> pathVariables) {
        return build(RequestMethod.POST, handlerClass, pathVariables);
    }

    public Path build(RequestMethod method, Class handlerClass, Map<String, String> pathVariables) {
        String url = findHandlerClassMapping(handlerClass) + findHandlerMethodMapping(handlerClass, method);
        return new Path(servletPath + expandPathVariables(url, pathVariables));
    }

    public Path build(Class handlerClass, String methodName, Map<String, String> pathVariables) {
        String url = findHandlerClassMapping(handlerClass) + findHandlerMethodMapping(handlerClass, methodName);
        return new Path(servletPath + expandPathVariables(url, pathVariables));
    }

    private String findHandlerClassMapping(Class handlerClass) {
        RequestMapping mapping = AnnotationUtils.findAnnotation(handlerClass, RequestMapping.class);
        return (mapping != null) ? getFirstPath(mapping) : "";
    }

    private String findHandlerMethodMapping(Class handlerClass, RequestMethod method) {
        for (Method classMethod : handlerClass.getMethods()) {
            RequestMapping mapping = AnnotationUtils.findAnnotation(classMethod, RequestMapping.class);
            if (mapping != null && ArrayUtils.contains(mapping.method(), method)) {
                return getFirstPath(mapping);
            }
        }
        throw new IllegalArgumentException(handlerClass.getName() + " cannot handle " + method + " requests");
    }

    private String findHandlerMethodMapping(Class handlerClass, String methodName) {
        for (Method classMethod : handlerClass.getMethods()) {
            if (classMethod.getName().equals(methodName)) {
                RequestMapping mapping = AnnotationUtils.findAnnotation(classMethod, RequestMapping.class);
                if (mapping != null) {
                    return getFirstPath(mapping);
                }
            }
        }
        throw new IllegalArgumentException(handlerClass.getName() +
                " does not contain an annotated method named '" + methodName + "'");
    }

    private String getFirstPath(RequestMapping mapping) {
        String[] paths = mapping.value();
        return (paths.length > 0) ? paths[0] : "";
    }

    private String expandPathVariables(String url, Map<String, String> pathVariables) {
        UriTemplate template = new UriTemplate(url);
        return template.expand(pathVariables).toString();
    }
}
