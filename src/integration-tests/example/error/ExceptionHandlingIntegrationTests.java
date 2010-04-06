package example.error;

import example.spring.Path;
import example.utils.XPathAssert;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static example.spring.PathBuilder.pathTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.pattern.PatternMatcher.matchesPattern;
import static org.hamcrest.text.pattern.Patterns.anyCharacterIn;
import static org.hamcrest.text.pattern.Patterns.exactly;
import static org.hamcrest.text.pattern.Patterns.sequence;
import static org.junit.Assert.assertThat;

public class ExceptionHandlingIntegrationTests {

    @Test
    public void shouldSeeErrorReferenceDisplayedOnThePage() throws Exception {
        SpringDispatcherServlet servlet = SpringDispatcherServlet.getInstance();

        Path path = pathTo(BadPresenter.class).build();

        MockHttpServletResponse response = servlet.process(new MockHttpServletRequest("GET", path.getUri()));

        String redirectedUrl = response.getRedirectedUrl();
        assertThat(redirectedUrl, matchesPattern(sequence("/error/", exactly(7, anyCharacterIn("A-Z0-9")))));

        String errorRef = StringUtils.substringAfterLast(redirectedUrl, "/");

        response = servlet.process(new MockHttpServletRequest("GET", redirectedUrl));

        String html = response.getContentAsString();
        XPathAssert xpath = new XPathAssert(html);

        xpath.matches("count(//span[@id='errorRef'])", is("1"));
        xpath.matches("//span[@id='errorRef']", is(errorRef));
    }
}
