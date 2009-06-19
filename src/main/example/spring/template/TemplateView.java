package example.spring.template;

import org.springframework.web.servlet.View;

public interface TemplateView extends View {

    void set(String name, Object value);

    void setSpec(String aggrSpec, Object... values);

    void setContentType(String contentType, String charset);

    void setDefaultFormat(WebFormat defaultFormat);

    void registerRenderer(Renderer renderer);
}
