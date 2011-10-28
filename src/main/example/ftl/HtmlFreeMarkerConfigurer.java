package example.ftl;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.List;

public class HtmlFreeMarkerConfigurer extends FreeMarkerConfigurer {

    @Override
    protected TemplateLoader getAggregateTemplateLoader(List<TemplateLoader> templateLoaders) {
        logger.info("Using HtmlTemplateLoader to enforce HTML-safe content");
        return new HtmlTemplateLoader(super.getAggregateTemplateLoader(templateLoaders));
    }

    @Override
    protected void postProcessTemplateLoaders(List<TemplateLoader> templateLoaders) {
        // spring.ftl from classpath will not work with the HtmlTemplateLoader
        // use /WEB-INF/templates/spring.ftl instead
    }

    @Override
    protected void postProcessConfiguration(Configuration config) throws IOException, TemplateException {
        config.setTemplateExceptionHandler(new HtmlExceptionHandler());
    }
}
