package example.utils;

import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jdom.Element;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.internal.matchers.StringContains.containsString;

public class XPathAssertTests {

    @Test
    public void shouldNotFailWhenXPathMatches() {
        String xml = "<errors><error>foo</error></errors>";
        XPathAssert matcher = new XPathAssert(xml);
        matcher.matchesText("//error", is("foo"));
    }

    @Test
    public void shouldFailWhenXPathDoesNotMatch() {
        String xml = "<errors><error>foo</error></errors>";
        XPathAssert matcher = new XPathAssert(xml);
        boolean failed = false;
        try {
            matcher.matchesText("//error", is("boo"));
        } catch (AssertionError e) {
            failed = true;
        }
        assertTrue("Should have failed", failed);
    }

    @Test
    public void shouldCreateErrorMessageWithExpectedActualAndXML() {
        String xml = "<errors><error>foo</error></errors>";
        XPathAssert matcher = new XPathAssert(xml);
        boolean failed = false;
        try {
            matcher.matchesText("//error", is("boo"));
        } catch (AssertionError e) {
            assertThat(e.getMessage(), containsString("\"foo\""));
            assertThat(e.getMessage(), containsString("\"boo\""));
            assertThat(e.getMessage(), containsString(xml));
            failed = true;
        }
        assertTrue("Should have failed", failed);
    }

    @Test
    public void shouldNotFailWhenElementMatches() {
        String xml = "<errors><error>foo</error></errors>";
        XPathAssert matcher = new XPathAssert(xml);
        matcher.matchesElement("//error", new ElementValueMatcher("foo"));
    }

    @Test
    public void shouldFailWhenXmlDoesNotContainElement() {
        String xml = "<errors><error>foo</error></errors>";
        XPathAssert matcher = new XPathAssert(xml);
        boolean failed = false;
        try {
            matcher.matchesElement("//error", new ElementValueMatcher("boo"));
        } catch (AssertionError e) {
            failed = true;
        }
        assertTrue("Should have failed", failed);
    }

    private static class ElementValueMatcher extends TypeSafeMatcher<Element> {

        private final String value;

        public ElementValueMatcher(String value) {
            this.value = value;
        }

        @Override
        public boolean matchesSafely(Element element) {
            return value.equals(element.getText());
        }

        public void describeTo(Description description) {
            description.appendText("Element with text '" + value + "'");
        }
    }
}
