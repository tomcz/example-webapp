package example.utils;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class XPathAssert {

    private final String xml;
    private final XPathParser parser;

    public XPathAssert(String xml) {
        this.parser = new XPathParser(xml);
        this.xml = xml;
    }

    public void matchesText(String xpathExpression, Matcher<String> expectedValue) {
        matchesText(xpathExpression, Hamcrest.convert(expectedValue));
    }

    public void matchesText(String xpathExpression, org.hamcrest.Matcher<String> expectedValue) {
        String actualValue = parser.getText(xpathExpression);
        if (!expectedValue.matches(actualValue)) {
            fail(xpathExpression, expectedValue, actualValue);
        }
    }

    public void matchesElement(String xpathExpression, Matcher<Element> expectedValue) {
        matchesElement(xpathExpression, Hamcrest.convert(expectedValue));
    }

    public void matchesElement(String xpathExpression, org.hamcrest.Matcher<Element> expectedValue) {
        Element element = parser.findElement(xpathExpression);
        if (!expectedValue.matches(element)) {
            fail(xpathExpression, expectedValue, toString(element));
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

    public String toString(Element element) {
        XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat());
        return outputter.outputString(element);
    }
}
