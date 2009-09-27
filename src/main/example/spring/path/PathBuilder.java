package example.spring.path;

import example.spring.Path;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

public interface PathBuilder {

    RedirectView redirectTo(Class handlerClass);

    RedirectView redirectTo(Class handlerClass, String paramName, Object paramValue);

    RedirectView redirectTo(Class handlerClass, Map<String, String> pathVariables);

    Path httpGet(Class handlerClass);

    Path httpGet(Class handlerClass, String paramName, Object paramValue);

    Path httpGet(Class handlerClass, Map<String, String> pathVariables);

    Path httpPost(Class handlerClass);

    Path httpPost(Class handlerClass, String paramName, Object paramValue);

    Path httpPost(Class handlerClass, Map<String, String> pathVariables);

    Path build(RequestMethod method, Class handlerClass, Map<String, String> pathVariables);

    Path build(Class handlerClass, String methodName, Map<String, String> pathVariables);
}
