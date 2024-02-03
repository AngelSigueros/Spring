package com.sas.sm_app.repos;

import com.sas.sm_app.domain.Seguidores;
import com.sas.sm_app.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SeguidoresRepository extends JpaRepository<Seguidores, Long> {

    Seguidores findFirstBySeguidor(Usuario usuario);

    Seguidores findFirstBySeguido(Usuario usuario);

}
