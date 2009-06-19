package example.web;

import example.domain.Property;

public class PropertyWrapper {

    private String name;
    private Property property;

    public PropertyWrapper(String name, Property property) {
        this.property = property;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return property.getValue();
    }

    public String getMessage() {
        return property.getMessage();
    }

    public boolean isValid() {
        return property.isValid();
    }
}
