package example.domain;

import com.google.common.collect.Maps;
import org.hamcrest.Matcher;
import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static example.utils.PredicateMatcher.with;
import static org.apache.commons.collections.CollectionUtils.countMatches;
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
        this.properties = Maps.newHashMap();
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
        return countMatches(properties.values(), with(validProperties())) == properties.size();
    }

    private Matcher<Property> validProperties() {
        return hasProperty("valid", equalTo(true));
    }
}
