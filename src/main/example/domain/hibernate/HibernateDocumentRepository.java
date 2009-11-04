package example.domain.hibernate;

import example.domain.Document;
import example.domain.DocumentRepository;
import example.domain.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HibernateDocumentRepository implements DocumentRepository {

    private final HibernateOperations hibernate;

    @Autowired
    public HibernateDocumentRepository(HibernateOperations hibernate) {
        this.hibernate = hibernate;
    }

    public Document get(Identity documentId) {
        if (documentId.isNew()) {
            return new Document(documentId);
        }
        Document document = hibernate.get(Document.class, documentId);
        if (document == null) {
            document = new Document(documentId);
        }
        return document;
    }

    public void set(Document document) {
        Identity identity = document.getIdentity();
        if (identity.isNew()) {
            throw new IllegalArgumentException("Cannot save document with '" + identity + "' identity");
        }
        hibernate.saveOrUpdate(document);
    }

    @SuppressWarnings({"unchecked"})
    public List<Identity> getIDs() {
        return hibernate.find("select identity from Document");
    }
}
