package example.spring.template;

import example.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.List;
import java.util.Locale;

public class StringTemplateViewFactory implements TemplateViewFactory, ResourceLoaderAware, ViewResolver {

    private String templateRoot;
    private ResourceLoader resourceLoader;
    private String sourceFileCharEncoding;

    private List<Renderer> renderers;
    private WebFormat defaultFormat;

    private String defaultContentType;
    private String defaultCharset;

    public void setTemplateRoot(String templateRoot) {
        this.templateRoot = templateRoot;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void setSourceFileCharEncoding(String sourceFileCharEncoding) {
        this.sourceFileCharEncoding = sourceFileCharEncoding;
    }

    public void setRenderers(List<Renderer> renderers) {
        this.renderers = renderers;
    }

    public void setDefaultFormat(String formatName) {
        this.defaultFormat = WebFormat.fromName(formatName);
    }

    public void setDefaultContentType(String defaultContentType) {
        this.defaultContentType = defaultContentType;
    }

    public void setDefaultCharset(String defaultCharset) {
        this.defaultCharset = defaultCharset;
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

    public View resolveViewName(String viewName, Locale locale) throws Exception {
        if (viewName.contains("/")) {
            String groupName = StringUtils.substringBefore(viewName, "/");
            String templateName = StringUtils.substringAfter(viewName, "/");
            return create(groupName, templateName);
        }
        return create(viewName);
    }

    private WebStringTemplateGroup createGroup(String groupName) {
        WebStringTemplateGroup group = new WebStringTemplateGroup(resourceLoader, templateRoot, groupName);
        applySourceFileCharEncoding(group);
        return group;
    }

    private void applySourceFileCharEncoding(WebStringTemplateGroup group) {
        if (sourceFileCharEncoding != null) {
            group.setFileCharEncoding(sourceFileCharEncoding);
        }
    }

    private StringTemplateView create(WebStringTemplateGroup group, String templateName) {
        WebStringTemplate template = (WebStringTemplate) group.getInstanceOf(templateName);
        StringTemplateView view = new StringTemplateView(template);
        registerRenderers(view);
        registerDefaults(view);
        return view;
    }

    private void registerRenderers(StringTemplateView view) {
        if (Lists.isNotEmpty(renderers)) {
            for (Renderer renderer : renderers) {
                view.registerRenderer(renderer);
            }
        }
    }

    private void registerDefaults(StringTemplateView view) {
        if (defaultFormat != null) {
            view.setDefaultFormat(defaultFormat);
        }
        if (defaultContentType != null) {
            view.setContentType(defaultContentType);
        }
        if (defaultCharset != null) {
            view.setCharset(defaultCharset);
        }
    }
}
