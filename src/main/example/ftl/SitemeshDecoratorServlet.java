package example.ftl;

import com.opensymphony.module.sitemesh.freemarker.FreemarkerDecoratorServlet;
import freemarker.cache.TemplateLoader;

import javax.servlet.ServletException;
import java.io.IOException;

public class SitemeshDecoratorServlet extends FreemarkerDecoratorServlet {

    @Override
    public void init() throws ServletException {
        super.init(); // allow freemarker servlet to setup its configuration
        getConfiguration().setTemplateExceptionHandler(new HtmlExceptionHandler());
    }

    @Override
    protected TemplateLoader createTemplateLoader(String templatePath) throws IOException {
        return new HtmlTemplateLoader(super.createTemplateLoader(templatePath));
    }
}
