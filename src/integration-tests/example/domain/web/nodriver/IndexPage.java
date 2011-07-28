package example.domain.web.nodriver;

public class IndexPage {

    private final HtmlPage page;

    public IndexPage(HtmlPage page) {
        page.shouldHaveBodyClass("index");
        this.page = page;
    }

    public FormPage createNewForm() {
        return page.clickAndExpect("#newForm a", FormPage.class);
    }

    public FormPage navigateToForm(String formId) {
        return page.clickAndExpect("#" + formId, FormPage.class);
    }
}
