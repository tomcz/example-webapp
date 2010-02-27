package example.spring.template;

import org.antlr.stringtemplate.AttributeRenderer;
import org.antlr.stringtemplate.NoIndentWriter;
import org.antlr.stringtemplate.StringTemplate;

import java.io.IOException;
import java.io.Writer;

public class WebStringTemplate extends StringTemplate {

    private WebFormat defaultFormat = WebFormat.HTML;

    public void setAggregate(String aggrSpec, Object... values) {
        super.setAttribute(aggrSpec, values);
    }

    public void setDefaultFormat(WebFormat defaultFormat) {
        this.defaultFormat = defaultFormat;
    }

    public void registerRenderer(Renderer renderer) {
        registerRenderer(renderer.getTypeToRender(), new RendererAdaptor(renderer));
    }

    public AttributeRenderer getAttributeRenderer(Class aClass) {
        AttributeRenderer renderer = super.getAttributeRenderer(aClass);
        return (renderer != null) ? renderer : new WebAttributeRenderer(defaultFormat);
    }

    public void write(Writer out) throws IOException {
        write(new NoIndentWriter(out));
    }
}
