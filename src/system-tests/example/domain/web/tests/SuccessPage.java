package example.domain.web.tests;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SuccessPage implements Page {

    private WebElement one;
    private WebElement two;
    private WebElement date;

    private PageDriver driver;

    public void verify(PageDriver driver) {
        assertThat(driver.getBodyClass(), equalTo("success"));
        this.driver = driver;
    }

    public void showsValues(String valueOne, String valueTwo, String valueDate) {
        assertThat(one.getText(), equalTo(valueOne));
        assertThat(two.getText(), equalTo(valueTwo));
        assertThat(date.getText(), equalTo(valueDate));
    }

    public String getFormId() {
        return StringUtils.substringAfterLast(driver.getCurrentUrl(), "/");
    }

    public void navigateToIndexPage() {
        driver.findElement(By.xpath("//a[@id='index']")).click();
    }
}
