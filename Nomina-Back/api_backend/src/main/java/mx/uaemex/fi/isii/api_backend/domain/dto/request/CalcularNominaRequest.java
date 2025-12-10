package mx.uaemex.fi.isii.api_backend.domain.dto.request;

/**
 * DTO for payroll calculation request
 */
public class CalcularNominaRequest {
    private String empleadoNombre;
    private String empleadoApellidos;
    private String empleadoRFC;
    private String empleadoCorreo;
    private Double montoNomina;

    public CalcularNominaRequest() {
    }

    public CalcularNominaRequest(String empleadoNombre, String empleadoApellidos,
                                 String empleadoRFC, String empleadoCorreo, Double montoNomina) {
        this.empleadoNombre = empleadoNombre;
        this.empleadoApellidos = empleadoApellidos;
        this.empleadoRFC = empleadoRFC;
        this.empleadoCorreo = empleadoCorreo;
        this.montoNomina = montoNomina;
    }

    public String getEmpleadoNombre() {
        return empleadoNombre;
    }

    public void setEmpleadoNombre(String empleadoNombre) {
        this.empleadoNombre = empleadoNombre;
    }

    public String getEmpleadoApellidos() {
        return empleadoApellidos;
    }

    public void setEmpleadoApellidos(String empleadoApellidos) {
        this.empleadoApellidos = empleadoApellidos;
    }

    public String getEmpleadoRFC() {
        return empleadoRFC;
    }

    public void setEmpleadoRFC(String empleadoRFC) {
        this.empleadoRFC = empleadoRFC;
    }

    public String getEmpleadoCorreo() {
        return empleadoCorreo;
    }

    public void setEmpleadoCorreo(String empleadoCorreo) {
        this.empleadoCorreo = empleadoCorreo;
    }

    public Double getMontoNomina() {
        return montoNomina;
    }

    public void setMontoNomina(Double montoNomina) {
        this.montoNomina = montoNomina;
    }
}