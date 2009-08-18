package example.web;

import example.domain.DocumentRepository;
import example.domain.Identity;
import example.spring.path.Path;
import example.spring.path.PathBuilder;
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
    public View present(final PathBuilder builder) {
        List<Identity> identities = repository.getIDs();
        TemplateView template = factory.create("example", "index");
        template.set("newForm", builder.httpGet(FormController.class, "documentId", Identity.NEW));
        template.set("mappings", createMappings(builder, identities));
        return template;
    }

    private List<Pair<Identity, Path>> createMappings(final PathBuilder builder, List<Identity> identities) {
        return Lists.map(identities, new Function<Identity, Pair<Identity, Path>>() {
            public Pair<Identity, Path> execute(Identity item) {
                Path path = builder.httpGet(FormController.class, "documentId", item);
                return Pair.create(item, path);
            }
        });
    }
}
