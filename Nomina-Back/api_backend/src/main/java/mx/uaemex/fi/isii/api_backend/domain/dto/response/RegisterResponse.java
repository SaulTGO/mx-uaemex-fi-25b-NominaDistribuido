package mx.uaemex.fi.isii.api_backend.domain.dto.response;

/**
 * DTO for registration response from API
 */
public class RegisterResponse {
    private Boolean success;
    private String message;
    private String userId;

    public RegisterResponse() {
    }

    public RegisterResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public RegisterResponse(Boolean success, String message, String userId) {
        this.success = success;
        this.message = message;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}