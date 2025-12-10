package mx.uaemex.fi.isii.api_backend.domain.dto.request;

/**
 * DTO for registration request from API
 */
public class RegisterRequest {
    private String nombre;
    private String apellidos;
    private String rfc;
    private String correo;
    private Boolean esAdministrador;
    private String password;

    public RegisterRequest() {
    }

    public RegisterRequest(String nombre, String apellidos, String rfc,
                           String correo, Boolean esAdministrador, String password) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.rfc = rfc;
        this.correo = correo;
        this.esAdministrador = esAdministrador;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Boolean getEsAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(Boolean esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}