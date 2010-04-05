package example.utils;

import org.jdom.Element;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class XPathParserTests {

    @Test
    public void shouldFindSingleElement() {
        String xml = "<errors><error>foo</error></errors>";
        XPathParser parser = new XPathParser(xml);
        Element element = parser.findElement("//error");
        assertThat(element, notNullValue());
        assertThat(element.getText(), is("foo"));
    }

    @Test
    public void shouldFindTextOfSingleElement() {
        String xml = "<errors><error>foo</error></errors>";
        XPathParser parser = new XPathParser(xml);
        assertThat(parser.getText("//error"), is("foo"));
    }

    @Test
    public void shouldFindMultipleElements() {
        String xml = "<errors><error>foo</error><error>bar</error></errors>";
        XPathParser parser = new XPathParser(xml);
        List<Element> elements = parser.findElements("//error");
        assertThat(elements.size(), is(2));
        assertThat(elements.get(0).getText(), is("foo"));
        assertThat(elements.get(1).getText(), is("bar"));
    }
}
