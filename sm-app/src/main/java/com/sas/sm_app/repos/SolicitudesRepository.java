package com.sas.sm_app.repos;

import com.sas.sm_app.domain.Solicitudes;
import com.sas.sm_app.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SolicitudesRepository extends JpaRepository<Solicitudes, Integer> {

    Solicitudes findFirstBySolicitante(Usuario usuario);

    Solicitudes findFirstByReceptor(Usuario usuario);

}
