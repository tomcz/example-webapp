package example.spring.template;

public interface Renderer {

    Class getTypeToRender();

    public String toString(Object o);

    public String toString(Object o, String formatName);
}
