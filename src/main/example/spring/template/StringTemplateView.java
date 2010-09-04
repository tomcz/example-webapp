package example.spring.template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class StringTemplateView implements TemplateView {

    public static final String DEFAULT_CONTENT_TYPE = "text/html";
    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final String BASE_PATH = "base";
    public static final String MODEL_KEY = "model";
    public static final String REQUEST_KEY = "request";
    public static final String SESSION_KEY = "session";

    private final WebStringTemplate template;

    private String contentType = DEFAULT_CONTENT_TYPE;
    private String charset = DEFAULT_CHARSET;
    private boolean useModelKey = true;

    public StringTemplateView(WebStringTemplate template) {
        this.template = template;
    }

    public void setUseModelKey(boolean useModelKey) {
        this.useModelKey = useModelKey;
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

        set(BASE_PATH, request.getContextPath());

        setModelAttributes(model);

        set(REQUEST_KEY, getRequestAttributes(request));
        set(SESSION_KEY, getSessionAttributes(request));

        registerRenderer(new PathRenderer(request));

        response.setContentType(contentType);
        response.setCharacterEncoding(charset);

        template.write(response.getWriter());
    }

    private void setModelAttributes(Map<String, ?> model) {
        if (useModelKey) {
            set(MODEL_KEY, model);
        } else {
            for (Map.Entry<String, ?> entry : model.entrySet()) {
                set(entry.getKey(), entry.getValue());
            }
        }
    }

    private Map<String, Object> getRequestAttributes(HttpServletRequest request) {
        Map<String, Object> attributes = new HashMap<String, Object>();
        for (Enumeration names = request.getAttributeNames(); names.hasMoreElements();) {
            String name = (String) names.nextElement();
            attributes.put(name, request.getAttribute(name));
        }
        return attributes;
    }

    private Map<String, Object> getSessionAttributes(HttpServletRequest request) {
        Map<String, Object> attributes = new HashMap<String, Object>();
        HttpSession session = request.getSession(false);
        if (session != null) {
            for (Enumeration names = session.getAttributeNames(); names.hasMoreElements();) {
                String name = (String) names.nextElement();
                attributes.put(name, session.getAttribute(name));
            }
        }
        return attributes;
    }
}
