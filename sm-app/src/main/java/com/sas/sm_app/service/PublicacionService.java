package com.sas.sm_app.service;

import com.sas.sm_app.domain.Comentario;
import com.sas.sm_app.domain.Likes;
import com.sas.sm_app.domain.Publicacion;
import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.PublicacionDTO;
import com.sas.sm_app.repos.ComentarioRepository;
import com.sas.sm_app.repos.LikesRepository;
import com.sas.sm_app.repos.PublicacionRepository;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.util.NotFoundException;
import com.sas.sm_app.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ComentarioRepository comentarioRepository;
    private final LikesRepository likesRepository;

    public PublicacionService(final PublicacionRepository publicacionRepository,
            final UsuarioRepository usuarioRepository,
            final ComentarioRepository comentarioRepository,
            final LikesRepository likesRepository) {
        this.publicacionRepository = publicacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.comentarioRepository = comentarioRepository;
        this.likesRepository = likesRepository;
    }

    public List<PublicacionDTO> findAll() {
        final List<Publicacion> publicacions = publicacionRepository.findAll(Sort.by("idPost"));
        return publicacions.stream()
                .map(publicacion -> mapToDTO(publicacion, new PublicacionDTO()))
                .toList();
    }

    public PublicacionDTO get(final Integer idPost) {
        return publicacionRepository.findById(idPost)
                .map(publicacion -> mapToDTO(publicacion, new PublicacionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PublicacionDTO publicacionDTO) {
        final Publicacion publicacion = new Publicacion();
        mapToEntity(publicacionDTO, publicacion);
        return publicacionRepository.save(publicacion).getIdPost();
    }

    public void update(final Integer idPost, final PublicacionDTO publicacionDTO) {
        final Publicacion publicacion = publicacionRepository.findById(idPost)
                .orElseThrow(NotFoundException::new);
        mapToEntity(publicacionDTO, publicacion);
        publicacionRepository.save(publicacion);
    }

    public void delete(final Integer idPost) {
        publicacionRepository.deleteById(idPost);
    }

    private PublicacionDTO mapToDTO(final Publicacion publicacion,
            final PublicacionDTO publicacionDTO) {
        publicacionDTO.setIdPost(publicacion.getIdPost());
        publicacionDTO.setTexto(publicacion.getTexto());
        publicacionDTO.setFecCreacion(publicacion.getFecCreacion());
        publicacionDTO.setUser(publicacion.getUser() == null ? null : publicacion.getUser().getIdUser());
        return publicacionDTO;
    }

    private Publicacion mapToEntity(final PublicacionDTO publicacionDTO,
            final Publicacion publicacion) {
        publicacion.setTexto(publicacionDTO.getTexto());
        publicacion.setFecCreacion(publicacionDTO.getFecCreacion());
        final Usuario user = publicacionDTO.getUser() == null ? null : usuarioRepository.findById(publicacionDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        publicacion.setUser(user);
        return publicacion;
    }

    public String getReferencedWarning(final Integer idPost) {
        final Publicacion publicacion = publicacionRepository.findById(idPost)
                .orElseThrow(NotFoundException::new);
        final Comentario postComentario = comentarioRepository.findFirstByPost(publicacion);
        if (postComentario != null) {
            return WebUtils.getMessage("publicacion.comentario.post.referenced", postComentario.getIdComment());
        }
        final Likes publicacionLikes = likesRepository.findFirstByPublicacion(publicacion);
        if (publicacionLikes != null) {
            return WebUtils.getMessage("publicacion.likes.publicacion.referenced", publicacionLikes.getIdLike());
        }
        return null;
    }

}
