package mx.uaemex.fi.isii.frontend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaemex.fi.isii.frontend.domain.dto.request.SessionRequest;
import mx.uaemex.fi.isii.frontend.domain.dto.response.SessionResponse;
import mx.uaemex.fi.isii.frontend.util.CookieManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for manage and create cookie sessions
 */
@RestController
public class SessionController {

    private final CookieManager cookieManager;

    public SessionController(CookieManager cookieManager) {
        this.cookieManager = cookieManager;
    }

    /**
     * Handles the post request for creating a cookie
     * @param request contains username and token
     * @param response HTTP response to add the cookie
     * @return SessionResponse with success status
     */
    @PostMapping("/create-cookie")
    public ResponseEntity<SessionResponse> createCookie(
            @RequestBody SessionRequest request,
            HttpServletResponse response) {

        try {
            String secureToken = cookieManager.generateSecureToken();
            Cookie cookie = cookieManager.createCookie("AUTH_USER", secureToken);

            response.addCookie(cookie);

            SessionResponse sessionResponse = new SessionResponse();
            sessionResponse.setSuccess(true);
            sessionResponse.setMessage("Sesión creada exitosamente");

            return ResponseEntity.ok(sessionResponse);

        } catch (Exception e) {
            SessionResponse sessionResponse = new SessionResponse();
            sessionResponse.setSuccess(false);
            sessionResponse.setMessage("Error al crear la sesión: " + e.getMessage());

            return ResponseEntity.internalServerError().body(sessionResponse);
        }
    }

    /**
     * Handles the post request for logout (delete cookie)
     * @param request HTTP request to access cookies
     * @param response HTTP response to delete the cookie
     * @return SessionResponse with success status
     */
    @PostMapping("/logout")
    public ResponseEntity<SessionResponse> logout(
            jakarta.servlet.http.HttpServletRequest request,
            HttpServletResponse response) {

        try {
            Cookie[] cookies = request.getCookies();
            Cookie authCookie = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("AUTH_USER".equals(cookie.getName())) {
                        authCookie = cookie;
                        break;
                    }
                }
            }

            if (authCookie != null) {
                cookieManager.deleteCookie(authCookie, response);
            }

            SessionResponse sessionResponse = new SessionResponse();
            sessionResponse.setSuccess(true);
            sessionResponse.setMessage("Sesión cerrada exitosamente");

            return ResponseEntity.ok(sessionResponse);

        } catch (Exception e) {
            SessionResponse sessionResponse = new SessionResponse();
            sessionResponse.setSuccess(false);
            sessionResponse.setMessage("Error al cerrar sesión: " + e.getMessage());

            return ResponseEntity.internalServerError().body(sessionResponse);
        }
    }
}