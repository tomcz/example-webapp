package example.domain.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class FormPage {

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

    public void submitForm(String one, String two, String date) {
        this.one.sendKeys(one);
        this.two.sendKeys(two);
        this.date.sendKeys(date);
        this.submit.click();
    }

    public String getOne() {
        return one.getValue();
    }

    public String getTwo() {
        return two.getValue();
    }

    public String getDate() {
        return date.getValue();
    }

    public String getMessageOne() {
        return messageOne.getText();
    }

    public String getMessageTwo() {
        return messageTwo.getText();
    }

    public String getMessageDate() {
        return messageDate.getText();
    }
}
