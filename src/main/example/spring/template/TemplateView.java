package example.spring.template;

import org.springframework.web.servlet.View;

public interface TemplateView extends View {

    void set(String name, Object value);

    void setAggregate(String aggrSpec, Object... values);

    void setDefaultFormat(WebFormat defaultFormat);

    void registerRenderer(Renderer renderer);

    void setContentType(String contentType);

    void setCharset(String charset);
}
