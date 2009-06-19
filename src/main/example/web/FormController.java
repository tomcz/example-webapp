package example.web;

import example.domain.Document;
import example.domain.DocumentRepository;
import example.domain.DocumentValidator;
import example.domain.Identity;
import example.spring.path.PathBuilder;
import example.spring.template.TemplateView;
import example.spring.template.TemplateViewFactory;
import static example.web.DocumentUtils.createDocumentModel;
import static example.web.DocumentUtils.setProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/form/{documentId}")
public class FormController {

    private final DocumentRepository repository;
    private final DocumentValidator validator;
    private final TemplateViewFactory factory;

    @Autowired
    public FormController(DocumentRepository repository, DocumentValidator validator, TemplateViewFactory factory) {
        this.repository = repository;
        this.validator = validator;
        this.factory = factory;
    }

    @RequestMapping(method = RequestMethod.GET)
    public View present(@PathVariable Identity documentId, PathBuilder builder) {
        TemplateView template = factory.create("example", "form");
        template.set("indexLink", builder.httpGet(IndexPresenter.class));
        template.set("formAction", builder.httpPost(getClass(), "documentId", documentId));
        template.set("document", createDocumentModel(repository.get(documentId)));
        return template;
    }

    @RequestMapping(method = RequestMethod.POST)
    public RedirectView process(@PathVariable Identity documentId, WebRequest request, PathBuilder builder) {
        Document document = repository.get(documentId);

        setProperties(request, document);
        validator.validate(document);
        repository.set(document);

        if (document.isValid()) {
            return builder.redirectTo(SuccessPresenter.class, "documentId", documentId);
        }
        return builder.redirectTo(getClass(), "documentId", documentId);
    }
}
