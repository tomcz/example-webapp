package example.domain;

import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static org.hamcrest.Matchers.equalTo;

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
        this.properties = new HashMap<Field, Property>();
        this.createdDateTime = new LocalDateTime();
        this.updatedDateTime = new LocalDateTime();
        this.identity = identity;
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
        return select(properties.values(), having(on(Property.class).isValid(), equalTo(false))).isEmpty();
    }
}
