package mx.uaemex.fi.isii.api_backend.domain.dto.response;

/**
 * DTO for payroll calculation response
 */
public class CalcularNominaResponse {
    private Boolean success;
    private String message;

    // Datos del empleado
    private String empleadoNombre;
    private String empleadoApellidos;
    private String empleadoRFC;
    private String empleadoCorreo;

    // Datos de la nómina
    private Double montoNomina;
    private Double isr;
    private Double deducciones;
    private Double nominaNeto;

    public CalcularNominaResponse() {
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

    public Double getIsr() {
        return isr;
    }

    public void setIsr(Double isr) {
        this.isr = isr;
    }

    public Double getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(Double deducciones) {
        this.deducciones = deducciones;
    }

    public Double getNominaNeto() {
        return nominaNeto;
    }

    public void setNominaNeto(Double nominaNeto) {
        this.nominaNeto = nominaNeto;
    }
}