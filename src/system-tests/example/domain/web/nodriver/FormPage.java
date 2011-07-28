package example.domain.web.nodriver;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class FormPage {

    private final HtmlPage page;

    public FormPage(HtmlPage page) {
        page.shouldHaveBodyClass("form");
        this.page = page;
    }

    public void showsValues(String valueOne, String valueTwo, String valueDate) {
        HtmlForm form = page.getForm("#documentForm");
        form.showsInputValue("input[name=one]", valueOne);
        form.showsInputValue("input[name=two]", valueTwo);
        form.showsInputValue("input[name=date]", valueDate);
    }

    public void setValues(String valueOne, String valueTwo, String valueDate) {
        HtmlForm form = page.getForm("#documentForm");
        form.setInputValue("input[name=one]", valueOne);
        form.setInputValue("input[name=two]", valueTwo);
        form.setInputValue("input[name=date]", valueDate);
    }

    public <T> T submitAndExpect(Class<T> pageClass) {
        HtmlForm form = page.getForm("#documentForm");
        return form.submitAndExpect(pageClass);
    }

    public void showsErrorForFieldTwo(String errorMessage) {
        assertThat(page.first("div.two.message").text(), equalTo(errorMessage));
    }
}
