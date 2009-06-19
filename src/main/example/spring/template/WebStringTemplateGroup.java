package example.spring.template;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.URL;

public class WebStringTemplateGroup extends StringTemplateGroup {

    private final Logger logger = Logger.getLogger(getClass());

    public WebStringTemplateGroup(String rootDir) {
        super("default", rootDir);
    }

    public WebStringTemplateGroup(String name, String rootDir) {
        super(name, rootDir + "/" + name);
    }

    public StringTemplate createStringTemplate() {
        return new WebStringTemplate();
    }

    @Override
    protected StringTemplate loadTemplate(String name, String fileName) {
        InputStream stream = null;
        BufferedReader reader = null;
        try {
            stream = new URL(fileName).openStream();
            reader = new BufferedReader(getInputStreamReader(stream));
            return loadTemplate(name, reader);

        } catch (Exception e) {
            logger.error("Cannot load template from " + fileName, e);
            return null;

        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(stream);
        }
    }
}
