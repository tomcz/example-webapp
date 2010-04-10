package example.domain.web.tests;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FormPage implements Page {

    private WebElement one;
    private WebElement two;
    private WebElement date;

    @FindBy(how = How.XPATH, using = "//span[contains(@class, 'one')]")
    private WebElement messageOne;

    @FindBy(how = How.XPATH, using = "//span[contains(@class, 'two')]")
    private WebElement messageTwo;

    @FindBy(how = How.XPATH, using = "//span[contains(@class, 'date')]")
    private WebElement messageDate;

    @FindBy(how = How.NAME, using = "submit")
    private WebElement submit;

    public void verify(PageDriver driver) {
        assertThat(driver.getBodyClass(), equalTo("form"));
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

    public void showsFieldTwoError(String message) {
        assertThat(messageTwo.getText(), equalTo(message));
    }
}
