package example.utils;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;

public class XPathAssert {

    private final String xml;
    private final XPathParser parser;

    public XPathAssert(String xml) {
        this.parser = new XPathParser(xml);
        this.xml = xml;
    }

    public void matches(String xpathExpression, Matcher<String> expectedValue) {
        matches(xpathExpression, Hamcrest.convert(expectedValue));
    }

    public void matches(String xpathExpression, org.hamcrest.Matcher<String> expectedValue) {
        String actualValue = parser.getText(xpathExpression);
        if (!expectedValue.matches(actualValue)) {
            fail(xpathExpression, expectedValue, actualValue);
        }
    }

    public void fail(String xpathExpression, org.hamcrest.Matcher expectedValue, String actualValue) {
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
