package example.domain.web.nodriver;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.mock.web.MockHttpServletRequest;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.collection.LambdaCollections.with;
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

    public void setInputValue(String fieldName, String value) {
        input(fieldName).val(value);
    }

    public void showsInputValue(String fieldName, String value) {
        assertThat(fieldName, input(fieldName).val(), equalTo(value));
    }

    public void selectOptionByValue(String fieldName, String value) {
        for (Element option : options(fieldName, "option")) {
            if (option.val().equals(value)) {
                option.attr("selected", "selected");
            } else {
                option.removeAttr("selected");
            }
        }
    }

    public void showsSelectedOptionWithValue(String fieldName, String value) {
        Elements options = options(fieldName, "option[selected]");
        if (!with(options).exists(having(on(Element.class).val(), equalTo(value)))) {
            fail("Select '" + fieldName + "' does not have '" + value + "' selected in " + form.outerHtml());
        }
    }

    public <T> T submitAndExpect(Class<T> pageClass) {
        String method = StringUtils.defaultIfEmpty(form.attr("method"), "post");
        String action = StringUtils.defaultIfEmpty(form.attr("action"), browser.currentURI());
        MockHttpServletRequest request = new MockHttpServletRequest(method.toUpperCase(), action);
        for (Element input : form.select("input[type=text]")) {
            request.addParameter(input.attr("name"), input.val());
        }
        for (Element select : form.select("select")) {
            String name = select.attr("name");
            Elements selected = select.select("option[selected]");
            if (selected.isEmpty()) {
                Elements options = select.select("option");
                if (options.size() > 0) {
                    request.addParameter(name, options.first().val());
                }
            } else {
                for (Element option : selected) {
                    request.addParameter(name, option.val());
                }
            }
        }
        return browser.send(request, pageClass);
    }

    private Element input(String fieldName) {
        return first("input[name=" + fieldName + "][type=text]");
    }

    private Elements options(String fieldName, String option) {
        String query = "select[name=" + fieldName + "] " + option;
        Elements options = form.select(query);
        if (options.isEmpty()) {
            fail("Cannot find " + query + " in " + form.outerHtml());
        }
        return options;
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
