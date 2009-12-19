package example.spring.view;

import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ViewWithStatusCode implements View {

    private final int statusCode;
    private final View delegate;

    public ViewWithStatusCode(int statusCode) {
        this(statusCode, new StaticView());
    }

    public ViewWithStatusCode(int statusCode, View delegate) {
        this.statusCode = statusCode;
        this.delegate = delegate;
    }

    public String getContentType() {
        return delegate.getContentType();
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setStatus(statusCode);
        delegate.render(model, request, response);
    }
}
