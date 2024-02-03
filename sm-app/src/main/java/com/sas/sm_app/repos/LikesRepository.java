package com.sas.sm_app.repos;

import com.sas.sm_app.domain.Likes;
import com.sas.sm_app.domain.Publicacion;
import com.sas.sm_app.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikesRepository extends JpaRepository<Likes, Integer> {

    Likes findFirstByPublicacion(Publicacion publicacion);

    Likes findFirstByUsuario(Usuario usuario);

}
