package example.utils;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;

public class XPathMatcher {

    private final XPathParser parser;
    private final String xml;

    public XPathMatcher(String xml) {
        this.parser = new XPathParser(xml);
        this.xml = xml;
    }

    public void shouldMatch(String xpathExpression, Matcher<String> expectedValue) {
        shouldMatch(xpathExpression, Hamcrest.convert(expectedValue));
    }

    public void shouldMatch(String xpathExpression, org.hamcrest.Matcher<String> expectedValue) {
        String actualValue = parser.getText(xpathExpression);
        if (!expectedValue.matches(actualValue)) {
            Description description = new StringDescription();
            description.appendText(xpathExpression);
            description.appendText("\nExpected: ");
            expectedValue.describeTo(description);
            description.appendText("\n     got: ");
            description.appendValue(actualValue);
            description.appendText("\n");
            description.appendText(xml);
            throw new AssertionError(description.toString());
        }
    }
}
