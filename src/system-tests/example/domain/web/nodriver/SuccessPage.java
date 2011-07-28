package example.domain.web.nodriver;

import org.apache.commons.lang.StringUtils;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class SuccessPage {

    private final HtmlPage page;

    public SuccessPage(HtmlPage page) {
        page.shouldHaveBodyClass("success");
        this.page = page;
    }

    public void showsValues(String valueOne, String valueTwo, String valueDate) {
        assertThat(page.first("#one").text(), equalTo(valueOne));
        assertThat(page.first("#two").text(), equalTo(valueTwo));
        assertThat(page.first("#date").text(), equalTo(valueDate));
    }

    public String getFormId() {
        return StringUtils.substringAfterLast(page.currentURI(), "/");
    }

    public IndexPage navigateToIndexPage() {
        return page.clickAndExpect("#index", IndexPage.class);
    }
}
