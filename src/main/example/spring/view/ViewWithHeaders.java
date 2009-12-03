package example.spring.view;

import example.utils.Maps;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ViewWithHeaders implements View {

    private final Map<String, List<String>> headers;
    private final View delegate;

    public ViewWithHeaders(View delegate) {
        this.headers = Maps.create();
        this.delegate = delegate;
    }

    public void setHeader(String name, String... values) {
        setHeader(name, Arrays.asList(values));
    }

    public void setHeader(String name, List<String> values) {
        headers.put(name, values);
    }

    public String getContentType() {
        return delegate.getContentType();
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        addHeadersToResponse(response);
        delegate.render(model, request, response);
    }

    private void addHeadersToResponse(HttpServletResponse response) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            boolean isFirst = true;
            String name = entry.getKey();
            for (String value : entry.getValue()) {
                if (isFirst) {
                    response.setHeader(name, value);
                } else {
                    response.addHeader(name, value);
                }
                isFirst = false;
            }
        }
    }
}
