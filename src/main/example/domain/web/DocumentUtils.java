package example.domain.web;

import example.domain.Document;
import example.domain.Document.Field;
import example.domain.Property;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

public class DocumentUtils {

    public static void setProperties(WebRequest request, Document document) {
        for (Field field : document.getFields()) {
            String value = request.getParameter(field.name());
            document.set(field, new Property(value));
        }
    }

    public static Map<String, PropertyWrapper> createDocumentModel(Document document) {
        Map<String, PropertyWrapper> model = new HashMap<String, PropertyWrapper>();
        for (Field field : document.getFields()) {
            model.put(field.name(), new PropertyWrapper(field.name(), document.get(field)));
        }
        return model;
    }
}
