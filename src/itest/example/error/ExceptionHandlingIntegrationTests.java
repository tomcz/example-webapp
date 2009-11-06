package example.error;

import example.spring.Path;
import static example.spring.PathBuilder.pathToGet;
import org.apache.commons.lang.StringUtils;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.text.pattern.PatternMatcher.matchesPattern;
import static org.hamcrest.text.pattern.Patterns.anyCharacterIn;
import static org.hamcrest.text.pattern.Patterns.exactly;
import static org.hamcrest.text.pattern.Patterns.sequence;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ExceptionHandlingIntegrationTests {

    @Test
    public void shouldSeeErrorReferenceDisplayedOnThePage() throws Exception {
        MockServletContext servletContext = createServletContext();
        XmlWebApplicationContext services = createRootApplicationContext(servletContext);
        XmlWebApplicationContext controllers = createServletApplicationContext(servletContext, services);
        DispatcherServlet servlet = createDispatcherServlet(servletContext, controllers);

        Path path = pathToGet(BadPresenter.class);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", path.getUri());
        MockHttpServletResponse response = new MockHttpServletResponse();

        servlet.service(request, response);

        String redirectedUrl = response.getRedirectedUrl();
        assertThat(redirectedUrl, matchesPattern(sequence("/error/", exactly(7, anyCharacterIn("A-Z0-9")))));

        String errorRef = StringUtils.substringAfterLast(redirectedUrl, "/");

        request = new MockHttpServletRequest("GET", redirectedUrl);
        response = new MockHttpServletResponse();

        servlet.service(request, response);

        String html = response.getContentAsString();

        assertThat(html, containsString(errorRef));
    }

    private MockServletContext createServletContext() {
        MockServletContext context = new MockServletContext("web", new FileSystemResourceLoader());
        context.addInitParameter("database.driver.class", "org.hsqldb.jdbcDriver");
        context.addInitParameter("database.driver.url", "jdbc:hsqldb:mem:webapp-template");
        context.addInitParameter("database.driver.username", "sa");
        context.addInitParameter("database.driver.password", "");
        context.addInitParameter("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        context.addInitParameter("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        context.addInitParameter("hibernate.hbm2ddl.auto", "create");
        return context;
    }

    private XmlWebApplicationContext createRootApplicationContext(MockServletContext servletContext) {
        XmlWebApplicationContext applicationContext = new XmlWebApplicationContext();
        applicationContext.setServletContext(servletContext);
        applicationContext.refresh();
        return applicationContext;
    }

    private XmlWebApplicationContext createServletApplicationContext(
            MockServletContext servletContext, XmlWebApplicationContext parentContext) {

        XmlWebApplicationContext applicationContext = new XmlWebApplicationContext();
        applicationContext.setConfigLocation("/WEB-INF/spring-servlet.xml");
        applicationContext.setParent(parentContext);
        applicationContext.setServletContext(servletContext);
        applicationContext.refresh();
        return applicationContext;
    }

    private DispatcherServlet createDispatcherServlet(
            MockServletContext servletContext, XmlWebApplicationContext applicationContext)
            throws Exception {

        servletContext.setAttribute(getClass().getName(), applicationContext);
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.setContextAttribute(getClass().getName());
        servlet.init(new MockServletConfig(servletContext));
        return servlet;
    }
}
