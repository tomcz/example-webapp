package example.web;

import example.domain.DocumentRepository;
import example.domain.Identity;
import example.spring.path.PathBuilder;
import example.spring.template.TemplateView;
import example.spring.template.TemplateViewFactory;
import static example.web.DocumentUtils.createDocumentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

@Controller
public class SuccessPresenter {

    private final DocumentRepository repository;
    private final TemplateViewFactory factory;

    @Autowired
    public SuccessPresenter(DocumentRepository repository, TemplateViewFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @RequestMapping(value = "/success/{documentId}", method = RequestMethod.GET)
    public View present(@PathVariable Identity documentId, PathBuilder builder) {
        TemplateView template = factory.create("example", "success");
        template.set("document", createDocumentModel(repository.get(documentId)));
        template.set("oldFormLink", builder.httpGet(FormController.class, "documentId", documentId));
        template.set("newFormLink", builder.httpGet(FormController.class, "documentId", Identity.NEW));
        template.set("indexLink", builder.httpGet(IndexPresenter.class));
        return template;
    }
}
