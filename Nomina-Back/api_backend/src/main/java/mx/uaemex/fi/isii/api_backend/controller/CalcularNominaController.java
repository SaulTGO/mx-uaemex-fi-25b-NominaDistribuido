package mx.uaemex.fi.isii.api_backend.controller;

import mx.uaemex.fi.isii.api_backend.domain.POJO.Nomina;
import mx.uaemex.fi.isii.api_backend.domain.dto.request.CalcularNominaRequest;
import mx.uaemex.fi.isii.api_backend.domain.dto.response.CalcularNominaResponse;
import mx.uaemex.fi.isii.api_backend.domain.dto.response.EmpleadosResponse;
import mx.uaemex.fi.isii.api_backend.domain.entity.Empleado;
import mx.uaemex.fi.isii.api_backend.services.CalcularNominaService;
import mx.uaemex.fi.isii.api_backend.services.EmpleadoRepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController for payroll calculation
 */
@RestController
@RequestMapping("user/nomina")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true", allowedHeaders = "*")
public class CalcularNominaController {

    private final CalcularNominaService calcularNominaService;
    private final EmpleadoRepositoryService empleadoRepositoryService;

    public CalcularNominaController(CalcularNominaService calcularNominaService,
                                    EmpleadoRepositoryService empleadoRepositoryService) {
        this.calcularNominaService = calcularNominaService;
        this.empleadoRepositoryService = empleadoRepositoryService;
    }

    /**
     * GET endpoint to retrieve all employees
     * @return List of all employees
     */
    @GetMapping("/empleados")
    public ResponseEntity<EmpleadosResponse> obtenerEmpleados() {
        try {
            List<Empleado> empleados = empleadoRepositoryService.findAllEmpleados();

            EmpleadosResponse response = new EmpleadosResponse();
            response.setSuccess(true);
            response.setEmpleados(empleados);
            response.setMessage("Empleados obtenidos correctamente");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            EmpleadosResponse errorResponse = new EmpleadosResponse();
            errorResponse.setSuccess(false);
            errorResponse.setMessage("Error al obtener empleados: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * POST endpoint to calculate payroll for an employee
     * @param request CalcularNominaRequest with employee data and salary amount
     * @return CalcularNominaResponse with calculated values
     */
    @PostMapping("/calcular")
    public ResponseEntity<CalcularNominaResponse> calcularNomina(
            @RequestBody CalcularNominaRequest request) {

        // Validar que el monto de nómina sea válido
        if (request.getMontoNomina() == null || request.getMontoNomina() <= 0) {
            CalcularNominaResponse errorResponse = new CalcularNominaResponse();
            errorResponse.setSuccess(false);
            errorResponse.setMessage("El monto de nómina debe ser mayor a 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Validar que se hayan proporcionado los datos del empleado
        if (request.getEmpleadoRFC() == null || request.getEmpleadoRFC().trim().isEmpty()) {
            CalcularNominaResponse errorResponse = new CalcularNominaResponse();
            errorResponse.setSuccess(false);
            errorResponse.setMessage("El RFC del empleado es obligatorio");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
            // Calcular la nómina
            Nomina nominaDatos = calcularNominaService.calcularNomina(request.getMontoNomina());

            // Crear respuesta exitosa
            CalcularNominaResponse response = new CalcularNominaResponse();
            response.setSuccess(true);
            response.setMessage("Nómina calculada correctamente");

            // Datos del empleado
            response.setEmpleadoNombre(request.getEmpleadoNombre());
            response.setEmpleadoApellidos(request.getEmpleadoApellidos());
            response.setEmpleadoRFC(request.getEmpleadoRFC());
            response.setEmpleadoCorreo(request.getEmpleadoCorreo());

            // Datos de la nómina calculada
            response.setMontoNomina(nominaDatos.getMontoNomina());
            response.setIsr(nominaDatos.getIsr());
            response.setDeducciones(nominaDatos.getDeducciones());
            response.setNominaNeto(nominaDatos.getNominaNeto());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            CalcularNominaResponse errorResponse = new CalcularNominaResponse();
            errorResponse.setSuccess(false);
            errorResponse.setMessage("Error al calcular nómina: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}