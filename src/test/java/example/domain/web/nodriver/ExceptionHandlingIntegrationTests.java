package example.domain.web.nodriver;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.pattern.PatternMatcher.matchesPattern;
import static org.hamcrest.text.pattern.Patterns.anyCharacterIn;
import static org.hamcrest.text.pattern.Patterns.exactly;
import static org.hamcrest.text.pattern.Patterns.sequence;
import static org.junit.Assert.assertThat;

public class ExceptionHandlingIntegrationTests {

    @Test
    public void shouldSeeErrorReferenceDisplayedOnThePage() throws Exception {
        SpringDispatcherServlet servlet = SpringDispatcherServlet.create();

        MockHttpServletResponse response = servlet.process(new MockHttpServletRequest("GET", "/bad"));

        String redirectedUrl = response.getRedirectedUrl();
        assertThat(redirectedUrl, matchesPattern(sequence("/error/", exactly(7, anyCharacterIn("A-Z0-9")))));

        String errorRef = StringUtils.substringAfterLast(redirectedUrl, "/");

        response = servlet.process(new MockHttpServletRequest("GET", redirectedUrl));

        String html = response.getContentAsString();
        Document document = Jsoup.parse(html);

        Elements elements = document.select("#errorRef");
        assertThat(elements.size(), equalTo(1));

        assertThat(elements.first().text(), equalTo(errorRef));
    }
}
