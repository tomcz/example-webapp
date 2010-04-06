package example.spring.template;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

public class StringTemplateViewFactory implements TemplateViewFactory, ResourceLoaderAware {

    private String templateRoot;
    private ResourceLoader resourceLoader;
    private String sourceFileCharEncoding;

    public void setTemplateRoot(String templateRoot) {
        this.templateRoot = templateRoot;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void setSourceFileCharEncoding(String sourceFileCharEncoding) {
        this.sourceFileCharEncoding = sourceFileCharEncoding;
    }

    public StringTemplateView create(String templateName) {
        WebStringTemplateGroup group = new WebStringTemplateGroup(resourceLoader, templateRoot);
        applySourceFileCharEncoding(group);
        return create(group, templateName);
    }

    public StringTemplateView create(String groupName, String templateName) {
        WebStringTemplateGroup group = createGroup(groupName);
        group.setSuperGroup(createGroup("shared"));
        return create(group, templateName);
    }

    private WebStringTemplateGroup createGroup(String groupName) {
        WebStringTemplateGroup group = new WebStringTemplateGroup(resourceLoader, templateRoot, groupName);
        applySourceFileCharEncoding(group);
        return group;
    }

    private void applySourceFileCharEncoding(WebStringTemplateGroup group) {
        if (StringUtils.isNotEmpty(sourceFileCharEncoding)) {
            group.setFileCharEncoding(sourceFileCharEncoding);
        }
    }

    private StringTemplateView create(WebStringTemplateGroup group, String templateName) {
        WebStringTemplate template = (WebStringTemplate) group.getInstanceOf(templateName);
        return new StringTemplateView(template);
    }
}
