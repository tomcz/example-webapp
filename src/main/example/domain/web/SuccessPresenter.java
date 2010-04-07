package example.domain.web;

import example.domain.DocumentRepository;
import example.domain.Identity;
import example.spring.template.TemplateView;
import example.spring.template.TemplateViewFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import static example.domain.web.DocumentUtils.createDocumentModel;
import static example.spring.PathBuilder.pathTo;

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
    public View present(@PathVariable Identity documentId) {
        TemplateView view = factory.create("example", "success");
        view.set("document", createDocumentModel(repository.get(documentId)));
        view.set("oldFormLink", pathTo(FormController.class).withVar("documentId", documentId).build());
        view.set("newFormLink", pathTo(FormController.class).withVar("documentId", Identity.NEW).build());
        view.set("indexLink", pathTo(IndexPresenter.class).build());
        return view;
    }
}
