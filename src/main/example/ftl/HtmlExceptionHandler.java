package example.ftl;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;

public class HtmlExceptionHandler implements TemplateExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
        logger.warn(te.getMessage() + "\n" + te.getFTLInstructionStack());
        try {
            out.write("???");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
