package example.domain.web;

import example.domain.DocumentRepository;
import example.domain.Identity;
import example.spring.Path;
import static example.spring.PathBuilder.httpGet;
import example.spring.template.TemplateView;
import example.spring.template.TemplateViewFactory;
import example.utils.Function;
import example.utils.Lists;
import example.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import java.util.List;

@Controller
public class IndexPresenter {

    private final DocumentRepository repository;
    private final TemplateViewFactory factory;

    @Autowired
    public IndexPresenter(TemplateViewFactory factory, DocumentRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    @RequestMapping(value = "/forms", method = RequestMethod.GET)
    public View present() {
        List<Identity> identities = repository.getIDs();
        TemplateView template = factory.create("example", "index");
        template.set("newForm", httpGet(FormController.class, "documentId", Identity.NEW));
        template.set("mappings", createMappings(identities));
        return template;
    }

    private List<Pair<Identity, Path>> createMappings(List<Identity> identities) {
        return Lists.map(identities, new Function<Identity, Pair<Identity, Path>>() {
            public Pair<Identity, Path> execute(Identity item) {
                Path path = httpGet(FormController.class, "documentId", item);
                return Pair.create(item, path);
            }
        });
    }
}
