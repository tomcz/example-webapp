package example.spring.template;

import example.utils.Maps;
import org.antlr.stringtemplate.NoIndentWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

public class StringTemplateView implements TemplateView {

    private final WebStringTemplate template;

    private String contentType;
    private String charset;

    public StringTemplateView(WebStringTemplate template) {
        this(template, "text/html", "UTF-8");
    }

    public StringTemplateView(WebStringTemplate template, String contentType, String charset) {
        this.template = template;
        this.contentType = contentType;
        this.charset = charset;
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
        template.registerRenderer(renderer.getTypeToRender(), new RendererAdaptor(renderer));
    }

    public void setDefaultFormat(WebFormat defaultFormat) {
        template.setDefaultFormat(defaultFormat);
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType(getContentType());
        response.setCharacterEncoding(charset);

        template.setAttribute("model", model);
        template.setAttribute("base", request.getContextPath());
        template.setAttribute("request", createMapOfRequestAttributes(request));

        template.write(new NoIndentWriter(response.getWriter()));
    }

    private Map<String, Object> createMapOfRequestAttributes(HttpServletRequest request) {
        Map<String, Object> attributes = Maps.create();
        for (Enumeration names = request.getAttributeNames(); names.hasMoreElements();) {
            String name = (String) names.nextElement();
            attributes.put(name, request.getAttribute(name));
        }
        return attributes;
    }
}
