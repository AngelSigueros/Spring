package com.sas.sm_app.service;

import com.sas.sm_app.domain.Comentario;
import com.sas.sm_app.domain.Publicacion;
import com.sas.sm_app.domain.Usuario;
import com.sas.sm_app.model.ComentarioDTO;
import com.sas.sm_app.repos.ComentarioRepository;
import com.sas.sm_app.repos.PublicacionRepository;
import com.sas.sm_app.repos.UsuarioRepository;
import com.sas.sm_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final PublicacionRepository publicacionRepository;

    public ComentarioService(final ComentarioRepository comentarioRepository,
            final UsuarioRepository usuarioRepository,
            final PublicacionRepository publicacionRepository) {
        this.comentarioRepository = comentarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.publicacionRepository = publicacionRepository;
    }

    public List<ComentarioDTO> findAll() {
        final List<Comentario> comentarios = comentarioRepository.findAll(Sort.by("idComment"));
        return comentarios.stream()
                .map(comentario -> mapToDTO(comentario, new ComentarioDTO()))
                .toList();
    }

    public ComentarioDTO get(final Integer idComment) {
        return comentarioRepository.findById(idComment)
                .map(comentario -> mapToDTO(comentario, new ComentarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ComentarioDTO comentarioDTO) {
        final Comentario comentario = new Comentario();
        mapToEntity(comentarioDTO, comentario);
        return comentarioRepository.save(comentario).getIdComment();
    }

    public void update(final Integer idComment, final ComentarioDTO comentarioDTO) {
        final Comentario comentario = comentarioRepository.findById(idComment)
                .orElseThrow(NotFoundException::new);
        mapToEntity(comentarioDTO, comentario);
        comentarioRepository.save(comentario);
    }

    public void delete(final Integer idComment) {
        comentarioRepository.deleteById(idComment);
    }

    private ComentarioDTO mapToDTO(final Comentario comentario, final ComentarioDTO comentarioDTO) {
        comentarioDTO.setIdComment(comentario.getIdComment());
        comentarioDTO.setTexto(comentario.getTexto());
        comentarioDTO.setFecCreacion(comentario.getFecCreacion());
        comentarioDTO.setUser(comentario.getUser() == null ? null : comentario.getUser().getIdUser());
        comentarioDTO.setPost(comentario.getPost() == null ? null : comentario.getPost().getIdPost());
        return comentarioDTO;
    }

    private Comentario mapToEntity(final ComentarioDTO comentarioDTO, final Comentario comentario) {
        comentario.setTexto(comentarioDTO.getTexto());
        comentario.setFecCreacion(comentarioDTO.getFecCreacion());
        final Usuario user = comentarioDTO.getUser() == null ? null : usuarioRepository.findById(comentarioDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        comentario.setUser(user);
        final Publicacion post = comentarioDTO.getPost() == null ? null : publicacionRepository.findById(comentarioDTO.getPost())
                .orElseThrow(() -> new NotFoundException("post not found"));
        comentario.setPost(post);
        return comentario;
    }

}
