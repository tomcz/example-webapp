package example.spring.template;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class StringTemplateViewFactory implements TemplateViewFactory, ResourceLoaderAware, ViewResolver {

    public static final String SHARED_GROUP_NAME = "shared";
    public static final String VIEW_NAME_SEPARATOR = "/";

    private ResourceLoader resourceLoader;
    private String sourceFileCharEncoding;

    private String contentType = StringTemplateView.DEFAULT_CONTENT_TYPE;
    private String charset = StringTemplateView.DEFAULT_CHARSET;
    private boolean useModelKey = true;
    private String templateRoot = "";

    private List<Renderer> renderers = Collections.emptyList();

    public void setTemplateRoot(String templateRoot) {
        this.templateRoot = templateRoot;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void setSourceFileCharEncoding(String sourceFileCharEncoding) {
        this.sourceFileCharEncoding = sourceFileCharEncoding;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setUseModelKey(boolean useModelKey) {
        this.useModelKey = useModelKey;
    }

    public void setRenderers(List<Renderer> renderers) {
        this.renderers = renderers;
    }

    public StringTemplateView create(String templateName) {
        WebStringTemplateGroup group = new WebStringTemplateGroup(resourceLoader, templateRoot);
        applySourceFileCharEncoding(group);
        return create(group, templateName);
    }

    public StringTemplateView create(String groupName, String templateName) {
        WebStringTemplateGroup group = createGroup(groupName);
        group.setSuperGroup(createGroup(SHARED_GROUP_NAME));
        return create(group, templateName);
    }

    public View resolveViewName(String viewName, Locale locale) throws Exception {
        if (viewName.contains(VIEW_NAME_SEPARATOR)) {
            String groupName = StringUtils.substringBefore(viewName, VIEW_NAME_SEPARATOR);
            String templateName = StringUtils.substringAfter(viewName, VIEW_NAME_SEPARATOR);
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
        if (StringUtils.isNotEmpty(sourceFileCharEncoding)) {
            group.setFileCharEncoding(sourceFileCharEncoding);
        }
    }

    private StringTemplateView create(WebStringTemplateGroup group, String templateName) {
        WebStringTemplate template = group.createTemplate(templateName);
        for (Renderer renderer : renderers) {
            template.registerRenderer(renderer);
        }
        StringTemplateView view = new StringTemplateView(template);
        view.setContentType(contentType, charset);
        view.setUseModelKey(useModelKey);
        return view;
    }
}
