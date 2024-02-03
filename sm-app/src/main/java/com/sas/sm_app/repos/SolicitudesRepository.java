package com.sas.sm_app.repos;

import com.sas.sm_app.domain.Solicitudes;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SolicitudesRepository extends JpaRepository<Solicitudes, Integer> {
}
