package example.spring.template;

import org.antlr.stringtemplate.AttributeRenderer;
import org.antlr.stringtemplate.StringTemplate;

public class WebStringTemplate extends StringTemplate {

    private WebFormat defaultFormat = WebFormat.HTML;

    public void setAggregate(String aggrSpec, Object... values) {
        super.setAttribute(aggrSpec, values);
    }

    public void setDefaultFormat(WebFormat defaultFormat) {
        this.defaultFormat = defaultFormat;
    }

    public AttributeRenderer getAttributeRenderer(Class aClass) {
        AttributeRenderer renderer = super.getAttributeRenderer(aClass);
        return (renderer != null) ? renderer : new WebAttributeRenderer(defaultFormat);
    }
}
