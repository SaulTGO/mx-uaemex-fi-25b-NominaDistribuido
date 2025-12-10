package mx.uaemex.fi.isii.frontend.domain.dto.request;

/**
 * DTO for session creation request
 */
public class SessionRequest {
    private String username;
    private String token;

    public SessionRequest() {
    }

    public SessionRequest(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}