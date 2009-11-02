package example.spring;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

public interface PathBuilder {

    PathRedirectView redirectTo(Class handlerClass);

    PathRedirectView redirectTo(Class handlerClass, String paramName, Object paramValue);

    PathRedirectView redirectTo(Class handlerClass, Map<String, String> pathVariables);

    Path httpGet(Class handlerClass);

    Path httpGet(Class handlerClass, String paramName, Object paramValue);

    Path httpGet(Class handlerClass, Map<String, String> pathVariables);

    Path httpPost(Class handlerClass);

    Path httpPost(Class handlerClass, String paramName, Object paramValue);

    Path httpPost(Class handlerClass, Map<String, String> pathVariables);

    Path build(RequestMethod method, Class handlerClass, Map<String, String> pathVariables);

    Path build(Class handlerClass, String methodName, Map<String, String> pathVariables);
}
