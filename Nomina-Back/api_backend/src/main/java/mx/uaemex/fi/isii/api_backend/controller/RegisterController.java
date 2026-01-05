package mx.uaemex.fi.isii.api_backend.controller;

import mx.uaemex.fi.isii.api_backend.domain.dto.request.RegisterRequest;
import mx.uaemex.fi.isii.api_backend.domain.dto.response.RegisterResponse;
import mx.uaemex.fi.isii.api_backend.domain.entity.Empleado;
import mx.uaemex.fi.isii.api_backend.services.RegisterService;
import mx.uaemex.fi.isii.api_backend.util.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * RestController. Handles register process
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true", allowedHeaders = "*")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    /**
     * POST method for user registration
     * Validates input, creates employee and optionally creates admin user
     * @param request RegisterRequest DTO with user data
     * @return ResponseEntity with RegisterResponse
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {

        Valid valid = registerService.ValidateRegisterParams(
                request.getNombre(),
                request.getApellidos(),
                request.getRfc(),
                request.getCorreo(),
                request.getEsAdministrador(),
                request.getPassword()
        );

        if (!valid.getIsValid()) {
            RegisterResponse errorResponse = new RegisterResponse();
            errorResponse.setSuccess(false);
            errorResponse.setMessage(valid.getError());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
            Empleado empleado = registerService.createEmpleado(
                    request.getNombre(),
                    request.getApellidos(),
                    request.getRfc(),
                    request.getCorreo()
            );

            registerService.saveEmpleado(empleado);

            if (request.getEsAdministrador() != null && request.getEsAdministrador()) {
                registerService.saveUsuario(empleado, request.getPassword());
            }

            RegisterResponse successResponse = new RegisterResponse();
            successResponse.setSuccess(true);
            successResponse.setMessage("Usuario registrado correctamente");

            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);

        } catch (Exception e) {
            RegisterResponse errorResponse = new RegisterResponse();
            errorResponse.setSuccess(false);
            errorResponse.setMessage("Error al registrar usuario: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}