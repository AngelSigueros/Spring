package com.sas.sm_app.repos;

import com.sas.sm_app.domain.Comentario;
import com.sas.sm_app.domain.Publicacion;
import com.sas.sm_app.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

    Comentario findFirstByUser(Usuario usuario);

    Comentario findFirstByPost(Publicacion publicacion);

}
