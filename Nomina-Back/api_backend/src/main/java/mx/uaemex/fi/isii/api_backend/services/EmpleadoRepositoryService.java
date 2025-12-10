package mx.uaemex.fi.isii.api_backend.services;

import mx.uaemex.fi.isii.api_backend.domain.entity.Empleado;
import mx.uaemex.fi.isii.api_backend.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service. CRUD transactions for the {@code Repository Empleado}
 * @see EmpleadoRepository
 */

@Service
public class EmpleadoRepositoryService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoRepositoryService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    /**
     *  For getting a list of all the employees
     * @return List of all Empleado in the BD
     */
    public List<Empleado> findAllEmpleados() {
        return empleadoRepository.findAll();
    }

}
