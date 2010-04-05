package example.domain;

import example.utils.Lists;
import example.utils.Maps;
import org.hamcrest.Matcher;
import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

public class Document {

    public enum Field {
        one, two, date
    }

    private Identity identity;
    private Map<Field, Property> properties;

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public Document() {
        this(new Identity());
    }

    public Document(Identity identity) {
        this.identity = identity;
        this.properties = Maps.create();
        this.createdDateTime = new LocalDateTime();
        this.updatedDateTime = new LocalDateTime();
    }

    public Identity getIdentity() {
        return identity;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public Property get(Field field) {
        Property property = properties.get(field);
        return (property != null) ? property : new Property();
    }

    public void set(Field field, Property property) {
        updatedDateTime = new LocalDateTime();
        properties.put(field, property);
    }

    public List<Field> getFields() {
        return Arrays.asList(Field.values());
    }

    public boolean isValid() {
        return Lists.containsOnly(properties.values(), validProperties());
    }

    private Matcher<Property> validProperties() {
        return hasProperty("valid", equalTo(true));
    }
}
