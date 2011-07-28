package example.domain.web.nodriver;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class HtmlPage {

    private String html;
    private Document dom;
    private Browser browser;

    public HtmlPage(MockHttpServletResponse response, Browser browser) throws Exception {
        this.browser = browser;
        if (StringUtils.startsWith(response.getContentType(), "text/html")) {
            html = response.getContentAsString();
            dom = Jsoup.parse(html);
        }
    }

    public String currentURI() {
        return browser.currentURI();
    }

    public void shouldHaveBodyClass(String index) {
        assertNotNull("Page is not text/html", html);
        assertThat(dom.body().classNames(), hasItem(index));
    }

    public <T> T clickAndExpect(String selector, Class<T> pageClass) {
        Element link = first(selector);
        assertThat("Unexpected element", link.tagName(), equalTo("a"));
        String href = link.attr("href");
        if (StringUtils.isEmpty(href)) {
            fail("Empty href attribute in " + link.outerHtml());
            return null;
        }
        return browser.get(href, pageClass);
    }

    public HtmlForm getForm(String selector) {
        Element form = first(selector);
        assertThat("Unexpected element", form.tagName(), equalTo("form"));
        return new HtmlForm(form, browser);
    }

    public Element first(String selector) {
        Elements elements = dom.select(selector);
        if (elements.isEmpty()) {
            fail("Cannot find " + selector + " in " + html);
            return null;
        }
        return elements.first();
    }
}
