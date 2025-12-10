package mx.uaemex.fi.isii.frontend.domain.dto.response;


/**
 * DTO for session creation response
 */
public class SessionResponse {
    private Boolean success;
    private String message;

    public SessionResponse() {
    }

    public SessionResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}