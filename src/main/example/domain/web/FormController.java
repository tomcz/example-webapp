package example.domain.web;

import ch.lambdaj.function.convert.Converter;
import example.domain.Document;
import example.domain.DocumentRepository;
import example.domain.DocumentValidator;
import example.domain.Field;
import example.domain.Identity;
import example.domain.Property;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;

import static ch.lambdaj.Lambda.convert;
import static example.domain.web.DocumentUtils.createDocumentModel;
import static example.domain.web.DocumentUtils.setProperties;
import static example.spring.PathBuilder.pathTo;

@Controller
@RequestMapping("/form/{documentId}")
public class FormController {

    private final DocumentRepository repository;
    private final DocumentValidator validator;

    // for proxy creation
    public FormController() {
        this(null, null);
    }

    @Autowired
    public FormController(DocumentRepository repository, DocumentValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView present(@PathVariable Identity documentId) {
        Document document = repository.get(documentId);
        ModelAndView mv = new ModelAndView("example/form");
        mv.addObject("indexLink", pathTo(IndexPresenter.class).build());
        mv.addObject("document", createDocumentModel(document));
        mv.addObject("fieldOptions", options(document, Field.two));
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public RedirectView process(@PathVariable Identity documentId, WebRequest request) {
        Document document = repository.get(documentId);

        setProperties(request, document);
        validator.validate(document);
        repository.set(document);

        if (document.isValid()) {
            return pathTo(SuccessPresenter.class).withVar("documentId", documentId).redirect();
        }
        return pathTo(getClass()).withVar("documentId", documentId).redirect();
    }

    private List<Option> options(Document document, Field field) {
        final Property property = document.get(field);
        List<String> values = Arrays.asList("", "option1", "option2", "error");
        return convert(values, new Converter<String, Option>() {
            public Option convert(String value) {
                boolean selected = StringUtils.equals(property.getValue(), value);
                return new Option(value, selected);
            }
        });
    }

    public static class Option {

        private final String value;
        private final boolean selected;

        public Option(String value, boolean selected) {
            this.selected = selected;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public boolean isSelected() {
            return selected;
        }
    }
}
