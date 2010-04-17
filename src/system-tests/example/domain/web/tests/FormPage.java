package example.domain.web.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FormPage implements Page {

    private WebElement one;
    private WebElement two;
    private WebElement date;

    @FindBy(how = How.NAME, using = "submit")
    private WebElement submit;

    private Browser browser;

    public void verify(Browser browser) {
        assertThat(browser.getBodyClass(), equalTo("form"));
        this.browser = browser;
    }

    public void submitForm(String valueOne, String valueTwo, String valueDate) {
        one.sendKeys(valueOne);
        two.sendKeys(valueTwo);
        date.sendKeys(valueDate);
        submit.click();
    }

    public void showsValues(String valueOne, String valueTwo, String valueDate) {
        assertThat(one.getValue(), equalTo(valueOne));
        assertThat(two.getValue(), equalTo(valueTwo));
        assertThat(date.getValue(), equalTo(valueDate));
    }

    public void showsErrorForFieldTwo(String message) {
        WebElement messageTwo = browser.findElement(By.cssSelector("div.two.message"));
        assertThat(messageTwo.getText(), equalTo(message));
    }
}
