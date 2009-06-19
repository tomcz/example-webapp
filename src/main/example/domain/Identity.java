package example.domain;

import example.utils.Strings;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public final class Identity implements Serializable {

    public static final Identity NEW = new Identity("new");

    private final String value;

    public Identity() {
        value = Strings.random();
    }

    private Identity(String value) {
        this.value = value;
    }

    public static Identity fromValue(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("Cannot create Identity from empty value");
        }
        if (NEW.getValue().equalsIgnoreCase(value)) {
            return NEW;
        }
        return new Identity(value);
    }

    public boolean isNew() {
        return NEW.equals(this);
    }

    public String getValue() {
        return value;
    }

    public int hashCode() {
        return value.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Identity) {
            Identity other = (Identity) obj;
            return this.value.equals(other.value);
        }
        return false;
    }

    public String toString() {
        return getValue();
    }
}
