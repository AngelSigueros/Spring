package com.sas.sm_app.service;

import com.sas.sm_app.domain.Hobby;
import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.HobbyDTO;
import com.sas.sm_app.repos.HobbyRepository;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.util.NotFoundException;
import com.sas.sm_app.util.WebUtils;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class HobbyService {

    private final HobbyRepository hobbyRepository;
    private final UsuarioRepository usuarioRepository;

    public HobbyService(final HobbyRepository hobbyRepository,
            final UsuarioRepository usuarioRepository) {
        this.hobbyRepository = hobbyRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<HobbyDTO> findAll() {
        final List<Hobby> hobbies = hobbyRepository.findAll(Sort.by("idHobby"));
        return hobbies.stream()
                .map(hobby -> mapToDTO(hobby, new HobbyDTO()))
                .toList();
    }

    public HobbyDTO get(final Integer idHobby) {
        return hobbyRepository.findById(idHobby)
                .map(hobby -> mapToDTO(hobby, new HobbyDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final HobbyDTO hobbyDTO) {
        final Hobby hobby = new Hobby();
        mapToEntity(hobbyDTO, hobby);
        return hobbyRepository.save(hobby).getIdHobby();
    }

    public void update(final Integer idHobby, final HobbyDTO hobbyDTO) {
        final Hobby hobby = hobbyRepository.findById(idHobby)
                .orElseThrow(NotFoundException::new);
        mapToEntity(hobbyDTO, hobby);
        hobbyRepository.save(hobby);
    }

    public void delete(final Integer idHobby) {
        final Hobby hobby = hobbyRepository.findById(idHobby)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        usuarioRepository.findAllByUsuarioHobbyHobbies(hobby)
                .forEach(usuario -> usuario.getUsuarioHobbyHobbies().remove(hobby));
        hobbyRepository.delete(hobby);
    }

    private HobbyDTO mapToDTO(final Hobby hobby, final HobbyDTO hobbyDTO) {
        hobbyDTO.setIdHobby(hobby.getIdHobby());
        hobbyDTO.setNombre(hobby.getNombre());
        hobbyDTO.setDescripcion(hobby.getDescripcion());
        return hobbyDTO;
    }

    private Hobby mapToEntity(final HobbyDTO hobbyDTO, final Hobby hobby) {
        hobby.setNombre(hobbyDTO.getNombre());
        hobby.setDescripcion(hobbyDTO.getDescripcion());
        return hobby;
    }

    public String getReferencedWarning(final Integer idHobby) {
        final Hobby hobby = hobbyRepository.findById(idHobby)
                .orElseThrow(NotFoundException::new);
        final Usuario usuarioHobbyHobbiesUsuario = usuarioRepository.findFirstByUsuarioHobbyHobbies(hobby);
        if (usuarioHobbyHobbiesUsuario != null) {
            return WebUtils.getMessage("hobby.usuario.usuarioHobbyHobbies.referenced", usuarioHobbyHobbiesUsuario.getIdUser());
        }
        return null;
    }

}
