package example.domain.hibernate;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.sql.DataSource;

public class GenerateSchema {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        XmlWebApplicationContext ctx = createRootApplicationContext(createServletContext());

        DataSource dataSource = ctx.getBean(DataSource.class);
        LocalSessionFactoryBean factoryBean = ctx.getBean(LocalSessionFactoryBean.class);
        Configuration configuration = factoryBean.getConfiguration();

        SchemaExport export = new SchemaExport(configuration, dataSource.getConnection()).setDelimiter(";");

        boolean printDDLToConsole = true;
        if (args.length > 0) {
            String outputFile = args[0];
            System.out.println("Writing out create statements to " + outputFile);
            printDDLToConsole = false;
            export.setOutputFile(outputFile);
        }
        export.execute(printDDLToConsole, false, false, true);

        printDDLToConsole = true;
        if (args.length > 1) {
            String outputFile = args[1];
            System.out.println("Writing out drop statements to " + outputFile);
            printDDLToConsole = false;
            export.setOutputFile(outputFile);
        }
        export.execute(printDDLToConsole, false, true, false);

        long end = System.currentTimeMillis();

        ctx.stop();
        System.out.println("Completed in " + (end - start) + "ms");
    }

    private static MockServletContext createServletContext() {
        MockServletContext context = new MockServletContext("src/webapp", new FileSystemResourceLoader());
        context.addInitParameter("database.driver.class", "org.hsqldb.jdbcDriver");
        context.addInitParameter("database.driver.url", "jdbc:hsqldb:mem:webapp-template");
        context.addInitParameter("database.driver.username", "sa");
        context.addInitParameter("database.driver.password", "");
        context.addInitParameter("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        context.addInitParameter("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        return context;
    }

    private static XmlWebApplicationContext createRootApplicationContext(MockServletContext servletContext) {
        XmlWebApplicationContext applicationContext = new XmlWebApplicationContext();
        applicationContext.setServletContext(servletContext);
        applicationContext.refresh();
        return applicationContext;
    }
}
