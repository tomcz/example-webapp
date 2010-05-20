package example.domain.web;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;
import example.domain.DocumentDetails;
import example.domain.DocumentRepository;
import example.domain.Identity;
import example.spring.Path;
import example.spring.template.TemplateView;
import example.spring.template.TemplateViewFactory;
import example.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import java.util.List;

import static example.spring.PathBuilder.pathTo;

@Controller
public class IndexPresenter {

    private final DocumentRepository repository;
    private final TemplateViewFactory factory;

    @Autowired
    public IndexPresenter(DocumentRepository repository, TemplateViewFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @RequestMapping(value = "/forms", method = RequestMethod.GET)
    public View present() {
        TemplateView view = factory.create("example", "index");
        view.set("mappings", createMappings(repository.getDetails()));
        view.set("newForm", pathTo(FormController.class).withVar("documentId", Identity.NEW).build());
        return view;
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
