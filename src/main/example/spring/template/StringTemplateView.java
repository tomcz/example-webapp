package example.spring.template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class StringTemplateView implements TemplateView {

    private final WebStringTemplate template;

    private String contentType = "text/html";
    private String charset = "UTF-8";

    public StringTemplateView(WebStringTemplate template) {
        this.template = template;
    }

    public String getContentType() {
        return contentType + ";charset=" + charset;
    }

    public void setContentType(String contentType, String charset) {
        this.contentType = contentType;
        this.charset = charset;
    }

    public void set(String name, Object value) {
        template.setAttribute(name, value);
    }

    public void setAggregate(String aggrSpec, Object... values) {
        template.setAggregate(aggrSpec, values);
    }

    public void registerRenderer(Renderer renderer) {
        template.registerRenderer(renderer);
    }

    public void setDefaultFormat(WebFormat defaultFormat) {
        template.setDefaultFormat(defaultFormat);
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        set("model", model);
        set("base", request.getContextPath());
        set("request", getRequestAttributes(request));

        registerRenderer(new PathRenderer(request));

        response.setContentType(contentType);
        response.setCharacterEncoding(charset);

        template.write(response.getWriter());
    }

    private Map<String, Object> getRequestAttributes(HttpServletRequest request) {
        Map<String, Object> attributes = new HashMap<String, Object>();
        for (Enumeration names = request.getAttributeNames(); names.hasMoreElements();) {
            String name = (String) names.nextElement();
            attributes.put(name, request.getAttribute(name));
        }
        return attributes;
    }
}
