package mx.uaemex.fi.isii.api_backend.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Class for cookie's basic management, create and delete methods
 */
@Component
public class CookieManager {

    /**
     * Method for creating a cookie
     * @param name Cookie's name
     * @param value Cookie's value
     * @return cookie
     */
    public Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(1000);
        cookie.setHttpOnly(true);
        return cookie;
    }

    /**
     * Basic empty constructor
     */
    public CookieManager(){
    }

    /**
     * Method for creating a cookie
     * @param cookie for accessing to the cookie
     * @param response to handle and send the cookie's changes
     *
     */
    public void deleteCookie(Cookie cookie, HttpServletResponse response) {
        cookie.setValue("");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * Method for generating a secure token
     * @return string secure token
     */
    public String generateSecureToken() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

}
