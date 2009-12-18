package example.utils;

import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Strings {

    private static StandardToStringStyle style;

    public static ToStringStyle style() {
        if (style == null) {
            style = new StandardToStringStyle();
            style.setUseIdentityHashCode(false);
            style.setUseShortClassName(true);
        }
        return style;
    }

    public static String toString(Object obj) {
        return ToStringBuilder.reflectionToString(obj, style());
    }

    public static String encodeURL(String text) {
        if (text == null) {
            return text;
        }
        try {
            return URLEncoder.encode(text, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            // this should never happen since UTF-8 is a supported encoding
            throw new RuntimeException(e);
        }
    }

    public static String decodeURL(String text) {
        if (text == null) {
            return text;
        }
        try {
            return URLDecoder.decode(text, "UTF-8");

        } catch (IllegalArgumentException e) {
            // We should not die here on bad text
            return text;

        } catch (UnsupportedEncodingException e) {
            // this should never happen since UTF-8 is a supported encoding
            throw new RuntimeException(e);
        }
    }
}
