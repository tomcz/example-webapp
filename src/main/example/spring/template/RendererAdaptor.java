package example.spring.template;

import org.antlr.stringtemplate.AttributeRenderer;

public class RendererAdaptor implements AttributeRenderer {

    private final Renderer delegate;

    public RendererAdaptor(Renderer delegate) {
        this.delegate = delegate;
    }

    public String toString(Object o) {
        return delegate.toString(o);
    }

    public String toString(Object o, String formatName) {
        return delegate.toString(o, formatName);
    }
}
