package example.domain.web;

import example.domain.DocumentRepository;
import example.domain.Identity;
import example.spring.PathBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static example.domain.web.DocumentUtils.createDocumentModel;

@Controller
public class SuccessPresenter {

    private final DocumentRepository repository;

    @Autowired
    public SuccessPresenter(DocumentRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/success/{documentId}", method = RequestMethod.GET)
    public ModelAndView present(@PathVariable Identity documentId) {
        PathBuilder builder = new PathBuilder(FormController.class);

        ModelAndView mv = new ModelAndView("example/success");
        mv.addObject("document", createDocumentModel(repository.get(documentId)));
        mv.addObject("oldFormLink", builder.withVar("documentId", documentId).build());
        mv.addObject("newFormLink", builder.withVar("documentId", Identity.NEW).build());
        mv.addObject("indexLink", new PathBuilder(IndexPresenter.class).build());
        return mv;
    }
}
