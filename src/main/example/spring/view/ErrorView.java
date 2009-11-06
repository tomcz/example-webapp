package example.spring.view;

import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ErrorView implements View {

    private final int errorCode;
    private final String message;

    public ErrorView(int errorCode) {
        this(errorCode, null);
    }

    public ErrorView(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getContentType() {
        return null;
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (message != null) {
            response.sendError(errorCode, message);
        } else {
            response.sendError(errorCode);
        }
    }
}
