package example.domain.web.tests;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ErrorPage implements Page {

    private PageDriver driver;

    public void verify(PageDriver driver) {
        assertThat(driver.getBodyClass(), equalTo("error"));
        this.driver = driver;
    }

    public void showsErrorId() {
        String errorId = driver.findElement(By.id("errorRef")).getText();
        String urlSuffix = StringUtils.substringAfterLast(driver.getCurrentUrl(), "/");
        assertThat("Should see error ID on page", errorId, equalTo(urlSuffix));
    }
}
