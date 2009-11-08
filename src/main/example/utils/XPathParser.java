package example.utils;

import org.apache.commons.lang.UnhandledException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class XPathParser {

    private final Document document;

    public XPathParser(String xml) {
        try {
            SAXBuilder builder = new SAXBuilder();
            this.document = builder.build(new StringReader(xml));

        } catch (JDOMException e) {
            throw new UnhandledException(e);

        } catch (IOException e) {
            throw new UnhandledException(e);
        }
    }

    public Document getDocument() {
        return document;
    }

    @SuppressWarnings({"unchecked"})
    public List<Element> findElements(String pattern) {
        try {
            return XPath.selectNodes(document, pattern);
        } catch (JDOMException e) {
            throw new UnhandledException(e);
        }
    }

    public Element findElement(String pattern) {
        try {
            return (Element) XPath.selectSingleNode(document, pattern);
        } catch (JDOMException e) {
            throw new UnhandledException(e);
        }
    }

    public String getText(String pattern) {
        try {
            return (String) XPath.selectSingleNode(document, "string(" + pattern + ")");
        } catch (JDOMException e) {
            throw new UnhandledException(e);
        }
    }
}
