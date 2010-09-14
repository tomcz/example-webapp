package example.domain.web;

import example.domain.Document;
import example.domain.DocumentRepository;
import example.domain.DocumentValidator;
import example.domain.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import static example.domain.web.DocumentUtils.createDocumentModel;
import static example.domain.web.DocumentUtils.setProperties;
import static example.spring.PathBuilder.pathTo;
import static example.spring.view.RedirectBuilder.redirectTo;

@Controller
@RequestMapping("/form/{documentId}")
public class FormController {

    private final DocumentRepository repository;
    private final DocumentValidator validator;

    @Autowired
    public FormController(DocumentRepository repository, DocumentValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView present(@PathVariable Identity documentId) {
        ModelAndView mv = new ModelAndView("example/form");
        mv.addObject("indexLink", pathTo(IndexPresenter.class).build());
        mv.addObject("document", createDocumentModel(repository.get(documentId)));
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public RedirectView process(@PathVariable Identity documentId, WebRequest request) {
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
