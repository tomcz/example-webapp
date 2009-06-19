package example.domain;

import java.util.List;

public interface DocumentRepository {

    Document get(Identity documentId);

    void set(Document document);

    List<Identity> getIDs();
}
