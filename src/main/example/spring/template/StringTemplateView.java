package example.spring.template;

import example.utils.Maps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

public class StringTemplateView implements TemplateView {

    private final WebStringTemplate template;

    private String contentType = "text/html";
    private String charset = "UTF-8";

    public StringTemplateView(WebStringTemplate template) {
        this.template = template;
    }

    public String getContentType() {
        return null;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setCharset(String charset) {
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

        set("base", request.getContextPath());
        registerRenderer(new PathRenderer(request));

        addRequestAttributesToTemplate(request);
        addModelAttributesToTemplate(model);

        response.setContentType(contentType);
        response.setCharacterEncoding(charset);

        template.write(response.getWriter());
    }

    private void addRequestAttributesToTemplate(HttpServletRequest request) {
        Map<String, Object> attributes = Maps.create();
        for (Enumeration names = request.getAttributeNames(); names.hasMoreElements();) {
            String name = (String) names.nextElement();
            attributes.put(name, request.getAttribute(name));
        }
        set("request", attributes);
    }

    private void addModelAttributesToTemplate(Map<String, ?> model) {
        Map<String, Object> attributes = Maps.create();
        for (Map.Entry<String, ?> entry : model.entrySet()) {
            String key = entry.getKey();
            if (key.contains(".")) {
                attributes.put(key, entry.getValue());
            } else {
                set(key, entry.getValue());
            }
        }
        if (attributes.size() > 0) {
            set("model", attributes);
        }
    }
}
