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

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public void setContentBytes(byte[] contentBytes) {
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
