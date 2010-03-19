package example.error;

import example.spring.Path;
import example.spring.PathBuilder;
import example.utils.XPathAssert;
import org.apache.commons.lang.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.pattern.PatternMatcher.matchesPattern;
import static org.hamcrest.text.pattern.Patterns.anyCharacterIn;
import static org.hamcrest.text.pattern.Patterns.exactly;
import static org.hamcrest.text.pattern.Patterns.sequence;
import static org.junit.Assert.assertThat;

public class ExceptionHandlingIntegrationTests {

    @Rule
    public DispatcherServletRule servlet = new DispatcherServletRule();

    @Test
    public void shouldSeeErrorReferenceDisplayedOnThePage() throws Exception {
        Path path = new PathBuilder(BadPresenter.class).build();

        MockHttpServletResponse response = servlet.process(new MockHttpServletRequest("GET", path.getUri()));

        String redirectedUrl = response.getRedirectedUrl();
        assertThat(redirectedUrl, matchesPattern(sequence("/error/", exactly(7, anyCharacterIn("A-Z0-9")))));

        String errorRef = StringUtils.substringAfterLast(redirectedUrl, "/");

        response = servlet.process(new MockHttpServletRequest("GET", redirectedUrl));

        String html = response.getContentAsString();
        XPathAssert xpath = new XPathAssert(html);

        xpath.matchesText("count(//span[@id='errorRef'])", is("1"));
        xpath.matchesText("//span[@id='errorRef']", is(errorRef));
    }
}
