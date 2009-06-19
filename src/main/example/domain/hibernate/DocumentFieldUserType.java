package example.domain.hibernate;

import example.domain.Document;

public class DocumentFieldUserType extends EnumUserType {

    public DocumentFieldUserType() {
        super(Document.Field.class);
    }
}
