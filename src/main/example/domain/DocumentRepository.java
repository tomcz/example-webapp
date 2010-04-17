package example.domain;

import java.util.List;

public interface DocumentRepository {

    List<DocumentDetails> getDetails();

    Document get(Identity documentId);

    void set(Document document);
}
