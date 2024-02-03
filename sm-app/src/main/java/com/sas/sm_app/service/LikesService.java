package com.sas.sm_app.service;

import com.sas.sm_app.domain.Likes;
import com.sas.sm_app.domain.Publicacion;
import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.LikesDTO;
import com.sas.sm_app.repos.LikesRepository;
import com.sas.sm_app.repos.PublicacionRepository;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final PublicacionRepository publicacionRepository;
    private final UsuarioRepository usuarioRepository;

    public LikesService(final LikesRepository likesRepository,
            final PublicacionRepository publicacionRepository,
            final UsuarioRepository usuarioRepository) {
        this.likesRepository = likesRepository;
        this.publicacionRepository = publicacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<LikesDTO> findAll() {
        final List<Likes> likeses = likesRepository.findAll(Sort.by("idLike"));
        return likeses.stream()
                .map(likes -> mapToDTO(likes, new LikesDTO()))
                .toList();
    }

    public LikesDTO get(final Integer idLike) {
        return likesRepository.findById(idLike)
                .map(likes -> mapToDTO(likes, new LikesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final LikesDTO likesDTO) {
        final Likes likes = new Likes();
        mapToEntity(likesDTO, likes);
        return likesRepository.save(likes).getIdLike();
    }

    public void update(final Integer idLike, final LikesDTO likesDTO) {
        final Likes likes = likesRepository.findById(idLike)
                .orElseThrow(NotFoundException::new);
        mapToEntity(likesDTO, likes);
        likesRepository.save(likes);
    }

    public void delete(final Integer idLike) {
        likesRepository.deleteById(idLike);
    }

    private LikesDTO mapToDTO(final Likes likes, final LikesDTO likesDTO) {
        likesDTO.setIdLike(likes.getIdLike());
        likesDTO.setPublicacion(likes.getPublicacion() == null ? null : likes.getPublicacion().getIdPost());
        likesDTO.setUsuario(likes.getUsuario() == null ? null : likes.getUsuario().getIdUser());
        return likesDTO;
    }

    private Likes mapToEntity(final LikesDTO likesDTO, final Likes likes) {
        final Publicacion publicacion = likesDTO.getPublicacion() == null ? null : publicacionRepository.findById(likesDTO.getPublicacion())
                .orElseThrow(() -> new NotFoundException("publicacion not found"));
        likes.setPublicacion(publicacion);
        final Usuario usuario = likesDTO.getUsuario() == null ? null : usuarioRepository.findById(likesDTO.getUsuario())
                .orElseThrow(() -> new NotFoundException("usuario not found"));
        likes.setUsuario(usuario);
        return likes;
    }

}
