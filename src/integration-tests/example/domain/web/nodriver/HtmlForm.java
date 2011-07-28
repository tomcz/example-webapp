package example.domain.web.nodriver;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@SuppressWarnings({"unchecked"})
public class HtmlForm {

    private final Element form;
    private final Browser browser;

    public HtmlForm(Element form, Browser browser) {
        this.browser = browser;
        this.form = form;
    }

    public void setInputValue(String selector, String value) {
        input(selector).val(value);
    }

    public void showsInputValue(String selector, String value) {
        assertThat(input(selector).val(), equalTo(value));
    }

    public <T> T submitAndExpect(Class<T> pageClass) {
        String method = StringUtils.defaultIfEmpty(form.attr("method"), "post");
        String action = StringUtils.defaultIfEmpty(form.attr("action"), browser.currentURI());
        MockHttpServletRequest request = new MockHttpServletRequest(method.toUpperCase(), action);
        for (Element input : form.select("input")) {
            request.addParameter(input.attr("name"), input.val());
        }
        return browser.send(request, pageClass);
    }

    private Element input(String selector) {
        Element input = first(selector);
        assertThat(input.tagName(), equalTo("input"));
        return input;
    }

    private Element first(String selector) {
        Elements elements = form.select(selector);
        if (elements.isEmpty()) {
            fail("Cannot find " + selector + " in " + form.outerHtml());
            return null;
        }
        return elements.first();
    }
}
