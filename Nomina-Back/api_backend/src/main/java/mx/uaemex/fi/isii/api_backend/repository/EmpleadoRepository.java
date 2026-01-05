package mx.uaemex.fi.isii.api_backend.repository;

import mx.uaemex.fi.isii.api_backend.domain.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    List<Empleado> findAll();
}
