package example.spring.template;

import example.utils.Strings;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;

public enum WebFormat {

    NONE, HTML, XML, URL, JS;

    public static WebFormat fromName(String name) {
        try {
            return WebFormat.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return WebFormat.NONE;
        }
    }

    public String format(Object obj) {
        String text = ObjectUtils.toString(obj);
        switch (this) {
            case HTML:
                return HtmlUtils.htmlEscape(text);
            case XML:
                return StringEscapeUtils.escapeXml(text);
            case URL:
                return Strings.encodeURL(text);
            case JS:
                return StringEscapeUtils.escapeJavaScript(text);
            default:
                return text;
        }
    }
}
