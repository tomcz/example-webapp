package example.spring.template;

import org.springframework.core.io.Resource;

import java.io.IOException;

public class WebStringTemplateFactory implements TemplateViewFactory {

    private final String templateRoot;

    public WebStringTemplateFactory(Resource templateRoot) throws IOException {
        this.templateRoot = templateRoot.getURL().toExternalForm();
    }

    public StringTemplateView create(String templateName) {
        WebStringTemplateGroup group = new WebStringTemplateGroup(templateRoot);
        return create(group, templateName);
    }

    public StringTemplateView create(String groupName, String templateName) {
        WebStringTemplateGroup group = new WebStringTemplateGroup(groupName, templateRoot);
        group.setSuperGroup(new WebStringTemplateGroup("shared", templateRoot));
        return create(group, templateName);
    }

    private StringTemplateView create(WebStringTemplateGroup group, String templateName) {
        WebStringTemplate template = (WebStringTemplate) group.getInstanceOf(templateName);
        return new StringTemplateView(template);
    }
}
