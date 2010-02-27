package example.spring.template;

import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.RequestConstants;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class StringTemplateDecoratorServlet extends HttpServletBean {

    private final UrlPathHelper pathHelper = new UrlPathHelper();

    private WebStringTemplateFactory factory;
    private String rootDir;

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String lookupPath = pathHelper.getLookupPathForRequest(request);
        String templateName = FilenameUtils.getBaseName(lookupPath);
        StringTemplateView template = factory().create(templateName);

        HTMLPage htmlPage = (HTMLPage) request.getAttribute(RequestConstants.PAGE);

        if (htmlPage == null) {
            template.set("title", "No Title");
            template.set("body", "No Body");
            template.set("head", "<!-- No head -->");
        } else {
            template.set("page", htmlPage);
            template.set("title", htmlPage.getTitle());
            template.set("head", htmlPage.getHead());
            template.set("body", htmlPage.getBody());
        }

        template.render(Collections.<String, Object>emptyMap(), request, response);
    }

    private WebStringTemplateFactory factory() {
        if (factory == null) {
            WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
            factory = new WebStringTemplateFactory();
            factory.setTemplateRoot(rootDir);
            factory.setResourceLoader(ctx);
        }
        return factory;
    }
}
