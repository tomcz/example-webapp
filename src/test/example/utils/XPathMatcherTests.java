package example.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.internal.matchers.StringContains.containsString;

public class XPathMatcherTests {

    @Test
    public void shouldNotFailWhenXPathMatches() {
        String xml = "<errors><error>foo</error></errors>";
        XPathMatcher matcher = new XPathMatcher(xml);
        matcher.shouldMatch("//error", is("foo"));
    }

    @Test
    public void shouldFailWhenXPathDoesNotMatch() {
        String xml = "<errors><error>foo</error></errors>";
        XPathMatcher matcher = new XPathMatcher(xml);
        boolean failed = false;
        try {
            matcher.shouldMatch("//error", is("boo"));
        } catch (AssertionError e) {
            failed = true;
        }
        assertTrue("Should have failed", failed);
    }

    @Test
    public void shouldCreateErrorMessageWithExpectedActualAndXML() {
        String xml = "<errors><error>foo</error></errors>";
        XPathMatcher matcher = new XPathMatcher(xml);
        boolean failed = false;
        try {
            matcher.shouldMatch("//error", is("boo"));
        } catch (AssertionError e) {
            assertThat(e.getMessage(), containsString("\"foo\""));
            assertThat(e.getMessage(), containsString("\"boo\""));
            assertThat(e.getMessage(), containsString(xml));
            failed = true;
        }
        assertTrue("Should have failed", failed);
    }
}
