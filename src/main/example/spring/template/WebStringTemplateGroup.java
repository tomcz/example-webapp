package example.spring.template;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.InputStream;

public class WebStringTemplateGroup extends StringTemplateGroup {

    private final Logger logger = Logger.getLogger(getClass());

    private final ResourceLoader resourceLoader;

    public WebStringTemplateGroup(ResourceLoader resourceLoader, String templateRoot) {
        super("default", templateRoot);
        this.resourceLoader = resourceLoader;
    }

    public WebStringTemplateGroup(ResourceLoader resourceLoader, String templateRoot, String groupName) {
        super(groupName, templateRoot + "/" + groupName);
        this.resourceLoader = resourceLoader;
    }

    public StringTemplate createStringTemplate() {
        return new WebStringTemplate();
    }

    @Override
    protected StringTemplate loadTemplate(String name, String fileName) {
        Resource resource = resourceLoader.getResource(fileName);
        if (!resource.exists()) {
            logger.debug("Template '" + fileName + "' does not exist");
            return null;
        }
        InputStream stream = null;
        BufferedReader reader = null;
        try {
            stream = resource.getInputStream();
            reader = new BufferedReader(getInputStreamReader(stream));
            return loadTemplate(name, reader);

        } catch (Exception e) {
            logger.debug("Cannot load template from " + fileName + " - error is: " + e);
            return null;

        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(stream);
        }
    }
}
