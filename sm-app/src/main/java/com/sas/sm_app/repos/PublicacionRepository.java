package com.sas.sm_app.repos;

import com.sas.sm_app.domain.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {
}
