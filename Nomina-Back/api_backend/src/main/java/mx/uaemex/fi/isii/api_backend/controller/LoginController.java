package mx.uaemex.fi.isii.api_backend.controller;

import mx.uaemex.fi.isii.api_backend.domain.dto.request.LoginRequest;
import mx.uaemex.fi.isii.api_backend.domain.dto.response.LoginResponse;
import mx.uaemex.fi.isii.api_backend.services.UsuarioRepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * RestController. For consuming login requests
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true", allowedHeaders = "*")
public class LoginController {

    private final UsuarioRepositoryService usuarioRepositoryService;

    public LoginController(UsuarioRepositoryService usuarioRepositoryService) {
        this.usuarioRepositoryService = usuarioRepositoryService;
    }

    /**
     * Do the login authentication
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest request) {
        LoginResponse response = new LoginResponse();

        if (usuarioRepositoryService.validateLogin(request.getUsername(), request.getPassword())){
            response.setSuccess(Boolean.TRUE);
            response.setMessage("Login realizado con éxito");

            return ResponseEntity.ok(response);
        } else {
            response.setSuccess(false);
            response.setMessage("Credenciales incorrectas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}
