package com.sas.sm_app.repos;

import com.sas.sm_app.domain.MensajePrivado;
import com.sas.sm_app.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MensajePrivadoRepository extends JpaRepository<MensajePrivado, Integer> {

    MensajePrivado findFirstByUsuarioEmisor(Usuario usuario);

    MensajePrivado findFirstByUsuarioReceptor(Usuario usuario);

}
