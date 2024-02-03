package com.sas.sm_app.repos;

import com.sas.sm_app.domain.Hobby;
import com.sas.sm_app.domain.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findFirstByUsuarioHobbyHobbies(Hobby hobby);

    List<Usuario> findAllByUsuarioHobbyHobbies(Hobby hobby);

}
