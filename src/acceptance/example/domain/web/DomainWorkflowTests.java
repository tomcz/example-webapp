package example.domain.web;

import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class DomainWorkflowTests {

    private static WebServerDriver driver;

    @BeforeClass
    public static void start() throws Exception {
        driver = new WebServerDriver();
        driver.start();
    }

    @AfterClass
    public static void stop() {
        driver.stop();
    }

    @Test
    public void shouldStoreFormDetailsCorrectly() {
        driver.getURL("/");
        assertThat(bodyClass(), equalTo("index"));

        driver.findElement(By.linkText("here")).click();
        assertThat(bodyClass(), equalTo("form"));

        FormPage form = driver.initPage(FormPage.class);
        form.submitForm("homer", "simpson", "10/03/2010");

        assertThat(bodyClass(), equalTo("success"));
        assertThat(elementText("one"), equalTo("homer"));
        assertThat(elementText("two"), equalTo("simpson"));
        assertThat(elementText("date"), equalTo("10/03/2010"));

        String successId = StringUtils.substringAfterLast(driver.getCurrentUrl(), "/");

        driver.findElement(By.xpath("//a[@id='index']")).click();
        assertThat(bodyClass(), equalTo("index"));

        driver.findElement(By.linkText(successId)).click();
        assertThat(bodyClass(), equalTo("form"));

        form = driver.initPage(FormPage.class);
        assertThat(form.getOne(), equalTo("homer"));
        assertThat(form.getTwo(), equalTo("simpson"));
        assertThat(form.getDate(), equalTo("10/03/2010"));
    }

    private String bodyClass() {
        return driver.findElement(By.tagName("body")).getAttribute("class");
    }

    private String elementText(String id) {
        return driver.findElement(By.id(id)).getText();
    }
}
