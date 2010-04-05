package example.error;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringDispatcherServlet {

    private static SpringDispatcherServlet instance;

    private DispatcherServlet servlet;

    public static SpringDispatcherServlet getInstance() {
        if (instance == null) {
            instance = new SpringDispatcherServlet();
        }
        return instance;
    }

    public MockHttpServletResponse process(MockHttpServletRequest request) throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        servlet().service(request, response);
        return response;
    }

    private DispatcherServlet servlet() throws Exception {
        if (servlet == null) {
            MockServletContext servletContext = createServletContext();
            XmlWebApplicationContext services = createRootApplicationContext(servletContext);
            XmlWebApplicationContext controllers = createServletApplicationContext(servletContext, services);
            servlet = createDispatcherServlet(servletContext, controllers);
        }
        return servlet;
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
        applicationContext.setServletContext(servletContext);
        applicationContext.setParent(parentContext);
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
