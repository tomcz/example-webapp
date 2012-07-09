package example.utils;

import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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
}
