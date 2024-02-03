package com.sas.sm_app.repos;

import com.sas.sm_app.domain.Configuracion;
import com.sas.sm_app.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer> {

    Configuracion findFirstByUser(Usuario usuario);

}
