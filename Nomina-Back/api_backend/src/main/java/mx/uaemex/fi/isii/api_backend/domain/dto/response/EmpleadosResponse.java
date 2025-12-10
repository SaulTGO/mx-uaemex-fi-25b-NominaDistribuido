package mx.uaemex.fi.isii.api_backend.domain.dto.response;

import mx.uaemex.fi.isii.api_backend.domain.entity.Empleado;
import java.util.List;

/**
 * DTO for employees list response
 */
public class EmpleadosResponse {
    private Boolean success;
    private String message;
    private List<Empleado> empleados;

    public EmpleadosResponse() {
    }

    public EmpleadosResponse(Boolean success, String message, List<Empleado> empleados) {
        this.success = success;
        this.message = message;
        this.empleados = empleados;
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

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
}