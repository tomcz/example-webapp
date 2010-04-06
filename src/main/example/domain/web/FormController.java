package example.domain.web;

import example.domain.Document;
import example.domain.DocumentRepository;
import example.domain.DocumentValidator;
import example.domain.Identity;
import example.spring.PathBuilder;
import example.spring.template.TemplateView;
import example.spring.template.TemplateViewFactory;
import example.spring.view.RedirectBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.View;

import static example.domain.web.DocumentUtils.createDocumentModel;
import static example.domain.web.DocumentUtils.setProperties;

@Controller
@RequestMapping("/form/{documentId}")
public class FormController {

    private final TemplateViewFactory factory;
    private final DocumentRepository repository;
    private final DocumentValidator validator;

    @Autowired
    public FormController(TemplateViewFactory factory, DocumentRepository repository, DocumentValidator validator) {
        this.repository = repository;
        this.validator = validator;
        this.factory = factory;
    }

    @RequestMapping(method = RequestMethod.GET)
    public View present(@PathVariable Identity documentId) {
        TemplateView view = factory.create("example", "form");
        view.set("indexLink", new PathBuilder(IndexPresenter.class).build());
        view.set("document", createDocumentModel(repository.get(documentId)));
        return view;
    }

    @RequestMapping(method = RequestMethod.POST)
    public View process(@PathVariable Identity documentId, WebRequest request) {
        Document document = repository.get(documentId);

        setProperties(request, document);
        validator.validate(document);
        repository.set(document);

        if (document.isValid()) {
            return new RedirectBuilder(SuccessPresenter.class).withVar("documentId", documentId).build();
        }
        return new RedirectBuilder(getClass()).withVar("documentId", documentId).build();
    }
}
