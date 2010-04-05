package example.domain.web;

import example.domain.DocumentRepository;
import example.domain.Identity;
import example.spring.Path;
import example.spring.PathBuilder;
import example.utils.Converter;
import example.utils.Lists;
import example.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexPresenter {

    private final DocumentRepository repository;

    @Autowired
    public IndexPresenter(DocumentRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/forms", method = RequestMethod.GET)
    public ModelAndView present() {
        List<Identity> identities = repository.getIDs();
        PathBuilder builder = new PathBuilder(FormController.class);

        ModelAndView mv = new ModelAndView("example/index");
        mv.addObject("newForm", builder.withVar("documentId", Identity.NEW).build());
        mv.addObject("mappings", createMappings(identities, builder));
        return mv;
    }

    private List<Pair<Identity, Path>> createMappings(List<Identity> identities, final PathBuilder builder) {
        return Lists.map(identities, new Converter<Identity, Pair<Identity, Path>>() {
            public Pair<Identity, Path> convert(Identity item) {
                Path path = builder.withVar("documentId", item).build();
                return Pair.create(item, path);
            }
        });
    }
}
