package example.spring;

import com.google.common.collect.Sets;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;
import static org.springframework.util.StringUtils.arrayToCommaDelimitedString;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

@Controller
@RequestMapping("/endpoints")
public class EndpointsPresenter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<String> display() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<String>(getMappings(), headers, HttpStatus.OK);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private String getMappings() {
        Set<String> mappings = Sets.newTreeSet();
        Map mapOfHandlers = getMapOfHandlers();
        for (Object handler : mapOfHandlers.values()) {
            if (handler instanceof Class) {
                append(mappings, (Class) handler);
            } else {
                append(mappings, handler.getClass());
            }
        }
        return collectionToDelimitedString(mappings, "\n");
    }

    private Map getMapOfHandlers() {
        AbstractUrlHandlerMapping mapping = applicationContext.getBean(AbstractUrlHandlerMapping.class);
        return mapping.getHandlerMap();
    }

    private void append(Set<String> mappings, Class handler) {
        String prefix = "";
        RequestMapping mapping = findAnnotation(handler, RequestMapping.class);
        if (mapping != null) {
            prefix = getFirstPath(mapping);
        }
        for (Method classMethod : handler.getMethods()) {
            mapping = findAnnotation(classMethod, RequestMapping.class);
            if (mapping != null) {
                mappings.add(path(prefix, mapping));
            }
        }
    }

    private String path(String prefix, RequestMapping mapping) {
        StringBuilder buf = new StringBuilder();
        buf.append(prefix);
        buf.append(getFirstPath(mapping));
        buf.append(' ');
        buf.append(arrayToCommaDelimitedString(mapping.method()));
        return buf.toString();
    }

    private String getFirstPath(RequestMapping mapping) {
        String[] paths = mapping.value();
        return (paths.length > 0) ? paths[0] : "";
    }
}
