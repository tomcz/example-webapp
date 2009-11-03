package example.spring;

import example.utils.Lists;
import org.springframework.web.servlet.View;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class CookieSettingView implements View {

    private final View delegate;
    private final List<Cookie> cookies;

    public CookieSettingView(View delegate) {
        this.cookies = Lists.create();
        this.delegate = delegate;
    }

    public void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        addCookie(cookie);
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public String getContentType() {
        return delegate.getContentType();
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        addCookiesToResponse(response);
        delegate.render(model, request, response);
    }

    private void addCookiesToResponse(HttpServletResponse response) {
        for (Cookie cookie : cookies) {
            response.addCookie(cookie);
        }
    }
}
