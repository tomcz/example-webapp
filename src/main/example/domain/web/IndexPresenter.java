package example.domain.web;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;
import example.domain.DocumentDetails;
import example.domain.DocumentRepository;
import example.domain.Identity;
import example.spring.Path;
import example.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static example.spring.PathBuilder.pathTo;

@Controller
public class IndexPresenter {

    private final DocumentRepository repository;

    @Autowired
    public IndexPresenter(DocumentRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/forms", method = RequestMethod.GET)
    public ModelAndView present() {
        ModelAndView mv = new ModelAndView("example/index");
        mv.addObject("mappings", createMappings(repository.getDetails()));
        mv.addObject("newForm", pathTo(FormController.class).withVar("documentId", Identity.NEW).build());
        return mv;
    }

    private List<Pair<DocumentDetails, Path>> createMappings(List<DocumentDetails> details) {
        return Lambda.convert(details, new Converter<DocumentDetails, Pair<DocumentDetails, Path>>() {
            public Pair<DocumentDetails, Path> convert(DocumentDetails item) {
                Path path = pathTo(FormController.class).withVar("documentId", item.getId()).build();
                return Pair.create(item, path);
            }
        });
    }
}
