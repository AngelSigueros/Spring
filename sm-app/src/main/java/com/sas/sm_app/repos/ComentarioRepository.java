package com.sas.sm_app.repos;

import com.sas.sm_app.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
}
