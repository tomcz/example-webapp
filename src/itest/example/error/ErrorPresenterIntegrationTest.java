package example.error;

import example.spring.Path;
import example.spring.PathBuilder;
import org.apache.commons.lang.RandomStringUtils;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.junit.internal.matchers.StringContains.containsString;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ErrorPresenterIntegrationTest {

    @Test
    public void shouldSeeErrorReferenceDisplayedOnThePage() throws Exception {
        MockServletContext context = new MockServletContext("web", new FileSystemResourceLoader());
        context.addInitParameter("database.driver.class", "org.hsqldb.jdbcDriver");
        context.addInitParameter("database.driver.url", "jdbc:hsqldb:mem:webapp-template");
        context.addInitParameter("database.driver.username", "sa");
        context.addInitParameter("database.driver.password", "");
        context.addInitParameter("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        context.addInitParameter("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        context.addInitParameter("hibernate.hbm2ddl.auto", "create");

        XmlWebApplicationContext applicationContext = new XmlWebApplicationContext();
        applicationContext.setServletContext(context);
        applicationContext.refresh();

        XmlWebApplicationContext springServletContext = new XmlWebApplicationContext();
        springServletContext.setConfigLocation("/WEB-INF/spring-servlet.xml");
        springServletContext.setParent(applicationContext);
        springServletContext.setServletContext(context);
        springServletContext.refresh();

        context.setAttribute(getClass().getName(), springServletContext);

        DispatcherServlet servlet = new DispatcherServlet();
        servlet.setContextAttribute(getClass().getName());
        servlet.init(new MockServletConfig(context));

        String errorRef = RandomStringUtils.randomAlphanumeric(7).toUpperCase();

        Path link = PathBuilder.httpGet(ErrorPresenter.class, "errorRef", errorRef);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", link.getUri());
        MockHttpServletResponse response = new MockHttpServletResponse();

        servlet.service(request, response);

        String html = response.getContentAsString();

        assertThat(html, containsString(errorRef));
    }
}
