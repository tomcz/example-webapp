package example.domain.web;

import example.domain.Document;
import example.domain.DocumentRepository;
import example.domain.DocumentValidator;
import example.domain.Identity;
import example.spring.template.TemplateView;
import example.spring.template.TemplateViewFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.View;

import static example.domain.web.DocumentUtils.createDocumentModel;
import static example.domain.web.DocumentUtils.setProperties;
import static example.spring.PathBuilder.pathTo;
import static example.spring.view.RedirectBuilder.redirectTo;

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
        view.set("indexLink", pathTo(IndexPresenter.class).build());
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
            return redirectTo(SuccessPresenter.class).withVar("documentId", documentId).build();
        }
        return redirectTo(getClass()).withVar("documentId", documentId).build();
    }
}
