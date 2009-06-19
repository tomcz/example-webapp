package example.spring.path;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

public interface PathBuilder {
    RedirectView redirectTo(Class handlerClass);

    RedirectView redirectTo(Class handlerClass, String paramName, Object paramValue);

    RedirectView redirectTo(Class handlerClass, Map<String, String> pathVariables);

    String httpGet(Class handlerClass);

    String httpGet(Class handlerClass, String paramName, Object paramValue);

    String httpGet(Class handlerClass, Map<String, String> pathVariables);

    String httpPost(Class handlerClass);

    String httpPost(Class handlerClass, String paramName, Object paramValue);

    String httpPost(Class handlerClass, Map<String, String> pathVariables);

    String build(RequestMethod method, Class handlerClass, Map<String, String> pathVariables);

    String build(Class handlerClass, String methodName, Map<String, String> pathVariables);
}
