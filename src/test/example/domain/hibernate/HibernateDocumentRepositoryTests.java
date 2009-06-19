package example.domain.hibernate;

import example.domain.Document;
import example.domain.Identity;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.orm.hibernate3.HibernateOperations;

public class HibernateDocumentRepositoryTests {

    @Test
    public void shouldReturnFoundDocument() throws Exception {
        Identity id = new Identity();
        Document doc = new Document(id);

        HibernateOperations hibernate = mock(HibernateOperations.class);
        when(hibernate.get(Document.class, id)).thenReturn(doc);

        HibernateDocumentRepository repository = new HibernateDocumentRepository(hibernate);

        assertThat(repository.get(id), sameInstance(doc));
    }

    @Test
    public void shouldCreateDocumentWhenCannotFindDocumentInDatabase() throws Exception {
        Identity id = new Identity();

        HibernateDocumentRepository repository = new HibernateDocumentRepository(mock(HibernateOperations.class));
        Document document = repository.get(id);

        assertThat(document, notNullValue());
        assertThat(document.getIdentity(), is(id));
    }
}
