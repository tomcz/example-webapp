package example.domain.binding;

import example.domain.Identity;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import java.beans.PropertyEditor;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IdentityBindingInititalizerTests {

    @Test
    public void shouldBindNewIdentityForGetRequestsWhenTextValueIsNew() throws Exception {
        WebDataBinder binder = new WebDataBinder(new Object());
        NativeWebRequest request = new ServletWebRequest(new MockHttpServletRequest("GET", "/foo.go"));

        IdentityBindingInititalizer initialiser = new IdentityBindingInititalizer();
        initialiser.initBinder(binder, request);

        PropertyEditor customEditor = binder.findCustomEditor(Identity.class, "");
        assertThat(customEditor, instanceOf(IdentityPropertyEditor.class));

        customEditor.setAsText("new");
        Identity value = (Identity) customEditor.getValue();
        assertThat(value.isNew(), is(true));
    }

    @Test
    public void shouldBindGeneratedIdentityForPostRequestsWhenTextValueIsNew() throws Exception {
        WebDataBinder binder = new WebDataBinder(new Object());
        NativeWebRequest request = new ServletWebRequest(new MockHttpServletRequest("POST", "/foo.go"));

        IdentityBindingInititalizer initialiser = new IdentityBindingInititalizer();
        initialiser.initBinder(binder, request);

        PropertyEditor customEditor = binder.findCustomEditor(Identity.class, "");
        assertThat(customEditor, instanceOf(IdentityPropertyEditor.class));

        customEditor.setAsText("new");
        Identity value = (Identity) customEditor.getValue();
        assertThat(value.isNew(), is(false));
    }
}
