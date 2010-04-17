package example.domain.hibernate;

import example.domain.Document;
import example.domain.DocumentDetails;
import example.domain.DocumentRepository;
import example.domain.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings({"unchecked"})
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

    public List<DocumentDetails> getDetails() {
        return hibernate.find("select new example.domain.DocumentDetails(identity, createdDateTime, updatedDateTime)"
                + " from Document order by updatedDateTime desc");
    }
}
