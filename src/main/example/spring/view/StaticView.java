package example.spring.view;

import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class StaticView implements View {

    private String contentType;
    private String contentText;
    private byte[] contentBytes;

    public StaticView() {
    }

    public StaticView(String contentType, String contentText) {
        this.contentType = contentType;
        this.contentText = contentText;
    }

    public StaticView(String contentType, byte[] contentBytes) {
        this.contentType = contentType;
        this.contentBytes = contentBytes;
    }

    public String getContentType() {
        return contentType;
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (contentType != null) {
            response.setContentType(contentType);
        }
        if (contentText != null) {
            response.setContentLength(contentText.length());
            response.getWriter().print(contentText);
        }
        if (contentBytes != null) {
            response.setContentLength(contentBytes.length);
            IOUtils.write(contentBytes, response.getOutputStream());
        }
    }
}
