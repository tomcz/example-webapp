package example.spring.template.sitemesh;

import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.RequestConstants;
import example.spring.template.StringTemplateView;
import example.spring.template.StringTemplateViewFactory;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;

public class StringTemplateDecoratorServlet extends HttpServletBean {

    private final UrlPathHelper pathHelper = new UrlPathHelper();

    private StringTemplateViewFactory factory;
    private String rootDir;

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    @Override
    protected void initServletBean() throws ServletException {
        factory = new StringTemplateViewFactory();
        factory.setResourceLoader(getRequiredWebApplicationContext(getServletContext()));
        factory.setTemplateRoot(rootDir);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String lookupPath = pathHelper.getLookupPathForRequest(request);
        String templateName = FilenameUtils.getBaseName(lookupPath);
        StringTemplateView template = factory.create(templateName);

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
}
