package com.sas.sm_app.repos;

import com.sas.sm_app.domain.Historia;
import com.sas.sm_app.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HistoriaRepository extends JpaRepository<Historia, Integer> {

    Historia findFirstByUser(Usuario usuario);

}
