package example.spring;

import example.utils.Maps;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriTemplate;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

public class PathBuilder {

    public static PathRedirectView redirectTo(Class handlerClass) {
        return redirectTo(handlerClass, Collections.<String, String>emptyMap());
    }

    public static PathRedirectView redirectTo(Class handlerClass, String paramName, Object paramValue) {
        return redirectTo(handlerClass, Maps.create(paramName, paramValue.toString()));
    }

    public static PathRedirectView redirectTo(Class handlerClass, Map<String, String> pathVariables) {
        return new PathRedirectView(httpGet(handlerClass, pathVariables));
    }

    public static Path httpGet(Class handlerClass) {
        return httpGet(handlerClass, Collections.<String, String>emptyMap());
    }

    public static Path httpGet(Class handlerClass, String paramName, Object paramValue) {
        return httpGet(handlerClass, Maps.create(paramName, paramValue.toString()));
    }

    public static Path httpGet(Class handlerClass, Map<String, String> pathVariables) {
        return build(RequestMethod.GET, handlerClass, pathVariables);
    }

    public static Path httpPost(Class handlerClass) {
        return httpPost(handlerClass, Collections.<String, String>emptyMap());
    }

    public static Path httpPost(Class handlerClass, String paramName, Object paramValue) {
        return httpPost(handlerClass, Maps.create(paramName, paramValue.toString()));
    }

    public static Path httpPost(Class handlerClass, Map<String, String> pathVariables) {
        return build(RequestMethod.POST, handlerClass, pathVariables);
    }

    public static Path build(RequestMethod method, Class handlerClass, Map<String, String> pathVariables) {
        String url = findHandlerClassMapping(handlerClass) + findHandlerMethodMapping(handlerClass, method);
        return new Path(expandPathVariables(url, pathVariables));
    }

    public static Path build(Class handlerClass, String methodName, Map<String, String> pathVariables) {
        String url = findHandlerClassMapping(handlerClass) + findHandlerMethodMapping(handlerClass, methodName);
        return new Path(expandPathVariables(url, pathVariables));
    }

    private static String findHandlerClassMapping(Class handlerClass) {
        RequestMapping mapping = AnnotationUtils.findAnnotation(handlerClass, RequestMapping.class);
        return (mapping != null) ? getFirstPath(mapping) : "";
    }

    private static String findHandlerMethodMapping(Class handlerClass, RequestMethod method) {
        for (Method classMethod : handlerClass.getMethods()) {
            RequestMapping mapping = AnnotationUtils.findAnnotation(classMethod, RequestMapping.class);
            if (mapping != null && ArrayUtils.contains(mapping.method(), method)) {
                return getFirstPath(mapping);
            }
        }
        throw new IllegalArgumentException(handlerClass.getName() + " cannot handle " + method + " requests");
    }

    private static String findHandlerMethodMapping(Class handlerClass, String methodName) {
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

    private static String getFirstPath(RequestMapping mapping) {
        String[] paths = mapping.value();
        return (paths.length > 0) ? paths[0] : "";
    }

    private static String expandPathVariables(String url, Map<String, String> pathVariables) {
        UriTemplate template = new UriTemplate(url);
        return template.expand(pathVariables).toString();
    }
}
